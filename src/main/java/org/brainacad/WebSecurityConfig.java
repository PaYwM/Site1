package org.brainacad;

import org.brainacad.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // аннотация помечающая класс для конфигурации spring context
@EnableWebSecurity // включение механизма безопасности
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // расшираем базовую настройку безопасности

    // Наш сервис который переопределяет базовую реализацию для авторизации.
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // Глобальная конфигурация для аутентификации.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());// определяем шифрование пароля
    }

    @Bean // Шифратор паролей
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean // Определение менеджера ответственного за аутентификацию
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    // Основная конфигурация.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // блок запросов требующих авторизацию
                    .antMatchers("/resources/**", "/registration", "/css/**").permitAll() // исключение
                    .anyRequest().authenticated() // все остальные запросы только после логина
                    .and() // определяем след. блок
                .formLogin()// блок для логина
                    .loginPage("/login") // страница логина
                    .usernameParameter("email") // поле имени юзера в моделе
                    .passwordParameter("password") // поле пароля в моделе
                    .permitAll() // доступ на стр. логина всем
                .and() // определяем след. блок
                .httpBasic() // включаем Basic аутентификацию
                .and()
                .logout() // блок для logout
                    .permitAll();
    }
}