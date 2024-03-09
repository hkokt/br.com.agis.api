package com.br.fatec.AGIS.repository;

import com.br.fatec.AGIS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    public User findByLogin(String login) ;
}
