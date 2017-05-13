/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * Integration tests for {@link EbayService}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EbayDataRestApplication.class)
public class EbayServiceIntegrationTests {

	@Autowired
	EbayService ebayService;

	@Test
	public void findAllMainCategories() {

		List<CategoryType> mainCategories = this.ebayService.getMainCategories();
		assertThat(mainCategories.size(), is(greaterThan(10)));
	}
}
