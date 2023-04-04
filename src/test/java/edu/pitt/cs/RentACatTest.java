package edu.pitt.cs;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object

	@Before
	public void setUp() throws Exception {
		// Turn on automatic bug injection in the Cat class, to emulate a buggy Cat.
		// Your unit tests should work regardless of these bugs.
		Cat.bugInjectionOn = true;

		// INITIALIZE THE TEST FIXTURE
		// 1. Create a new RentACat object and assign to r
		r = RentACat.createInstance();

		// 2. Create an unrented Cat with ID 1 and name "Jennyanydots", assign to c1
		c1 = Mockito.mock(Cat.class);
		Mockito.when(c1.getName()).thenReturn("Jennyanydots");
		Mockito.when(c1.getId()).thenReturn(1);
		Mockito.when(c1.getRented()).thenReturn(false);
		Mockito.when(c1.toString()).thenReturn("ID 1. Jennyanydots");

		// 3. Create an unrented Cat with ID 2 and name "Old Deuteronomy", assign to c2
		c2 = Mockito.mock(Cat.class);
		Mockito.when(c2.getName()).thenReturn("Old Deuteronomy");
		Mockito.when(c2.getId()).thenReturn(2);
		Mockito.when(c2.getRented()).thenReturn(false);
		Mockito.when(c2.toString()).thenReturn("ID 2. Old Deuteronomy");		

		// 4. Create an unrented Cat with ID 3 and name "Mistoffelees", assign to c3
		c3 = Mockito.mock(Cat.class);
		Mockito.when(c3.getName()).thenReturn("Mistoffelees");
		Mockito.when(c3.getId()).thenReturn(3);
		Mockito.when(c3.getRented()).thenReturn(false);
		Mockito.when(c3.toString()).thenReturn("ID 3. Mistoffelees");

	}

	@After
	public void tearDown() throws Exception {
		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 * </pre>
	 */
	// SPENCER
	@Test
	public void testGetCatNullNumCats0() {
		// Precondition met by setUp method

		// Execution step
		Cat cat = r.getCat(2);

		// Post condition, assert cat is empty, does not exist
		assertNull("Cat ID 2 is not null", cat);
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 */
	// SPENCER
	@Test
	public void testGetCatNumCats3() {
		// Preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		// Execution steps
		Cat cat = r.getCat(2);

		// Postconditions
		// Confirm cat exists
		assertNotNull("Returned cat is null", cat);
		// Confirm cat has correct ID
		assertEquals("Returned cat does not have ID = 2", 2, cat.getId());
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */
	// SPENCER
	@Test
	public void testCatAvailableFalseNumCats0() {
		// Preconditions met by setUp method
		// Execution step
		Boolean ret = r.catAvailable(2);

		// Postcondition
		// Confirm false, cat 2 is not available
		assertFalse("Returned value is true; cat is available", ret);
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c3 is rented.
	 *                c1 and c2 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is true.
	 * </pre>
	 */
	// RAINIER
	@Test
	public void testCatAvailableTrueNumCats3() {
		// Preconditions
		// Add cats
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		// Mock cat 3 to be "rented"
		Mockito.when(c3.getRented()).thenReturn(true);

		// Execution steps
		boolean ret = r.catAvailable(2);

		// Postcondition
		// Ensure cat 2 is available
		assertTrue("Returned value is false: cat is not available", ret);
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 *                c1 and c3 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */
	// RAINIER
	@Test
	public void testCatAvailableFalseNumCats3() {
		// Preconditions
		// Add cats
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		// Mock cat 2 to be "rented"
		Mockito.when(c2.getRented()).thenReturn(true);

		// Execution steps
		boolean ret = r.catAvailable(2);

		// Post conditions
		// Ensure value is false, cat 2 is unavailable to rent 
		assertFalse("Returned value is true: cat is available", ret);
	}

	/**
	 * Test case for boolean catExists(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */
	// RAINIER
	@Test
	public void testCatExistsFalseNumCats0() {
		// Preconditions met by setUp method

		// Execution step
		boolean ret = r.catExists(2);

		// Postconditions
		// Ensure returned value is false, cat 2 does not exist
		assertFalse("Returned value is true: cat exists", ret);
	}

	/**
	 * Test case for boolean catExists(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is true.
	 * </pre>
	 */
	// RAINIER
	@Test
	public void testCatExistsTrueNumCats3() {
		// Preconditions
		// Add the cats
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		
		// Execution steps
		boolean ret = r.catExists(2);

		// Postconditions
		// Ensure returned value is true, cat 2 does exist
		assertTrue("Returned value is false: cat does not exist", ret);
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */
	// SPENCER
	@Test
	public void testListCatsNumCats0() {
		// Preconditions met by setUp method

		// Execution step
		// List cats, store in catList string
		String catList = r.listCats();

		// Postconditions
		// Ensure returned string is empty
		assertEquals("Cat list is not empty","", catList);
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */
	// SPENCER
	@Test
	public void testListCatsNumCats3() {
		// Preconditions
		// Add cats
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		// Execution steps
		// generate listCats, store in catList string
		String catList = r.listCats();

		// Postconditions
		// Assert catList string is equal to expected string
		assertEquals("Return value is not expected cat list", "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n", catList);
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */
	// SPENCER
	@Test
	public void testRentCatFailureNumCats0() {
		boolean ret = r.rentCat(2);
		assertFalse("Returned value is true: cat was rented", ret);
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c1.rentCat(), c2.rentCat(), c3.rentCat() are never called.
	 * </pre>
	 * 
	 * Hint: See sample_code/mockito_example/NoogieTest.java in the course
	 * repository for an example of behavior verification. Refer to the
	 * testBadgerPlayCalled method.
	 */
	// SPENCER
	@Test
	public void testRentCatFailureNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		Mockito.when(c2.getRented()).thenReturn(true);

		Boolean catRented = r.rentCat(c2.getId());
		
		assertFalse("Succesfully rented cat", catRented);
		Mockito.verify(c1, Mockito.times(0)).rentCat();
		Mockito.verify(c2, Mockito.times(0)).rentCat();
		Mockito.verify(c3, Mockito.times(0)).rentCat();
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */
	// RAINIER
	@Test
	public void testReturnCatFailureNumCats0() {
		// TODO
		boolean ret = r.returnCat(2);
		assertFalse("Returned value is true: cat is returned", ret);
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2.returnCat() is called exactly once.
	 *                 c1.returnCat() and c3.returnCat are never called.
	 * </pre>
	 * 
	 * Hint: See sample_code/mockito_example/NoogieTest.java in the course
	 * repository for an example of behavior verification. Refer to the
	 * testBadgerPlayCalled method.
	 */
	// RAINIER
	@Test
	public void testReturnCatNumCats3() {
		// TODO
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		Mockito.when(c2.getRented()).thenReturn(true);

		Boolean catReturned = r.returnCat(c2.getId());
		
		assertTrue("Failed to rent cat", catReturned);
		Mockito.verify(c1, Mockito.times(0)).returnCat();
		Mockito.verify(c2, Mockito.times(1)).returnCat();
		Mockito.verify(c3, Mockito.times(0)).returnCat();
	}
}
