package com.nitin.spring;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nitin.spring.model.Person;
import com.nitin.spring.service.PersonService;

@Controller

public class PersonController {
	
	private PersonService personService;
	
	private JSONObject jsonRequest = new JSONObject();

	
	@Autowired(required=true)
	@Qualifier(value="personService")
	public void setPersonService(PersonService ps){
		
		this.personService = ps;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody String savePerson(@RequestBody @Valid final Person person) {
		try{
			JSONObject jObject = new JSONObject();
			jObject.put("status", "fail");
			System.out.println(person);
			System.out.println("hello--------------------Registeor---------------------controller>");
			jObject = personService.savePersonService(person);
			
			
			return jObject.toString();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String login(@RequestBody Person person){
		System.out.println("hello--------------------login---------------------controller>");
		JSONObject jObject = new JSONObject();
		try{
			jObject.put("statuss","fail");
			jObject = personService.login(person);
			return jObject.toString();
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
		
	}
	
}
