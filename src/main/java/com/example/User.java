package com.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Set;

public class User
{
	private String name;
	private String email;
	private Set<Role> roles;
	private LocalDate dateCreated;

	public User(
			@JsonProperty("name") String name,
			@JsonProperty("email") String email,
			@JsonProperty("role") Set<Role> roles,
			@JsonProperty("dateCreated") LocalDate dateCreated
	)
	{
		this.name = name;
		this.email = email;
		this.roles = roles;
		this.dateCreated = dateCreated;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public LocalDate getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public String toJson() throws JsonProcessingException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.registerModule(new JavaTimeModule());
		mapper.setDateFormat(dateFormat);

		ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
		String json = writer.writeValueAsString(this);

		return json;
	}

	public enum Role { USER, ADMIN };
}
