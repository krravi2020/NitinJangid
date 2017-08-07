package com.nitin.spring.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nitin.spring.model.Person;

@Repository
public class PersonDAOImpl implements PersonDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addPerson(Person p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("Person saved successfully, Person Details="+p);
	}

	@Override
	public void updatePerson(Person p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Person updated successfully, Person Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> listPersons() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Person> personsList = session.createQuery("from Person").list();
		for(Person p : personsList){
			logger.info("Person List::"+p);
		}
		return personsList;
	}

	@Override
	public Person getPersonById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Person p = (Person) session.load(Person.class, new Integer(id));
		logger.info("Person loaded successfully, Person details="+p);
		return p;
	}

	@Override
	public void removePerson(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Person p = (Person) session.load(Person.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		logger.info("Person deleted successfully, person details="+p);
	}

	@Override
	@Transactional
	public int savePersonDAO(Person p) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		
		session.persist(p);
		System.out.println("hello-----------------------------------------DAO>");
		return 1;
	}

	@Override
	@Transactional
	public Person getPersonByEmail(String email) {
		// TODO Auto-generated method stub
		System.out.println("hello--------------------login---------------------DAO>");
		System.out.println("email "+ email);
		
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Person as p where p.email= :email");
		query.setString("email",email );
		System.out.println(query.list());
		Person person = (Person) query.uniqueResult();
		System.out.println("----------------> "+person);
		return person;
				
		
	}

}
