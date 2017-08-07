package com.nitin.spring.dao;

import java.util.List;

import org.json.JSONObject;

import com.nitin.spring.model.Person;

public interface PersonDAO {

	public void addPerson(Person p);
	public void updatePerson(Person p);
	public List<Person> listPersons();
	public Person getPersonById(int id);
	public void removePerson(int id);
	public int savePersonDAO(Person p);
	
	//impl
	public Person getPersonByEmail(String email);
}
