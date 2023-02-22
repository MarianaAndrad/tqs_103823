/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;

    private BoundedSetOfNaturals setD;



    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        setD = new BoundedSetOfNaturals(8);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    // @Disabled("TODO revise test logic")
    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        /* Note: this test is not necessary, as it is already covered by the test above

        setB.add(11);
        assertTrue(setB.contains(11), "add: added element not found in set.");
        assertEquals(7, setB.size(), "add: elements count not as expected."); */

        // New Test : add elements to a set with a fixed capacity
        setD.add(new int[]{11, 12, 13});
        assertTrue(setD.contains(11), "add: added element not found in set.");
        assertTrue(setD.contains(12), "add: added element not found in set.");
        assertTrue(setD.contains(13), "add: added element not found in set.");
        assertEquals(3, setD.size(), "add: elements count not as expected.");
    }

    // @Disabled("TODO revise to test the construction from invalid arrays")
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};
        int[] elems2 = new int[]{10, 20, 20};

        //Note: Bounded set is full. no more elements allowed.
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));

        //Note: duplicate elements are not allowed.
        assertThrows(IllegalArgumentException.class, () -> setD.add(elems2));
        assertThrows(IllegalArgumentException.class, () -> BoundedSetOfNaturals.fromArray(elems2));

        // Note: negative elements are not allowed.
        assertThrows(IllegalArgumentException.class, () -> setD.add(elems));
        assertThrows(IllegalArgumentException.class, () -> BoundedSetOfNaturals.fromArray(elems));

    }

    //@Disabled(" NEW Test: Intersection of two sets")
    @Test
    public void testIntersection() {
        BoundedSetOfNaturals setI = setB.intersects(setC);
        assertTrue(setI.contains(50), "intersection: element not found in set.");
        assertTrue(setI.contains(60), "intersection: element not found in set.");
        assertEquals(2, setI.size(), "intersection: elements count not as expected.");
    }



}
