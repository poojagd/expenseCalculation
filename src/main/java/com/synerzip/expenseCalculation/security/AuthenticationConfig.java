package com.synerzip.expenseCalculation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/users/**").permitAll()
        						.anyRequest().authenticated().and()
								.formLogin().loginPage("/login").permitAll()
								.and()
								.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
				                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.cors().and().csrf().disable();
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
	
	 @Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	  }
}
