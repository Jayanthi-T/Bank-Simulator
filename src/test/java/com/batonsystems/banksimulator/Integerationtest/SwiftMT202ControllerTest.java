package com.batonsystems.banksimulator.Integerationtest;

import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.entity.SWIFTMT202Request;
import com.batonsystems.banksimulator.repository.AccountRepository;
import com.batonsystems.banksimulator.repository.BranchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//Tests generateSWIFTMessage
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class SwiftMT202ControllerTest {

	@Autowired
	BranchRepository branchRepository;
	@Autowired
	AccountRepository accountRepo;
	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	private MockMvc mockMvc;
	@Value("${swift.mt202.request.path}")
	private String requestQueue;
	@Value("${swift.mt202.response.path}")
	private String responseQueue;


	//Tests SWIFTMT202Conroller
	@Test
	@DirtiesContext
	public void testGenerateSWIFTMessageOfSwiftController() throws Exception {

		Branch branch = new Branch("005943","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
		branchRepository.save(branch);

		Account acc1=new Account(1,"123456789001","Jay","Savings",60000);
		acc1.setBranch(branch);
		accountRepo.save(acc1);
		Account acc2=new Account(2,"345678129001","Ajay","Savings",30000);
		acc2.setBranch(branch);
		accountRepo.save(acc2);

		String date = "220814";
		SWIFTMT202Request trans = new SWIFTMT202Request("yes","INR",5000,"123456789001","345678129001",date,"NUS7013604301361");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JSR310Module());
		String swiftMsgDetails = mapper.writeValueAsString(trans);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/generateSWIFTMT202Message")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(swiftMsgDetails);

		String expected32A = ":32A:220814INR5000,";
		String expected53B = ":53B:/123456789001";
		String expected58A = ":58A:/345678129001";

		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
				.andExpect(content().string(org.hamcrest.Matchers.containsString(expected32A)))
				.andExpect(content().string(org.hamcrest.Matchers.containsString(expected53B)))
				.andExpect(content().string(org.hamcrest.Matchers.containsString(expected58A)));

	}

	//Tests the message in the queue
	@Test
	@DirtiesContext
	public void testGenerateSWIFTMessageUsingQueue() throws Exception {

		Branch branch = new Branch("005943","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
		branchRepository.save(branch);

		Account acc1=new Account(1,"123456789001","Jay","Savings",60000);
		acc1.setBranch(branch);
		accountRepo.save(acc1);
		Account acc2=new Account(2,"345678129001","Ajay","Savings",30000);
		acc2.setBranch(branch);
		accountRepo.save(acc2);

		String date = "220814";
		SWIFTMT202Request trans = new SWIFTMT202Request("yes","INR",5000,"123456789001","345678129001",date,"NUS7013604301361");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JSR310Module());
		String swiftMsgDetails = mapper.writeValueAsString(trans);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/generateSWIFTMT202Message")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(swiftMsgDetails);

		MvcResult mvcResult = mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
				.andReturn();

		String responseMsg = mvcResult.getResponse().getContentAsString();

		this.jmsTemplate.convertAndSend(requestQueue,responseMsg);

		this.jmsTemplate.setReceiveTimeout(1_000);

		String actualMsg = this.jmsTemplate.receiveAndConvert(responseQueue).toString();

		String expected32A = ":32A:220814INR5000,";
		String expected53B = ":53B:/123456789001";
		String expected58A = ":58A:/345678129001";

		assertAll(
				()->assertTrue(actualMsg.contains(expected32A)),
				()->assertTrue(actualMsg.contains(expected53B)),
				()->assertTrue(actualMsg.contains(expected58A))
		);
	}


}
