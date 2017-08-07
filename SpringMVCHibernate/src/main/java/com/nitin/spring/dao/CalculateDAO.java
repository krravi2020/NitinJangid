package com.nitin.spring.dao;

import java.util.List;

import org.json.JSONObject;

import com.nitin.spring.model.CalculatorPojo;
import com.nitin.spring.model.Person;

public interface CalculateDAO {

	public void addHistoryforCalc(CalculatorPojo cal);
	public List<CalculatorPojo> getHistoryforCalc();
	
	
}
