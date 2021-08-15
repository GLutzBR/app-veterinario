package br.com.lutztechnology.appveterinario.config;

import br.com.lutztechnology.appveterinario.services.AppUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfigurerAdapterImpl extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserDetailsServiceImpl appUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/app/medical-records", "/app/customers/**", "/app/animals/**", "/admin/users/**").authenticated()
                .regexMatchers("/app/medical-records/archived", "/app/medical-records/search.*", "/app/medical-records/[0-9]{1,10}").authenticated()
                .antMatchers("/app/medical-records/**").hasAnyRole("VET", "ADMIN")
                .antMatchers("/admin/roles/**").hasRole("ADMIN");

        http.csrf()
                .ignoringAntMatchers("/api/v1/**");

        http
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");

        http.rememberMe()
                .key("chaveUnicaDeTeste");
//                .userDetailsService(appUserDetailsService)
//                .tokenValiditySeconds(1800)
//                .and()
//                .requiresChannel()
//                .anyRequest()
//                .requiresSecure()
//                .and()
//                .cors();
    }
}
