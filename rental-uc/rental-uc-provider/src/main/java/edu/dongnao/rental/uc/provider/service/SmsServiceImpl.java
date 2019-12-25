package edu.dongnao.rental.uc.provider.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import edu.dongnao.rental.lang.ServiceResult;
import edu.dongnao.rental.uc.api.ISmsService;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Traveler
 * @date 2019/12/23 11:55
 */
@Service(protocol = "dubbo")
@Log4j2
public class SmsServiceImpl implements ISmsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${aliyun.sms.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.sms.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.template-code}")
    private String templateCode;

    private IAcsClient acsClient;

    private static final Random RANDOM = new Random();
    private static final String SMS_CODE_CONTENT_PREFIX = "SMS::CODE::CONTENT::PREFIX";
    private static final String[] NUMS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    @PostConstruct
    public void init() {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        this.acsClient = new DefaultAcsClient(profile);
    }

    @Override
    public ServiceResult<String> sendSms(String telephone) {
        String gapKey = "SMS::CODE::INTERVAL::" + telephone;
        String code = redisTemplate.opsForValue().get(gapKey);
        if (code != null) {
            return new ServiceResult<>(false, "请求次数太频繁");
        }

        // 生成随机验证码
        StringBuilder builder = new StringBuilder();
        int codeLength = 6;
        for (int i = 0; i < codeLength; i++) {
            builder.append(NUMS[RANDOM.nextInt(10)]);
        }
        code = builder.toString();
        // post方式发送短信模版
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName("租个房");
        request.setPhoneNumbers(telephone);
        request.setTemplateCode(templateCode);
        request.setTemplateParam(String.format("{\"code\":\"%s\"}", code));
        // 发送短信-获取请求结果
        boolean success = false;
        try {
            SendSmsResponse response = acsClient.getAcsResponse(request);
            success = "OK".equals(response.getCode());

            Assert.isTrue(success, String.format("Send sms fail: %s", response.getMessage()));
        } catch (ClientException e) {
            log.error(e.getMessage(), e);
        }
        // 短信发送成功
        if (success) {
            redisTemplate.opsForValue().set(gapKey, code, 60, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(SMS_CODE_CONTENT_PREFIX + telephone, code, 10, TimeUnit.MINUTES);
            return ServiceResult.of(code);
        }
        return new ServiceResult<>(false, "服务器繁忙，请稍后重试");
    }

    @Override
    public String getSmsCode(String telephone) {
        return this.redisTemplate.opsForValue().get(SMS_CODE_CONTENT_PREFIX + telephone);
    }

    @Override
    public void remove(String telephone) {
        this.redisTemplate.delete(SMS_CODE_CONTENT_PREFIX + telephone);
    }
}
