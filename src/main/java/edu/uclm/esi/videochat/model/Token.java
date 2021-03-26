package edu.uclm.esi.videochat.model;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Token {
	@Id
	private String id;
	private String email;
	private long date;
	
	public Token() {
	}

	public Token(String email) {
		this.id = UUID.randomUUID().toString();
		this.email = email;
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		c.add(Calendar.MONTH, +1);
		c.add(Calendar.HOUR, +1);
		this.date = c.getTimeInMillis();
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

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	
}
