package tqs.euromillions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.euromillions.CuponEuromillions;
import tqs.euromillions.Dip;
import tqs.euromillions.EuromillionsDraw;

class EuromillionsDrawTest {

    private CuponEuromillions sampleCoupon;

    @BeforeEach
     void setUp()  {
        sampleCoupon = new CuponEuromillions();
        sampleCoupon.appendDip(Dip.generateRandomDip());
        sampleCoupon.appendDip(Dip.generateRandomDip());
        sampleCoupon.appendDip(new Dip(new int[]{1, 2, 3, 48, 49}, new int[]{1, 9}));
    }


    @DisplayName("reports correct matches in a coupon")
    @Test
     void testCompareBetWithDrawToGetResults() {
        Dip winningDip, matchesFound;

        // test for full match, using the 3rd dip in the coupon as the Draw results
        winningDip = sampleCoupon.getDipByIndex(2);
        EuromillionsDraw testDraw = new EuromillionsDraw(winningDip);
        matchesFound = testDraw.findMatchesFor(sampleCoupon).get(2);

        assertEquals(winningDip, matchesFound, "expected the bet and the matches found to be equal");

        // test for no matches at all
        testDraw = new EuromillionsDraw(new Dip(new int[]{9, 10, 11, 12, 13}, new int[]{2, 3}));
        matchesFound = testDraw.findMatchesFor(sampleCoupon).get(2);
        // compare empty with the matches found
        assertEquals( new Dip(), matchesFound);
    }

    @DisplayName("format as string show all elements and count")
    @Test
     void testFormatandcountDips() {
        CuponEuromillions coupon = new CuponEuromillions();
        coupon.appendDip(new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2}));
        coupon.appendDip(new Dip(new int[]{6, 7, 8, 9, 10}, new int[]{3, 4}));
        assertEquals(2, coupon.countDips(), "format as string: coupon size not as expected. ");
        assertEquals("""
                        Dip #1:N[  1  2  3  4  5] S[  1  2]
                        Dip #2:N[  6  7  8  9 10] S[  3  4]
                        """, coupon.format(), "format as string: formatted string not as expected. ");
    }


}
