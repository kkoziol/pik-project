package com.project.pik.EbayApi;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EbayDataRestApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// Separate profile for web tests to avoid clashing databases
public class EbayRestIntegrationTests {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	@Test
	public void testMainCategories() throws Exception {
		this.mvc.perform(get("/ebay/categories/maincategories")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("categoryID")));
	}

	@Test
	public void testSubCategories() throws Exception {
		this.mvc.perform(get("/ebay/categories/subcategories/20081")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("categoryID")));
	}

	@Test
	public void testSpecificsOfCategories() throws Exception {
		this.mvc.perform(get("/ebay/categories/specifics/870")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("Color")));
	}

	@Test
	public void testBestMatchOfCategories() throws Exception {
		this.mvc.perform(get("/ebay/categories/bestmatch/test")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("categoryId")));
	}

	@Test
	public void testKeywordSearching() throws Exception {
		this.mvc.perform(get("/ebay/items/search/drapala/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("item")));
	}

	@Test
	public void testBestMatchSearching() throws Exception {
		this.mvc.perform(get("/ebay/items/bestmatch/test")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("item")));
	}

	@Test
	public void testCheapestSearching() throws Exception {
		this.mvc.perform(get("/ebay/items/cheapest/test/20081")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("item")));
	}
}