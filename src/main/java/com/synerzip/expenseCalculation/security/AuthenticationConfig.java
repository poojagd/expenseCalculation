package com.synerzip.expenseCalculation.security;

import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import static com.synerzip.expenseCalculation.security.SecurityConstants.SIGN_UP_URL;
import com.synerzip.expenseCalculation.repository.UserRepository;
import com.synerzip.expenseCalculation.service.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll().and()
        .authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests()
        .antMatchers("/h2-console/**").permitAll().anyRequest().authenticated().and().formLogin()
        .loginPage("/login").permitAll().and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager()))
        .addFilter(new JWTAuthorizationFilter(authenticationManager())).sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.csrf().disable();
    http.headers().frameOptions().disable();
    http.cors().configurationSource(new CorsConfigurationSource() {

      @Override
      public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        return config;
      }

    });
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {

      @Override
      public String encode(CharSequence rawPassword) {
        String str = rawPassword.toString();
        return BCrypt.hashpw(str, BCrypt.gensalt(10));
      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String str = rawPassword.toString();
        return BCrypt.checkpw(str, encodedPassword);
      }

    });

  }


}
