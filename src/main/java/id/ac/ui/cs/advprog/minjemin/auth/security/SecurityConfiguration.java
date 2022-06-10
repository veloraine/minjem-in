package id.ac.ui.cs.advprog.minjemin.auth.security;

import id.ac.ui.cs.advprog.minjemin.auth.service.MinjeminUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    MinjeminUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint())
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
                .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/").permitAll()
                    .antMatchers(HttpMethod.GET, "/access-denied").permitAll()
                    .antMatchers("/scripts/**", "/images/**", "/css/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/auth/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/admin/**").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.POST, "/admin/**").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.GET, "/peminjaman/pinjam/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/peminjaman/pinjam/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/user-inventory").permitAll()
                    .anyRequest().authenticated()
                    .and().exceptionHandling().accessDeniedPage("/access-denied")
                .and()
                    .logout()
                    .logoutSuccessUrl("/auth/login")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll()
                .and()
                    .cors().and().csrf().disable();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
