package com.example.demo.entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message = "username shouldn't be empty")
	private String name;
	@Email(message = "email id is not valid")
	private String email;
	@NotEmpty(message = "password must not be empty")
	private String password;
	@Pattern(regexp = "^\\d{10}$",message = "invalid phone number entered")
	private String phNo;
	
	@ManyToMany
	@JoinTable(name = "user_event",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "event_id"))
	private List<Event> registeredEvents = new ArrayList<>();
}
