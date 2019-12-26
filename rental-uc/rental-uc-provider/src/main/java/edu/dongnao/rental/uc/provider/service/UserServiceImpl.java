package edu.dongnao.rental.uc.provider.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.dongnao.rental.uc.api.IUserService;
import edu.dongnao.rental.uc.provider.entity.User;
import edu.dongnao.rental.uc.provider.repository.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author Traveler
 * @since 2019/12/26 12:35
 */
@Service(protocol = "dubbo")
@Log4j2
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService<User> {
}
