package com.fillubell.config;

import com.fillubell.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                //.antMatchers("/admin").hasRole("ADMIN") //только ажмины
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll() //доступ к страницам
                .anyRequest().authenticated() //ну тип те кто не зашел тот не заходит
                .and()
                .formLogin().loginPage("/auth/login") //страничка login и AuthController
                .loginProcessingUrl("/process_login") //процесс с данными
                .defaultSuccessUrl("/hello", true) //перекидывание на главную страничку
                .failureUrl("/auth/login?error") // ошибка
                .and()
                .logout().logoutUrl("/logout") //страница выхода
                .logoutSuccessUrl("/auth/login"); //куда перекинет потом
    }   //Настройка Страниц авторизации в Spring Security

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService) //вход
                .passwordEncoder(getPasswordEncoder()); //сравнение хешей(паролей)
    }   //Настройка входа

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(); //преколы с хэшем
    }
}
