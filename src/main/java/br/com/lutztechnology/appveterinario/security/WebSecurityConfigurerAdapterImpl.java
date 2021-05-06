package br.com.lutztechnology.appveterinario.security;

import br.com.lutztechnology.appveterinario.domain.service.AppUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfigurerAdapterImpl extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserDetailsServiceImpl appUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/app/medical-records", "/app/customers/**", "/app/animals/**", "/admin/users/**").authenticated()
                .regexMatchers("/app/medical-records/archived", "/app/medical-records/search.*", "/app/medical-records/[0-9]{1,10}").authenticated()
                .antMatchers("/app/medical-records/**").hasAnyRole("VET", "ADMIN")
                .antMatchers("/admin/roles/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                .userDetailsService(appUserDetailsService)
                .tokenValiditySeconds(1800)
                .and()
                .requiresChannel()
                .anyRequest()
                .requiresSecure()
                .and()
                .cors()
                .and()
                .csrf().disable();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.appUserDetailsService);

        return daoAuthenticationProvider;
    }
}
