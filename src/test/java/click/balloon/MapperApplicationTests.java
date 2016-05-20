package click.balloon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import bowling.BowlingServiceApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BowlingServiceApplication.class)
@WebAppConfiguration
public class MapperApplicationTests {

	@Test
	public void contextLoads() {
	}

}
