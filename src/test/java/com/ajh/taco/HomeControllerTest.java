package com.ajh.taco;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.ajh.taco.controller.HomeController;
import com.ajh.taco.security.SecurityConfigProps;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
@ContextConfiguration(classes = {HomeController.class, SecurityConfigProps.class}) // Why should set explicitly with web security?
//@TestPropertySource(locations="test.yml")
public class HomeControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(value="andy") // How to remove hard-coding?
	public void testHomePage() throws Exception {
		System.out.println("### Run testHomePage()...");
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("home"))
			.andExpect(content().string(containsString("Welcome to...")));
	}

}
