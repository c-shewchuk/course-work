import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
/*
 * You should pass all JUnit tests in this class before you try running the FastenerOrderSystem
 * program.  Note that this testing class does not really have enough tests, particularly
 * when testing for illegal attributes.  But it is not bad...
 */
public class TestHierarchy {

	public static ArrayList<Fastener> db;
	
	@BeforeClass
	public static void oneTimeSetUp() throws Exception {
		db = new ArrayList<>();
	}

	// Legal instances
	@Test
	public void test0() throws Exception {
										// O.D.,  I.D., thick, material, finish, price/unit, unit size
		FlatWasher toAdd = new FlatWasher(1.375, 0.562, 0.109, "Steel", "Yellow Zinc", 10.7375, 25);
		assertNotNull(toAdd);
		db.add(toAdd);
	}
	@Test
	public void test1() throws Exception {
										// O.D.,  I.D., thick, material, finish, price/unit, unit size
		FlatWasher toAdd = new FlatWasher(0.875, 0.375, 0.083, "Steel", "Zinc", 5.68, 100);
		assertNotNull(toAdd);
		db.add(toAdd);
	}
	@Test
	public void test2() throws Exception {
										// O.D.,  I.D., thick, material, finish, price/unit, unit size
		FlatWasher toAdd = new FlatWasher(0.750, 0.380, 0.062, "Nylon", "Plain", 2.2475, 25);
		assertNotNull(toAdd);
		db.add(toAdd);
	}
	@Test
	public void test3() throws Exception {
									// thread,  material, finish, price/unit, unit size
		WingNut toAdd = new WingNut("1/4-20", "Stainless Steel", "Plain", 0.5528, 1);
		assertNotNull(toAdd);
		db.add(toAdd);
	}
	@Test
	public void test4() throws Exception {
									// thread,  material, finish, price/unit, unit size
		WingNut toAdd = new WingNut("5/16-18", "Steel", "Zinc", 18.225, 50);
		assertNotNull(toAdd);
		db.add(toAdd);
	}
	@Test
	public void test5() throws Exception {
											// length, thread,  material, finish, price/unit, unit size
		CarriageBolt toAdd = new CarriageBolt(1.0, "3/8-16", "Steel", "Zinc", 0.2985, 1);
		assertNotNull(toAdd);
		db.add(toAdd);
	}
	@Test
	public void test6() throws Exception {
											// length, thread,  material, finish, price/unit, unit size
		CarriageBolt toAdd = new CarriageBolt(0.75, "1/4-20", "Stainless Steel", "Plain", 18.825, 50);
		assertNotNull(toAdd);
		db.add(toAdd);
	}
	@Test
	public void test7() throws Exception {
											// length, thread,  material, finish, price/unit, unit size
		CarriageBolt toAdd = new CarriageBolt(12.0, "1/2-13", "Steel", "Hot Dipped Galvanized", 3.72, 1);
		assertNotNull(toAdd);
		db.add(toAdd);
	}
	@Test
	public void test8() throws Exception {
									// length, thread,  material, finish, head, drive, point, price/unit, unit size
		WoodScrew toAdd = new WoodScrew(2.0, "#8-13", "Steel", "ACQ 1000 Hour", "Flat", "Philips", "Type 17", 44.10, 750);
		assertNotNull(toAdd);
		db.add(toAdd);
	}
	@Test
	public void test9() throws Exception {
									// length, thread,  material, finish, head, drive, point, price/unit, unit size
		WoodScrew toAdd = new WoodScrew(0.75, "#8-15", "Steel", "Zinc", "Flat", "Square", "Sharp", 8.84, 100);
		assertNotNull(toAdd);
		db.add(toAdd);
	}
	@Test
	public void test10() throws Exception {
									// length, thread,  material, finish, head, drive, point, price/unit, unit size
		WoodScrew toAdd = new WoodScrew(3.0, "#10-13", "Steel", "Zinc", "Flat", "Square", "Sharp", 24.63, 100);
		assertNotNull(toAdd);
		db.add(toAdd);
	}

	// Illegal instances
	// There are not really enough of these tests, but this is better than nothing...
	@Test (expected=IllegalFastener.class)
	public void test11() throws Exception {
										// O.D.,  I.D., thick, material, finish, price/unit, unit size
		FlatWasher toAdd = new FlatWasher(3.375, 0.562, 0.109, "Steel", "Yellow Zinc", 10.7375, 25);
		assertNull(toAdd);
	}
	@Test (expected=IllegalFastener.class)
	public void test12() throws Exception {
										// O.D.,  I.D., thick, material, finish, price/unit, unit size
		FlatWasher toAdd = new FlatWasher(0.875, 0.375, 0.083, "Bork", "Zinc", 5.68, 100);
		assertNull(toAdd);
	}
	@Test (expected=IllegalFastener.class)
	public void test13() throws Exception {
										// O.D.,  I.D., thick, material, finish, price/unit, unit size
		FlatWasher toAdd = new FlatWasher(0.750, 0.380, 0.062, "Nylon", "Plain", 2.2475, 22);
		assertNull(toAdd);
	}
	@Test (expected=IllegalFastener.class)
	public void test14() throws Exception {
									// thread,  material, finish, price/unit, unit size
		WingNut toAdd = new WingNut("1/4-20", "Stainless Steel", "Black Phosphate", 0.5528, 1);
		assertNull(toAdd);
	}
	@Test (expected=IllegalFastener.class)
	public void test15() throws Exception {
									// thread,  material, finish, price/unit, unit size
		WingNut toAdd = new WingNut("15/16-18", "Steel", "Zinc", 18.225, 50);
		assertNull(toAdd);
	}
	@Test (expected=IllegalFastener.class)
	public void test16() throws Exception {
											// length, thread,  material, finish, price/unit, unit size
		CarriageBolt toAdd = new CarriageBolt(1.0, "3/8-16", "Nylon", "Zinc", 0.2985, 1);
		assertNull(toAdd);
	}
	@Test (expected=IllegalFastener.class)
	public void test17() throws Exception {
											// length, thread,  material, finish, price/unit, unit size
		CarriageBolt toAdd = new CarriageBolt(0.05, "1/4-20", "Stainless Steel", "Plain", 18.825, 50);
		assertNull(toAdd);
	}
	@Test (expected=IllegalFastener.class)
	public void test18() throws Exception {
											// length, thread,  material, finish, price/unit, unit size
		CarriageBolt toAdd = new CarriageBolt(12.0, "1/2-13", "Steel", "Lubricated", 3.72, 1);
		assertNull(toAdd);
	}
	@Test (expected=IllegalFastener.class)
	public void test19() throws Exception {
									// length, thread,  material, finish, head, drive, point, price/unit, unit size
		WoodScrew toAdd = new WoodScrew(2.0, "#8-13", "Steel", "ACQ 1000 Hour", "Flat", "Philips", "Blunt", 44.10, 750);
		assertNull(toAdd);
	}
	@Test (expected=IllegalFastener.class)
	public void test20() throws Exception {
									// length, thread,  material, finish, head, drive, point, price/unit, unit size
		WoodScrew toAdd = new WoodScrew(25.0, "#8-15", "Steel", "Zinc", "Flat", "Square", "Sharp", 8.84, 100);
		assertNull(toAdd);
	}
	@Test (expected=IllegalFastener.class)
	public void test21() throws Exception {
									// length, thread,  material, finish, head, drive, point, price/unit, unit size
		WoodScrew toAdd = new WoodScrew(3.0, "#10-13", "Aluminium", "Zinc", "Flat", "Square", "Sharp", 24.63, 100);
		assertNull(toAdd);
	}

	// This method will save the collection of legal Fastener objects to be used
	// by the FastenerOrderSystem class.
	@AfterClass
	public static void oneTimeTearDownAfterClass() throws Exception {
		String dbFile = "Database.dat";
		File dbFileObject = new File(dbFile);
		if (dbFileObject.exists())
			dbFileObject.delete();
		db.trimToSize();
		for (Fastener item : db)
			System.out.println(item);
		// Save the ArrayList so it can be used by the order system class.
    	try (ObjectOutputStream binFileOut = new ObjectOutputStream(new FileOutputStream(dbFile))) {
            binFileOut.writeObject(db);
        }
	} // end oneTimeTearDownAfterClass method

} // end TestHierarchy class