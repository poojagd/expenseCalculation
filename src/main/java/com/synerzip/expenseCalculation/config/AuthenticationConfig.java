package com.synerzip.expenseCalculation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

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
		http.authorizeRequests().antMatchers("/expenses/**").authenticated()
		                        .anyRequest().permitAll()
		                        .and()
		                        .formLogin().permitAll()
		                        .and()
		                        .httpBasic();
		http.csrf().disable();
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
