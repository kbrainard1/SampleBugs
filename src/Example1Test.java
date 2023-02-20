import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class Example1Test {

	@Test
	public void addGetTest() {
		int id = 5;
		String value = "five";
		Example1 example = new Example1();
		example.put(id, value);
		Assert.assertEquals("five", example.get(5));
	}
}
