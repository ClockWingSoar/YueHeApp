package com.yuehe.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.yuehe.app.entity.User;
import com.yuehe.app.service.UserService;
import com.yuehe.app.util.YueHeUtil;

@Configuration
public class BeanConfigurations {
 
	@Autowired
    private UserService userService;
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
    //when spring boot initialize, it will run this method
    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
        	   int idNums = userService.getAllUser().size();
               String id = YueHeUtil.getId(8,idNums);
               if(idNums == 0)
            	   userService.create(new User(id, "admin", passwordEncoder().encode("admin"), "ROLE_ADMIN"));
        };
    }

}