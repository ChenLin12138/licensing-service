package com.chenlin.licenseservice.controllers.test;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Chen Lin
 * @date 2019-05-12
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LicenseServiceControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	protected MockMvc mvc;
	
	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void getLicensesTest() throws Exception {
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/organizatons/1/licenses/2")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				//we can add request parm here
				//.param("5", "8")
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.id",is("2")))
		.andExpect(jsonPath("$.organizationId",is("1")))
		.andExpect(jsonPath("$.productName",is("Teleco")))
		.andExpect(jsonPath("$.licenseType",is("Seat")))
		.andDo(MockMvcResultHandlers.print())
		.andReturn();
		
		System.out.println(result);
				
	}
	
	

}
