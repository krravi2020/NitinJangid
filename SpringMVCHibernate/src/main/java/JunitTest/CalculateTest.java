package JunitTest;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nitin.spring.CalculatorController;
import com.nitin.spring.service.CalculatorService;





@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
public class CalculateTest {

	private MockMvc mockMvc;
	
	@Mock
	private CalculatorService calculatorService;
	
	@InjectMocks
	private CalculatorController calculatorController;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(calculatorController).build();
		 
	 }
	
	@Test
	public void testingAdd() throws Exception{
		
		JSONObject jObject = new JSONObject();
		jObject.put("status", "success");
		jObject.put("Result", "6");
		
		String op = "2+3"; 
		
		when(calculatorService.calculate(op)).thenReturn(jObject);
		
		
        
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		MockHttpServletRequestBuilder request = post("/calculator?op=2+3");

		request.accept(MEDIA_TYPE_JSON_UTF8);
		request.contentType(MEDIA_TYPE_JSON_UTF8);
		
		MvcResult result = mockMvc.perform(request)
		    .andDo(print())
		    .andExpect(status().isOk())
		    .andReturn();

		String content = result.getResponse().getContentAsString();
		

		verify(calculatorService, times(1)).calculate(op);
		verifyNoMoreInteractions(calculatorService);
	}
}
