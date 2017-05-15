package com.project.pik.EbayApi.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.project.pik.EbayApi.ApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest(classes = ApplicationConfig.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class EbayServiceImplTests {
	/**
	 * Category: Computers/Tablets & NetworkingDesktops & All-In-One
	 * ComputersApple Desktops & All-In-One Computers
	 */
	private final static String desktopAndAllInOneComputersCategorieId = "111418";
	private final static Logger logger = Logger.getLogger(EbayServiceImplTests.class);

	@Autowired
	private EbayServiceImpl ebayService;

	@Test
	public void testgetCategorySpecificsByCategoryId() {
		String asString = castMapStringListStringTo(
				ebayService.getCategorySpecificsByCategoryId(desktopAndAllInOneComputersCategorieId));
		logger.debug(asString);
	}

	public String castMapStringListStringTo(Map<String, List<String>> toCast) {
		StringBuilder builder = new StringBuilder("Found refinments: \n");
		toCast.forEach((k, v) -> builder.append(k + ":" + v.stream().collect(Collectors.joining(", "))));

		return builder.toString();
	}

}
