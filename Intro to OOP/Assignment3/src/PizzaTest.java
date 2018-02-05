
/*
 * Your Pizza class must pass all the tests contained here.  You may
 * certainly add more tests if you wish.
 */
import static org.junit.Assert.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.Test;

public class PizzaTest {

	// Some legal argument tests:
	// One parameter constructor.
	// Also tests for toString and getCost.
	@Test
	public void testPizzaString() throws Exception {
		Pizza pizza1 = new Pizza("small");
		assertEquals("small pizza, cheese, pepperoni. Cost: $8.50 each.", pizza1.toString());
		assertEquals(8.50, pizza1.getCost(), 0.01);
	}
	@Test
	public void testPizzaString0() throws Exception {
		Pizza pizza1 = new Pizza("SMALL");
		assertEquals("small pizza, cheese, pepperoni. Cost: $8.50 each.", pizza1.toString());
		assertEquals(8.50, pizza1.getCost(), 0.01);
	}
	@Test
	public void testPizzaString1() throws Exception {
		Pizza pizza1 = new Pizza("medium");
		assertEquals("medium pizza, cheese, pepperoni. Cost: $10.50 each.", pizza1.toString());
		assertEquals(10.50, pizza1.getCost(), 0.01);
	}
	@Test
	public void testPizzaString2() throws Exception {
		Pizza pizza1 = new Pizza("large");
		assertEquals("large pizza, cheese, pepperoni. Cost: $12.50 each.", pizza1.toString());
		assertEquals(12.50, pizza1.getCost(), 0.01);
	}
	
	// Four parameter constructor
	@Test
	public void testPizzaStringIntIntInt() throws Exception {
		Pizza pizza1 = new Pizza("small", 1, 0, 0);
		assertEquals("small pizza, cheese only. Cost: $7.00 each.", pizza1.toString());
		assertEquals(7.00, pizza1.getCost(), 0.01);		
	}
	@Test
	public void testPizzaStringIntIntInt1() throws Exception {
		Pizza pizza1 = new Pizza("medium", 3, 0, 0);
		assertEquals("medium pizza, triple cheese only. Cost: $12.00 each.", pizza1.toString());
		assertEquals(12.00, pizza1.getCost(), 0.01);		
	}
	@Test
	public void testPizzaStringIntIntInt2() throws Exception {
		Pizza pizza1 = new Pizza("large", 2, 1, 0);
		assertEquals("large pizza, double cheese, ham. Cost: $14.00 each.", pizza1.toString());
		assertEquals(14.00, pizza1.getCost(), 0.01);		
	}
	@Test
	public void testPizzaStringIntIntInt3() throws Exception {
		Pizza pizza1 = new Pizza("large", 1, 2, 1);
		assertEquals("large pizza, cheese, double ham, pepperoni. Cost: $15.50 each.", pizza1.toString());
		assertEquals(15.50, pizza1.getCost(), 0.01);		
	}

	// Some illegal argument tests
	// One parameter constructor
	@Test (expected=IllegalPizza.class)
	public void testPizzaString3() throws Exception {
		Pizza pizza1 = new Pizza("smallish");
		assertNull(pizza1);
	}
	@Test (expected=IllegalPizza.class)
	public void testPizzaString4() throws Exception {
		Pizza pizza1 = new Pizza("M");
		assertNull(pizza1);
	}
	@Test (expected=IllegalPizza.class)
	public void testPizzaString5() throws Exception {
		Pizza pizza1 = new Pizza("lge");
		assertNull(pizza1);
	}
	@Test (expected=IllegalPizza.class)
	public void testPizzaString6() throws Exception {
		Pizza pizza1 = new Pizza(" ");
		assertNull(pizza1);
	}
	@Test (expected=IllegalPizza.class)
	public void testPizzaString7() throws Exception {
		Pizza pizza1 = new Pizza("");
		assertNull(pizza1);
	}
	@Test (expected=IllegalPizza.class)
	public void testPizzaString8() throws Exception {
		Pizza pizza1 = new Pizza(null);
		assertNull(pizza1);
	}
	
	// Four parameter constructor
	@Test (expected=IllegalPizza.class)
	public void testPizzaStringIntIntInt4() throws Exception {
		Pizza pizza1 = new Pizza("small", 0, 0, 0);
		assertNull(pizza1);
	}
	@Test (expected=IllegalPizza.class)
	public void testPizzaStringIntIntInt5() throws Exception {
		Pizza pizza1 = new Pizza("medium", 4, 0, 0);
		assertNull(pizza1);
	}
	@Test (expected=IllegalPizza.class)
	public void testPizzaStringIntIntInt6() throws Exception {
		Pizza pizza1 = new Pizza("large", 2, 1, 3);
		assertNull(pizza1);
	}
	@Test (expected=IllegalPizza.class)
	public void testPizzaStringIntIntInt7() throws Exception {
		Pizza pizza1 = new Pizza("large", 1, 3, 1);
		assertNull(pizza1);
	}
	@Test (expected=IllegalPizza.class)
	public void testPizzaStringIntIntInt8() throws Exception {
		Pizza pizza1 = new Pizza("large", 1, -1, 1);
		assertNull(pizza1);
	}
	@Test (expected=IllegalPizza.class)
	public void testPizzaStringIntIntInt9() throws Exception {
		Pizza pizza1 = new Pizza("large", 1, 4, 1);
		assertNull(pizza1);
	}
	@Test (expected=IllegalPizza.class)
	public void testPizzaStringIntIntInt10() throws Exception {
		Pizza pizza1 = new Pizza("large", 1, 1, 4);
		assertNull(pizza1);
	}

	@Test
	public void testEqualsObject() throws Exception {
		Pizza pizza1 = new Pizza("large", 1, 1, 2);
		Pizza pizza2 = new Pizza("large", 1, 1, 2);
		Pizza pizza3 = new Pizza("medium", 1, 1, 2);
		Pizza pizza4 = new Pizza("large", 2, 1, 2);
		Pizza pizza5 = new Pizza("large", 1, 0, 2);
		assertEquals(pizza1, pizza2);
		assertTrue(pizza1.equals(pizza2));
		assertFalse(pizza1.equals(pizza3));
		assertFalse(pizza1.equals(pizza4));
		assertFalse(pizza1.equals(pizza5));
	}

	@Test
	public void testClone() throws Exception {
		Pizza pizza1 = new Pizza("large", 1, 1, 2);
		Pizza pizza2 = pizza1.clone();
		assertEquals(pizza1, pizza2);
	}

	@Test
	public void testFileSave() throws Exception {
    	String testFile = "OnePizza.dat";
		Pizza pizza1 = new Pizza("large", 1, 1, 2);
		Pizza pizza2;
    	try (ObjectOutputStream binFileOut = new ObjectOutputStream(new FileOutputStream(testFile))) {
            binFileOut.writeObject(pizza1);
        }
        try (ObjectInputStream binFileIn = new ObjectInputStream(new FileInputStream(testFile))) {
            pizza2 = (Pizza)binFileIn.readObject();
        }
        assertEquals("Comparing object from file to object saved.", pizza1, pizza2);
    }

} // end TestPizza
