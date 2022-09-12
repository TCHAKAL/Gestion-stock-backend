package dz.tchakal.gds.configuration;

import dz.tchakal.gds.service.authentication.ApplicationUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // la classe WebSecurityConfiguration a déja cette annotation
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Lazy
    private ApplicationUserDetailService applicationUserDetailService;
    @Autowired
    @Lazy
    private ApplicationRequestFilter applicationRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/**/authentication/authenticate",
                        "/**/utilisateurs/create",
                        "/**/entreprises/**",
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/swagger-ui/index.html",
                        "/v3/api-docs/**"
                        ).permitAll()
                .anyRequest()
                .authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Dire a spring quel user details a utilisé
        auth.userDetailsService(applicationUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManagerBean();
    }

    //NoOpPasswordEncoder.getInstance() ou bien "{noop}" pour dire a spring boot que le mot de passe n'est pas crypté
    @Bean
    public PasswordEncoder passwordEncoder() {
        //Pour cryptage du mot de passe
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
}
