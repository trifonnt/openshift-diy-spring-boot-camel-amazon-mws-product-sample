package demo;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author mnachev
 */
public class ListMatchingProductsSampleNGTest {

	public ListMatchingProductsSampleNGTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@BeforeMethod
	public void setUpMethod() throws Exception {
	}

	@AfterMethod
	public void tearDownMethod() throws Exception {
	}

	/**
	 * Test of main method, of class ListMatchingProductsSample.
	 */
	@Test
	public void testMain() {
		System.out.println("main");
		String[] args = new String[] {};
		ListMatchingProductsSample.main(args);
	}
}