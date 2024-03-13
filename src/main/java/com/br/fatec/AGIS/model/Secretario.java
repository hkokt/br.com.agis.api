package com.br.fatec.AGIS.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.br.fatec.AGIS.service.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@lombok.Data
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Secretario implements UserDetails {
	private static final long serialVersionUID = 1L;

	// PARTE NECESSARIA PARA LOGIN
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(nullable = false)
	private String id;
	
	@Column(nullable = false, length = 11)
	private String cpf;
	
	@Column(nullable = false)
	private String senha;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "roles")
	private UserRole role;
	
	//DADOS A MANTER
	@Column(nullable = false, length = 100)
	private String nome;
	
	@Column(nullable = false, name = "data_nasc", columnDefinition = "DATE")
	private LocalDate dataNasc;
	
	@Column(nullable = false, unique = true, name = "email_pessoal", length = 30)
	private String emailPessoal;
	
	@Column(nullable = false, unique = true, name = "email_corp", length = 30)
	private String emailCorp;	
	
	@Column(nullable = false, length = 20)
	private String situacao;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == UserRole.SECRETARIO) return List.of(new SimpleGrantedAuthority("ROLE_SECRETARIO"));
		return null;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return cpf;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
