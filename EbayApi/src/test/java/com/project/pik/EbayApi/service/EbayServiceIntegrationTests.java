package com.project.pik.EbayApi.service;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ebay.soap.eBLBaseComponents.CategoryType;
import com.project.pik.EbayApi.EbayDataRestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EbayDataRestApplication.class)
public class EbayServiceIntegrationTests {

	@Autowired
	EbayCategoriesService ebayCategoriesService;

	@Test
	public void findAllMainCategories() {

		List<CategoryType> mainCategories = this.ebayCategoriesService.getMainCategories();
		assertThat(mainCategories.size(), is(greaterThan(10)));
	}
}