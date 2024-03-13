package com.br.fatec.AGIS.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.br.fatec.AGIS.service.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private SecurityFilter securityFilter;
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/secretario/login").permitAll()
						//requisições POST permitidas para a secretaria
						.requestMatchers(HttpMethod.POST, 
								"/secretario/registro", 
								"/alunos", 
								"/cursos", 
								"/disciplinas", 
								"/grades",
								"/turmas",
								"/professores",
								"//datas"
								).hasRole("SECRETARIO")
						//requisições PUT permitidas para a secretaria
						.requestMatchers(HttpMethod.PUT, 
								"/alunos", 
								"/cursos", 
								"/disciplinas", 
								"/grades",
								"//datas"
								).hasRole("SECRETARIO")
						//.anyRequest().authenticated()
				)
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}
