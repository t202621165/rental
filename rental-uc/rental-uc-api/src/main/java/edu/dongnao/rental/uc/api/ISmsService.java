package edu.dongnao.rental.uc.api;

import edu.dongnao.rental.lang.ServiceResult;

/**
 * @author Traveler
 * @date 2019/12/20 15:34
 */
public interface ISmsService {

    /**
     * 发送短信验证码到指定手机
     * @param telephone
     *          手机号码
     * @return
     */
    ServiceResult<String> sendSms(String telephone);

    /**
     * 获取缓存中的验证码
     * @param telephone
     * @return
     */
    String getSmsCode(String telephone);

    /**
     * 删除缓存中的验证码
     * @param telephone
     */
    void remove(String telephone);
}
