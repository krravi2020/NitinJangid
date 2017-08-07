package com.nitin.spring.service;

import java.util.List;

import org.json.JSONObject;

import com.nitin.spring.model.Person;

public interface PersonService {

	public void addPerson(Person p);
	public void updatePerson(Person p);
	public List<Person> listPersons();
	public Person getPersonById(int id);
	public void removePerson(int id);
	
	//new impl
	public JSONObject savePersonService(Person p);
	public JSONObject login(Person p);
	
}
