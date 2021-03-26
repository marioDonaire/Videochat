package edu.uclm.esi.videochat.springdao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.uclm.esi.videochat.model.Message;
import edu.uclm.esi.videochat.model.User;

public interface MessageRepository extends CrudRepository <Message, String> {
	
	@Query(value = "SELECT * FROM message where sender=:destinatario and recipient=:usuario or recipient=:destinatario and sender=:usuario", nativeQuery = true)
	public List<Message> findConversacion(String destinatario, String usuario);
}
