package com.project.pik.EbayApi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EbayDataRestApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FoundResultsRestTests {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Autowired
	private EntityManager entityManager;

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@After
	public void cleanup() {
		entityManager
				.createNativeQuery(
						"DELETE FROM found_results where order_id in (select order_id from orders "
						+ "where user_id in (select user_id from users where login='test'))")
				.executeUpdate();
	}

	@Test
	public void testAsyncResponse() throws Exception {
		MvcResult resultActions = this.mvc.perform(get("/foundresults/async/test")).andExpect(request().asyncStarted())
				.andReturn();

		this.mvc.perform(asyncDispatch(resultActions)).andExpect(status().is2xxSuccessful());
	}

}
