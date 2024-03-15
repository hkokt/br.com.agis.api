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
						.requestMatchers(HttpMethod.POST, "/professores/login", "/secretario/registro", "/secretario/login").permitAll()
						
						// SECRETARIA
						.requestMatchers(HttpMethod.POST, 
								//"/secretario/registro", 
								"/alunos", 
								"/cursos", 
								"/disciplinas", 
								"/grades",
								"/turmas",
								"/professores/registro",
								"/datas"
								).hasRole("SECRETARIO")
						.requestMatchers(HttpMethod.GET, 
								"/alunos", 
								"/alunos/{ra}", 
								"/cursos", 
								"/cursos/{cod}", 
								"/datas",
								"/datas/{desc}",
								"/disciplinas", 
								"/disciplinas/{cod}",
								"/disciplinas/curso/{cod}",
								"/grades",
								"/grades/{cod}",
								"/matriculas/aluno/{ra}",
								"/professores",
								"/professores/{cod}",
								"/turmas/{cod}",
								"/turmas/grade/{cod}"
								).hasRole("SECRETARIO")
						.requestMatchers(HttpMethod.PUT, 
								"/alunos/{ra}", 
								"/cursos/{cod}", 
								"/disciplinas/{cod}", 
								"/datas/{data}",
								"/professores/{cod}",
								"/turmas/{cod}"
								).hasRole("SECRETARIO")
						.requestMatchers(HttpMethod.DELETE, 
								"/alunos/{ra}", 
								"/cursos/{cod}", 
								"/disciplinas/{cod}", 
								"/datas/{data}",
								"/professores/{cod}",
								"/turmas/{cod}"
								).hasRole("SECRETARIO")
						
						// PROFESSOR
						.requestMatchers(HttpMethod.POST, 
								"/chamada"
								).hasRole("PROFESSOR")
						.requestMatchers(HttpMethod.GET, 
								"/chamada/{cod}/{data}",
								"/chamada/verificacao/{cod}/{data}",
								"/matriculas/turma/{cod}",
								"/turmas/professor/{cod}"
								).hasRole("PROFESSOR")
						.requestMatchers(HttpMethod.PATCH, 
								"/turmas/{cod}/{metodo}"
								).hasRole("PROFESSOR")
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
