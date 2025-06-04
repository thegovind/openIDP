/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 
 * class OAuth2ResourceServerConfig {@inheritDoc}
 */
@Configuration
@EnableWebSecurity
public class OAuth2ResourceServerConfig {



	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))
			.authorizeHttpRequests(authz -> authz
				.requestMatchers("/", "/lib/*", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/images/*",
						"/css/*", "/swagger-ui.js", "/swagger-ui.min.js", "/api-docs", "/fonts/*", "/api-docs/*",
						"/api-docs/default/*", "/o2c.html", "index.html", "/webjars/**", "/hystrix/**").permitAll()
				.requestMatchers("/licenseService/**").hasAuthority("SCOPE_read")
				.anyRequest().authenticated());
		return http.build();
	}

}
