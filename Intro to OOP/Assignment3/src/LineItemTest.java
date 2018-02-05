/*
 * Your LineItem class must pass all the tests contained here.  You may
 * certainly add more tests if you wish.
 */
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;
//TODO test file save of a LineItem object to a binary file.
public class LineItemTest {

	public Pizza pizza1;

	@Before
	public void setUp() throws Exception {
		pizza1 = new Pizza("medium", 1, 1, 1);
	}

	// Test with legal arguments.
	// Also testing all accessors and toString.
	// One parameter constructor.
	@Test
	public void testLineItemPizza() throws Exception {
		LineItem line1 = new LineItem(pizza1);
		assertEquals(pizza1, line1.getPizza());
		assertEquals(1, line1.getNumber());
		assertEquals(12.0, line1.getCost(), 0.01);
		assertEquals(" 1 " + pizza1.toString(), line1.toString());
	}

	// Two parameter constructor.
	@Test
	public void testLineItemIntPizza() throws Exception {
		LineItem line1 = new LineItem(1, pizza1);
		assertEquals(pizza1, line1.getPizza());
		assertEquals(1, line1.getNumber());
		assertEquals(12.0, line1.getCost(), 0.01);
		assertEquals(" 1 " + pizza1.toString(), line1.toString());
	}
	@Test
	public void testLineItemIntPizza1() throws Exception {
		LineItem line1 = new LineItem(100, pizza1);
		assertEquals(pizza1, line1.getPizza());
		assertEquals(100, line1.getNumber());
		assertEquals(12.0 * 100, line1.getCost(), 0.01);
		assertEquals("100 " + pizza1.toString(), line1.toString());
	}
	@Test
	public void testLineItemIntPizza2() throws Exception {
		LineItem line1 = new LineItem(10, pizza1);
		assertEquals(pizza1, line1.getPizza());
		assertEquals(10, line1.getNumber());
		assertEquals(12.0 * 10, line1.getCost(), 0.01);
		assertEquals("10 " + pizza1.toString(), line1.toString());
	}
	@Test
	public void testLineItemIntPizza3() throws Exception {
		LineItem line1 = new LineItem(50, pizza1);
		assertEquals(pizza1, line1.getPizza());
		assertEquals(50, line1.getNumber());
		assertEquals(12.0 * 50, line1.getCost(), 0.01);
		assertEquals("50 " + pizza1.toString(), line1.toString());
	}

	// Test with illegal arguments.
	// One parameter constructor.
	@Test (expected=IllegalPizza.class)
	public void testLineItemPizza1() throws Exception {
		LineItem line1 = new LineItem(null);
		assertNull(line1);
	}

	// Two parameter constructor.
	@Test (expected=IllegalPizza.class)
	public void testLineItemIntPizza4() throws Exception {
		LineItem line1 = new LineItem(-1, pizza1);
		assertNull(line1);
	}
	@Test (expected=IllegalPizza.class)
	public void testLineItemIntPizza5() throws Exception {
		LineItem line1 = new LineItem(0, pizza1);
		assertNull(line1);
	}
	@Test (expected=IllegalPizza.class)
	public void testLineItemIntPizza6() throws Exception {
		LineItem line1 = new LineItem(101, pizza1);
		assertNull(line1);
	}
	@Test (expected=IllegalPizza.class)
	public void testLineItemIntPizza7() throws Exception {
		LineItem line1 = new LineItem(500, pizza1);
		assertNull(line1);
	}
	@Test (expected=IllegalPizza.class)
	public void testLineItemIntPizza8() throws Exception {
		LineItem line1 = new LineItem(50, null);
		assertNull(line1);
	}

	// Legal arguments
	@Test
	public void testSetNumber() throws Exception {
		LineItem line1 = new LineItem(50, pizza1);
		line1.setNumber(1);
		assertEquals(1, line1.getNumber());
		line1.setNumber(100);
		assertEquals(100, line1.getNumber());
		line1.setNumber(50);
		assertEquals(50, line1.getNumber());
	}

	// Illegal arguments
	@Test  (expected=IllegalPizza.class)
	public void testSetNumber1() throws Exception {
		LineItem line1 = new LineItem(50, pizza1);
		line1.setNumber(-1);
		assertEquals(50, line1.getNumber());
	}
	@Test  (expected=IllegalPizza.class)
	public void testSetNumber2() throws Exception {
		LineItem line1 = new LineItem(50, pizza1);
		line1.setNumber(0);
		assertEquals(50, line1.getNumber());
	}
	@Test  (expected=IllegalPizza.class)
	public void testSetNumber3() throws Exception {
		LineItem line1 = new LineItem(50, pizza1);
		line1.setNumber(101);
		assertEquals(50, line1.getNumber());
	}
	@Test  (expected=IllegalPizza.class)
	public void testSetNumber4() throws Exception {
		LineItem line1 = new LineItem(50, pizza1);
		line1.setNumber(500);
		assertEquals(50, line1.getNumber());
	}

	@Test
	public void testCompareTo() throws Exception {
		LineItem same1 = new LineItem(1, new Pizza("small", 2, 0, 1)); // total cost $10.00
		LineItem same2 = new LineItem(1, new Pizza("medium")); // total cost $10.50
		LineItem lower = new LineItem(2, new Pizza("small", 2, 1, 0));
		LineItem higher1 = new LineItem(2, new Pizza("small", 2, 2, 0));
		LineItem higher2 = new LineItem(2, new Pizza("medium", 2, 1, 0));
		LineItem highest = new LineItem(10, new Pizza("small", 2, 1, 0));
		assertTrue(same1.compareTo(same1) == 0);
		assertTrue("Cost difference less than a dollar is considered equal.", same1.compareTo(same2) == 0);
		assertTrue("Cost difference less than a dollar is considered equal.", same2.compareTo(same1) == 0);
		assertTrue(higher1.compareTo(lower) < 0);
		assertTrue(lower.compareTo(higher1) > 0);
		assertTrue(higher2.compareTo(higher1) < 0);
		assertTrue(higher1.compareTo(higher2) > 0);
		assertTrue(highest.compareTo(higher2) < 0);
		assertTrue(higher2.compareTo(highest) > 0);		
	}

	@Test
	public void testFileSave() throws Exception {
    	String testFile = "OneItem.dat";
		LineItem line1 = new LineItem(pizza1);
		LineItem line2;
    	try (ObjectOutputStream binFileOut = new ObjectOutputStream(new FileOutputStream(testFile))) {
            binFileOut.writeObject(line1);
        }
        try (ObjectInputStream binFileIn = new ObjectInputStream(new FileInputStream(testFile))) {
            line2 = (LineItem)binFileIn.readObject();
        }
        assertEquals("Comparing object from file to object saved.", line1.toString(), line2.toString());
    }
	
} // end TestLineItem