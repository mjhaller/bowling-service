package bowling;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.IntNode;
import com.google.common.collect.Iterators;

/**
 * Placeholder for testing http
 * 
 * @author mhaller
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BowlingServiceApplication.class)
@WebAppConfiguration
public class GameControllerTest {

	ObjectMapper mapper = new ObjectMapper();
	MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext webApplicationContext;

	
	@Before
	public void setup()
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
	        	.build();
	}
	
	
	@Test
	public void gameCreate() throws Exception {
		JsonNode node = testPost("/game", "{\"player\" : \"levi\"}");
		assertEquals(10, Iterators.size(node.get("frames").elements()));
	}
	
	@Test
	public void perfectGame() throws Exception
	{
		gameCreate();
		JsonNode node = null;
		for (int i = 1; i <= 12 ; i++)
			node = testPost("/game/1/roll", "{\"pins\" : \"10\"}");
		assertEquals(IntNode.valueOf(300), node.get("totalScore"));
		
	}
	
	private JsonNode testPost(String uri, String data) throws Exception
	{
		MvcResult result = mockMvc.
			    perform(
			    	post(uri)
			    	.contentType(MediaType.APPLICATION_JSON)
			    	.content(data)
				)
			    .andExpect(status().isOk())
			    .andReturn();
		return mapper.readValue(result.getResponse().getContentAsString(), JsonNode.class);
	}

}
