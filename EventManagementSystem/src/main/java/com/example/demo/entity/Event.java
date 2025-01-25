package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "events")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message = "event name cannot be empty")
	private String name;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date date;
	@NotEmpty(message = "location shouldn't be empty")
	private String location;
	@NotEmpty(message = "venue shouldn't be empty")
	private String venue;
	@NotEmpty(message = "provide a brief description")
	private String description;
	@ManyToMany(mappedBy = "registeredEvents")
	@JsonIgnore
	private List<User> registeredUsers = new ArrayList<>();
}
