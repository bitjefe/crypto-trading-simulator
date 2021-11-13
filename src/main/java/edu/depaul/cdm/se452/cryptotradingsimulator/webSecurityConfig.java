package edu.depaul.cdm.se452.cryptotradingsimulator;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class webSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    // we need to have a Bean here to have the Autowired elements in
    // CustomUserDetailsService class.
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * http.authorizeRequests().anyRequest().authenticated().and().formLogin().
         * loginPage("/auth-sign-in.html") .permitAll();
         */

        http.authorizeRequests().antMatchers("/home/list_users").authenticated().anyRequest().permitAll().and()
                .formLogin().usernameParameter("username").defaultSuccessUrl("/home/list_users").permitAll().and()
                .logout().logoutSuccessUrl("/home").permitAll();

        /*
         * http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
         */

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        /*
         * auth.inMemoryAuthentication().withUser("user").password("{noop}pass2") //
         * Spring Security 5 requires specifying // the password storage format
         * .roles("USER");
         */
    }

}