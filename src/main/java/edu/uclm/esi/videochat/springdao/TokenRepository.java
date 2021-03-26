package edu.uclm.esi.videochat.springdao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.uclm.esi.videochat.model.Token;
import edu.uclm.esi.videochat.model.User;

public interface TokenRepository extends CrudRepository <Token, String> {
	
	public Optional<Token> findById(String Id);

}
