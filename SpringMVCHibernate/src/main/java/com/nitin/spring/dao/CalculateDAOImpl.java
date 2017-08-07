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

import com.nitin.spring.model.CalculatorPojo;
import com.nitin.spring.model.Person;

@Repository
public class CalculateDAOImpl implements CalculateDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(CalculateDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}


	@Override
	public void addHistoryforCalc(CalculatorPojo cal) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(cal);
		logger.info("Added History="+cal);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<CalculatorPojo> getHistoryforCalc() {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<CalculatorPojo> historyList = session.createQuery("from CalculatorPojo").list();
		
		return historyList;
		
	}

	

}
