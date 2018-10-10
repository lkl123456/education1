package com.adks.gclc.orgweb;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration  
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class AdminControllerTest {
	@Autowired  
    private WebApplicationContext webApplicationContext; 
	
	private MockMvc mockMvc; 
	private MockHttpServletRequest request;  
	
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();  
        request = new MockHttpServletRequest();  
	}
	@Ignore
	@Test
	public void testGetAdminListJson() throws Exception {
		RequestBuilder builder = MockMvcRequestBuilders.get("/admin/getAdminListJson").param("page", "1").param("rows", "10");
		mockMvc.perform(builder).andExpect(status().isOk()).andDo(print());
	}
}
