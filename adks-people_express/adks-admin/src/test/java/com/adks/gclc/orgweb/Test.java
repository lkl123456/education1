package com.adks.gclc.orgweb;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration  
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class Test {

	@Autowired  
    private WebApplicationContext webApplicationContext; 
	
	private MockMvc mockMvc; 
	private MockHttpServletRequest request;  
	
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();  
        request = new MockHttpServletRequest();  
	}
	@org.junit.Test
	public void testGetAdminListJson() throws Exception {
		RequestBuilder builder = MockMvcRequestBuilders.get("/userIndex/home");
		mockMvc.perform(builder).andExpect(status().isOk()).andDo(print());
	}
	
}
