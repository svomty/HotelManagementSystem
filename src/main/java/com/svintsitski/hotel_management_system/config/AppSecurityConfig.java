package com.svintsitski.hotel_management_system.config;

import com.svintsitski.hotel_management_system.model.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@ComponentScan(basePackages = {"com.svintsitski.hotel_management_system"})
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Аннотация @EnableWebSecurity в связке с WebSecurityConfigurerAdapter классом работает над обеспечением
     * аутентификации. По умолчанию в Spring Security встроены и активны HTTP аутентификация и аутентификация на базе
     * веб форм.
     *
     * @author Артем Свинтицкий
     */

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        Config config = Config.getInstance();

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserDetails userDetails = User.withUsername(config.getLogin())
                .password(encoder.encode(config.getPassword()))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .and().formLogin()
                .and().logout().logoutSuccessUrl("/").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager());
    }

    /**
     * здесь прописываем пользователей с их ролями, а затем указываем адреса ресурсов с ограниченным доступом,
     * ограничение задано по ролям. Имена и пароли пользователей, для простоты, указаны прямо в коде. Spring Security
     * позволяет с легкостью указать другой источник для данных о пользователях, например базу данных. Обратите внимание
     * что роли в месте где мы присваиваем их пользователю пишутся без префикса ROLE_, в то время как в указании в
     * методе access, в котором мы, с помощью языка выражений SPEL (Spring Expression Language), задаем выражения
     * проверки ресурса (в нашем случае выражение проверки роли пользователя hasRole(‘ROLE_имя роли’)), мы пишем роль
     * с префиксом ROLE_. Еще одна маленькая хитрость для аутентификации: defaultSuccessUrl("/", false), установка
     * второго параметра (alwaysUse) в false говорит Spring Security что в случае успешной авторизации можно
     * перенаправить пользователя на ту страничку, с которой он пришел на страницу аутентификации.
     */
}
