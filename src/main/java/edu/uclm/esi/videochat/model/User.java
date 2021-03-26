package edu.uclm.esi.videochat.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	@Id
	private String id;
	private String email;
	private String name;
	private String pwd;
	private String picture;
	@Transient 
	private WebSocketSession sessionDeTexto;
	@Transient 
	private WebSocketSession sessionDeSignaling;
	private long confirmationDate;
	
	public User() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String userName) {
		this.name = userName;
	}

	@JsonIgnore
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setSessionDeTexto(WebSocketSession session) {
		this.sessionDeTexto = session;
	}
	public void setSessionDeSignaling(WebSocketSession session) {
		this.sessionDeSignaling = session;
	}
	
	@JsonIgnore
	public WebSocketSession getSessionDeSignaling() {
		return sessionDeSignaling;
	}

	public void setConfirmationDate(long date) {
		this.confirmationDate = date;
	}
	
	public long getConfirmationDate() {
		return confirmationDate;
	}
	@JsonIgnore
	public WebSocketSession getSessionDeTexto() {
		return sessionDeTexto;
	}
}
