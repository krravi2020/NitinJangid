package com.nitin.spring;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nitin.spring.model.CalculatorPojo;
import com.nitin.spring.service.CalculatorService;
import com.nitin.spring.service.PersonService;

@Controller
@Scope("session")
public class CalculatorController {

	private CalculatorService calculatorService;
    

	@Autowired(required=true)
	@Qualifier(value="calculatorService")
	public void setPersonService(CalculatorService cs){
		
		this.calculatorService = cs;
	}
   
	
	@RequestMapping(value="/calculator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String calcular(@RequestParam("op") String op){
		System.out.println("op in controller----->>> "+op);
		JSONObject jObject = new JSONObject();
		
		try {
			jObject.put("status", "Fail");
			jObject = calculatorService.calculate(op);
			//jObject.put("result", result);
			System.out.println("done");
			return jObject.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			System.out.println("done");
			return jObject.toString();
		}
		
        
    }
	@RequestMapping(value="/getHistory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String history(CalculatorPojo cal){
		JSONObject jObject = new JSONObject();
		
		try {
			jObject.put("status", "Fail");
			jObject = calculatorService.getHistory();
			return jObject.toString();
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	
	
	}
}
