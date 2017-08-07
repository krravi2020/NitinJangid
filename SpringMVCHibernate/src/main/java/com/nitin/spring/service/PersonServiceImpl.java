package com.nitin.spring.service;

import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nitin.spring.dao.PersonDAO;
import com.nitin.spring.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	
	private PersonDAO personDAO;

	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public void addPerson(Person p) {
		this.personDAO.addPerson(p);
	}

	@Override
	@Transactional
	public void updatePerson(Person p) {
		this.personDAO.updatePerson(p);
	}

	@Override
	@Transactional
	public List<Person> listPersons() {
		return this.personDAO.listPersons();
	}

	@Override
	@Transactional
	public Person getPersonById(int id) {
		return this.personDAO.getPersonById(id);
	}

	@Override
	@Transactional
	public void removePerson(int id) {
		this.personDAO.removePerson(id);
	}

	@Override
	public JSONObject savePersonService(Person person) {
		// TODO Auto-generated method stub
		
		final JSONObject jobject = new JSONObject();
		try{
		/*final String name = person.getName();
		final String emailId = person.getEmailId();
		final String mobile = person.getMobile();
		final String city = person.getCity();
		final String state = person.getState();
		final String country = person.getCountry();*/
		System.out.println("hello-----------------------------------------Service>");
		int i = personDAO.savePersonDAO(person);
		if(i==1){
			jobject.put("status", "success");
            jobject.put("message", "User has been registered ");
		}else{
			jobject.put("status", "Fail");
            jobject.put("message", "User has not been registered ");
		}
		
		return jobject;
		}catch(Exception e){
			e.printStackTrace();
			
			return jobject;
		}

	}

	@Override
	public JSONObject login(Person p) {
		// TODO Auto-generated method stub
		System.out.println("hello------------------login-----------------------Service>");
		final JSONObject jobject = new JSONObject();
		try{
			String email = p.getEmail();
			String password = p.getPassword();
			
			Person person = personDAO.getPersonByEmail(email);
			System.out.println("done");
			String emailIdDB = person.getEmail();
			String passwordDB = person.getPassword();
			if(email.equalsIgnoreCase(emailIdDB)){
				System.out.println("Email is matched!");
				if(password.equals(passwordDB)){
					System.out.println("Passwod is matched!");
					UUID uuid = UUID.randomUUID();
			        String accessToken = uuid.toString();
			        System.out.println("accessToken " + accessToken);
			        jobject.put("statuss", "success");
			        jobject.put("message", "user is logined successfully!");
			        jobject.put("accessToken", accessToken);
			        
			        final JSONObject jPerson = new JSONObject();
			        jPerson.put("id", person.getId());
			        jPerson.put("name", person.getName());
			        jPerson.put("email", person.getEmail());
			        
			        jobject.put("data", jPerson);
			        return jobject;
				}
				else{
					jobject.put("statuss", "fail");
			        jobject.put("message", "password is wrong!");
			        return jobject;
				}
			}else{
				jobject.put("statuss", "fail");
		        jobject.put("message", "email id is wrong!");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}
