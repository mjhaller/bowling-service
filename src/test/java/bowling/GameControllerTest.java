package bowling;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

/**
 * Placeholder for testing http
 * 
 * @author mhaller
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BowlingServiceApplication.class)
@WebAppConfiguration
public class GameControllerTest implements Loggable {

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
	
	
	private JsonNode gameCreate() throws Exception {
		JsonNode player = testPost("/api/players", "{\"name\" : \"levi\"}");
		JsonNode node = testPost("/api/games", String.format("{ \"player\" : \"%s\"}",player.get("_links").get("player").get("href").asText()));
		return node;
	}
	
	@Test
	public void createGame() throws Exception
	{
		assertThat(gameCreate().get("id").intValue(), greaterThan(0));
	}
	
	@Test
	public void perfectGame() throws Exception
	{
		
		JsonNode node = gameCreate();
		String gameUri = node.get("_links").get("self").get("href").asText();
		node = testGet(gameUri);
		String uri = node.get("_links").get("nextFrame").get("href").asText();
		while (uri != null)
		{
			log().info("posting to uri " + uri);
			node = testPost(uri + "/rolls", "{\"pins\" : \"10\"}");
			uri = null;
			if ( node.get("_links").get("nextFrame") != null)
				uri = node.get("_links").get("nextFrame").get("href").asText();
		}
		node = testGet(gameUri);
		assertEquals(IntNode.valueOf(300), node.get("totalScore"));
		
	}
	
	private JsonNode testPost(String uri, String data) throws Exception
	{
		MvcResult result = mockMvc.
			    perform(
			    	post(uri)
			    	.contentType(MediaType.APPLICATION_JSON)
			    	.accept(MediaType.APPLICATION_JSON)
			    	.content(data)
				)
			    .andExpect(status().is2xxSuccessful())
			    .andReturn();
		return mapper.readValue(result.getResponse().getContentAsString(), JsonNode.class);
	}
	
	private JsonNode testGet(String uri) throws Exception
	{
		MvcResult result = mockMvc.
			    perform(
			    	get(uri)
			    	.accept(MediaType.APPLICATION_JSON)
				)
			    .andExpect(status().isOk())
			    .andReturn();
		return mapper.readValue(result.getResponse().getContentAsString(), JsonNode.class);
	}

}
