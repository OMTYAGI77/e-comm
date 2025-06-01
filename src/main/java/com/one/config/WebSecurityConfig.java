package com.one.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.one.security.jwt.AuthEntryPointJwt;
import com.one.security.jwt.AuthTokenFilter;
import com.one.service.impl.UserDetailsServiceImpl;
import com.one.utils.EncryptionUtils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConf) throws Exception {
		return authConf.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(userDetailsService);
		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// return new BCryptPasswordEncoder();

		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence charSequence) {
				try {
					return EncryptionUtils.makeMD5String(charSequence.toString());
				} catch (Exception e) {
					return null;
				}
			}

			@Override
			public boolean matches(CharSequence charSequence, String s) {
				try {
					return EncryptionUtils.checkMD5Password(charSequence.toString(), s);
				} catch (Exception e) {
					return false;
				}
			}
		};
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults());
		http.csrf(csrf -> csrf.disable()).exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(request -> request.requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/**"))
				.permitAll().anyRequest().authenticated());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		// log.info("ingnoring the security");
		return web -> web.ignoring()
				.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**")
				.requestMatchers("/error/**");
	}

}
