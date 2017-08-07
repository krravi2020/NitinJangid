package com.nitin.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.nfunk.jep.JEP;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nitin.spring.dao.CalculateDAO;
import com.nitin.spring.dao.PersonDAO;
import com.nitin.spring.model.CalculatorPojo;

import javassist.compiler.ast.Symbol;


@Service
public class CalculatorServiceImpl implements CalculatorService{

	private JEP parser;
    private String memory;
    
    private CalculateDAO calculateDAO;
    public void setCalculateDAO(CalculateDAO calculateDAO) {
		this.calculateDAO = calculateDAO;
	}

    public CalculatorServiceImpl() {
        parser = new JEP();
        parser.initFunTab();                  // clear the contents of the function table
        parser.addStandardFunctions();
        parser.addStandardConstants();
        parser.setImplicitMul(true);
         memory = "0";
    }
	
	@Override
	@Transactional
	public JSONObject calculate(String op) {
		// TODO Auto-generated method stub
		System.out.println("------------------------------------------------->");
		System.out.println("op in service "+ op);
		JSONObject jObject = new JSONObject();
		 try {
			 	String leftvalue = null;
			 	String rightvalue = null;
			 	String operator = null;
			 	jObject.put("status", "success");
			 	System.out.println("op do "+ op);
			 	op = op.replaceAll(" ", "+");
			 	
			 	
			 	System.out.println("op done "+ op);

	            parser.parseExpression(op);
	            String res = String.valueOf(parser.getValue());
	            System.out.println("result "+res);
	            if(res == null)
	                res = "Syntex Error";
	            else{
	                if(res.equalsIgnoreCase("Infinity") || res.equalsIgnoreCase("NaN"))
	                    res = "Mathmetical Error" ;
	            }
	            
	            float result = Float.parseFloat(res);
	            
	            if (op.contains("+")){
	            	leftvalue = getLeftValue(op,"+");
	            	rightvalue = getRightValue(op,"+");
	            	operator = "+";
	            }
	            else if (op.contains("-")){
	            	leftvalue = getLeftValue(op,"-");
	            	rightvalue = getRightValue(op,"-");
	            	operator = "-";
	            }
	            else if (op.contains("*")){
	            	leftvalue = getLeftValue(op,"*");
	            	rightvalue = getRightValue(op,"*");
	            	operator = "*";
	            }
	            else if (op.contains("/")){
	            	leftvalue = getLeftValue(op,"/");
	            	rightvalue = getRightValue(op,"/");
	            	operator = "÷";
	            }
	            
	            
	            CalculatorPojo calculatorPojo = new CalculatorPojo();
	            
	            calculatorPojo.setFirstNum(Integer.parseInt(leftvalue));
	            calculatorPojo.setOperator(operator);
	            calculatorPojo.setSecondNum(Integer.parseInt(rightvalue));
	            calculatorPojo.setResult(result);
	            System.out.println("-------->>>>>>>>");
	            System.out.println(calculatorPojo.getFirstNum());
	            System.out.println(calculatorPojo.getOperator());
	            System.out.println(calculatorPojo.getSecondNum());
	            System.out.println(calculatorPojo.getResult());
	            calculateDAO.addHistoryforCalc(calculatorPojo);
	            
	            jObject.put("Result", fixResult(res));
	            return jObject;
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	            return jObject;
	        }
		
	}

	
	
	

	
	private String fixResult(String re){
        if(re.endsWith(".0"))
            re = re.substring(0, re.length()-2);
        return re;
    }
	
	private String getLeftValue(String op, String sign) throws Exception {
        int indexSign = op.indexOf(sign);
        try {
            if (indexSign == 0)
                throw new Exception();
            
            int indexStart = 0;
            for(int i = indexSign-1; i>-1; i--){
                if(op.charAt(i)== ' '){
                    indexStart = i;
                    break;
                }
            }
            
            return op.substring(indexStart, indexSign);
        } catch (IndexOutOfBoundsException e) {
            throw new Exception();
        }

    }
	private String getRightValue(String op, String sign) throws Exception {
        try {
            int indexSign = op.indexOf(sign);
            if(indexSign == op.length()-1)
                throw new Exception();
            
            int indexEnd = op.length();
            for(int i = indexSign+2; i < op.length() ; i++){
                if(op.charAt(i)== ' '){
                    indexEnd = i;
                    break;
                }
            }
            
            return op.substring(indexSign+1, indexEnd);
        } catch (IndexOutOfBoundsException e) {
            throw new Exception();
        }

    }

	@Override
	@Transactional
	public JSONObject getHistory() {
		// TODO Auto-generated method stub
		JSONObject jObject = new JSONObject();
		try{
			
			List<CalculatorPojo> listhistory = calculateDAO.getHistoryforCalc();
			System.out.println(listhistory);
			List list = new ArrayList();
			for(CalculatorPojo p : listhistory){
				System.out.println(p.getFirstNum());
				JSONObject jObject2 = new JSONObject();
				jObject2.put("id", p.getId());
				jObject2.put("firstInput", p.getFirstNum());
				jObject2.put("secondInput", p.getSecondNum());
				jObject2.put("operator", p.getOperator());
				jObject2.put("result", p.getResult());
				list.add(jObject2);
			}
			jObject.put("historyList", list);
			jObject.put("status", "success");
			return jObject;
			
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}
}
