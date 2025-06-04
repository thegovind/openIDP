
/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.Customizer;

/**
 * @author Infosys
**/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Autowired
	private RestfulRemoteAuthenticationProvider restfulRemoteAuthenticationProvider;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz -> authz
				.requestMatchers("/login").permitAll()
				.requestMatchers("/oauth/token/revokeById/**").permitAll()
				.requestMatchers("/tokens/**").permitAll()
				.anyRequest().authenticated())
			.formLogin(form -> form.permitAll())
			.csrf(csrf -> csrf.disable())
			.authenticationProvider(restfulRemoteAuthenticationProvider);
		return http.build();
	}

}
