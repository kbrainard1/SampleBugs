import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class Example2Test {

	@Test
	public void runSimpleTest() throws Exception {
		Example2 example2 = new Example2();
		Assert.assertEquals(5, example2.findSumOfSquares(2, 1));
	}
	
	@Test
	public void runThreadTest() throws Exception {
		Example2 example2 = new Example2();
		long val1 = example2.findSumOfSquares(100, 1);
		long val2 = example2.findSumOfSquares(100, 2);
		Assert.assertEquals(val1, val2);
	}
	
//	@Test
//	public void runThoroughThreadTest() throws Exception {
//		Example2 example2 = new Example2() {
//			@Override
//			protected void testHook() {
//				try {
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					Thread.currentThread().interrupt();
//				}
//			}
//		};
//		
//		long val2 = example2.findSumOfSquares(5, 2);
//		Assert.assertEquals(55, val2);
//	}
}
