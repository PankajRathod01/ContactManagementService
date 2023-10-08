/**
 * 
 */
package com.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author pankaj.rathod
 *
 */
@EnableWebSecurity
@Configuration
public class CmsSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {

		// InMemoryUserDetailsManager
		UserDetails admin = User.withUsername("admin").password(encoder.encode("admin")).build();

		UserDetails user = User.withUsername("user").password(encoder.encode("user")).build();

		return new InMemoryUserDetailsManager(admin, user);
	}
	
	// Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable).
		authorizeHttpRequests(requests -> 
		requests.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll().
		requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**", "/swagger-ui/**")).permitAll().
		requestMatchers(new AntPathRequestMatcher("/v1/contact"))
				.permitAll().anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService(passwordEncoder()));
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

}
