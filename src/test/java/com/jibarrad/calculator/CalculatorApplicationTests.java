package com.jibarrad.calculator;

import com.jibarrad.calculator.config.TestSecurityConfig;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class CalculatorApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private String apiBaseUrl = "/v1";

	@Test
	public void testLogin() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.post(apiBaseUrl+"/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\": \"admin@calculator.com\", \"password\": \"admin123\"}")
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Login successful"))
				.andExpect(MockMvcResultMatchers.header().exists("Authorization"))
				.andExpect(MockMvcResultMatchers.header().string("Authorization", org.hamcrest.Matchers.not(org.hamcrest.Matchers.emptyString())));
	}

	private String performLoginAndGetToken() throws Exception {
		String loginRequestBody = "{\"username\": \"admin@calculator.com\", \"password\": \"admin123\"}";

		return mockMvc.perform(MockMvcRequestBuilders
						.post(apiBaseUrl+"/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loginRequestBody)
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn()
				.getResponse()
				.getHeader("Authorization");
	}

	@Test
	public void testTopUp() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		mockMvc.perform(MockMvcRequestBuilders
						.post(apiBaseUrl+"/users/topup?userId=1")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\": \"admin@calculator.com\", \"password\": \"admin123\"}")
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("admin@calculator.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(20));
	}

	@Test
	public void testAddition() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		mockMvc.perform(MockMvcRequestBuilders
						.post(apiBaseUrl+"/calculator/addition")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userId\": 1, \"num1\": 2, \"num2\": 2 }")
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("4.0"));
	}

	@Test
	public void testSubtraction() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		mockMvc.perform(MockMvcRequestBuilders
						.post(apiBaseUrl+"/calculator/subtraction")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userId\": 1, \"num1\": 5, \"num2\": 2 }")
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("3.0"));
	}

	@Test
	public void testMultiplication() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		mockMvc.perform(MockMvcRequestBuilders
						.post(apiBaseUrl+"/calculator/multiplication")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userId\": 1, \"num1\": 5, \"num2\": 2 }")
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("10.0"));
	}

	@Test
	public void testDivision() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		mockMvc.perform(MockMvcRequestBuilders
						.post(apiBaseUrl+"/calculator/division")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userId\": 1, \"num1\": 2, \"num2\": 2 }")
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("1.0"));
	}

	@Test
	public void testDivisionByZero() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		mockMvc.perform(MockMvcRequestBuilders
						.post(apiBaseUrl+"/calculator/division")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userId\": 1, \"num1\": 2, \"num2\": 0 }")
				)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Division by zero is not allowed."));
	}

	@Test
	public void testSquareRoot() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		mockMvc.perform(MockMvcRequestBuilders
						.post(apiBaseUrl+"/calculator/square_root")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userId\": 1, \"num1\": 4 }")
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("2.0"));
	}

	@Test
	public void testSquareRootNegative() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		mockMvc.perform(MockMvcRequestBuilders
						.post(apiBaseUrl+"/calculator/square_root")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userId\": 1, \"num1\": -4 }")
				)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Square root of a negative number is not allowed."));
	}

	@Test
	public void testRandomString() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.post(apiBaseUrl+"/randomstring")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"userId\": 1, \"num\": 2, \"len\": 10, \"digits\": true, \"upperAlpha\": true, \"lowerAlpha\": true, \"unique\": true}")
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String responseContent = result.getResponse().getContentAsString();
		JSONArray jsonArray = new JSONArray(responseContent);

		Assertions.assertEquals(2, jsonArray.length()); // Check if there are 2 strings

		for (int i = 0; i < jsonArray.length(); i++) {
			String randomString = jsonArray.getString(i);
			System.out.println(randomString);
			// Check if the string matches the pattern
			Assertions.assertTrue(randomString.matches("^[0-9a-zA-Z]{10}$"));
		}
	}

	@Test
	public void testGetOperations() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		mockMvc.perform(MockMvcRequestBuilders
						.get(apiBaseUrl+"/operations")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("ADDITION"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].cost").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].type").value("SUBTRACTION"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].cost").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].type").value("MULTIPLICATION"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].cost").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$[3].id").value(4))
				.andExpect(MockMvcResultMatchers.jsonPath("$[3].type").value("DIVISION"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[3].cost").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$[4].id").value(5))
				.andExpect(MockMvcResultMatchers.jsonPath("$[4].type").value("SQUARE_ROOT"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[4].cost").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$[5].id").value(6))
				.andExpect(MockMvcResultMatchers.jsonPath("$[5].type").value("RANDOM_STRING"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[5].cost").value(4));
	}

	@Test
	public void testGetCurrentUser() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		mockMvc.perform(MockMvcRequestBuilders
						.get(apiBaseUrl+"/users/current")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("admin@calculator.com"));
	}

	@Test
	public void testGetRecordsCount() throws Exception {
		String authToken = "Bearer " + performLoginAndGetToken();

		mockMvc.perform(MockMvcRequestBuilders
						.get(apiBaseUrl+"/records?userId=1&page=0&elements=5&sortBy=date_time&sortDirection=DESC&operationResult=")
						.header("Authorization", authToken)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
	}
}
