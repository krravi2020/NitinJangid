package com.nitin.spring.service;

import org.json.JSONObject;

public interface CalculatorService {

	public JSONObject calculate(String op);
	public JSONObject getHistory();
	
}
