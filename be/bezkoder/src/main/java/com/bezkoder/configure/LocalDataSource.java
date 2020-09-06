package com.bezkoder.configure;




import com.bezkoder.configure.jwt.JwtUtils;
import com.bezkoder.enumeration.ERole;
import com.bezkoder.model.Role;
import com.bezkoder.model.User;

import com.bezkoder.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Component
@Profile("mysql")
public class LocalDataSource implements CommandLineRunner {
    Logger LOGGER = LoggerFactory.getLogger(LocalDataSource.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;
    @Override
    @Transactional
    public void run(String... args) {
        LOGGER.trace("This is TRACE");
        LOGGER.debug("This is DEBUG");
        LOGGER.info("This is INFO");
        LOGGER.warn("This is WARN");
        LOGGER.error("This is ERROR");
//        logger.error("Error Message Logged !!!", new NullPointerException("NullError"));
//        User user = new User();
//        user.setUsername("admin");
//        user.setEmail("admin@gmail.com");
//
//        Role role = new Role();
//        role.setName(ERole.ROLE_ADMIN.getName());
//        role.setUser(user);
//
//        user.setRoles(Collections.singletonList(role));
//        user.setPassword(encoder.encode("123456"));
//
//        userRepository.saveAndFlush(user);
    }
}
