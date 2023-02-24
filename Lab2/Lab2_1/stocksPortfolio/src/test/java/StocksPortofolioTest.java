import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StocksPortofolioTest {

    @Mock
    IStockmarketService market;

    @InjectMocks
    StocksPortfolio portfolio;

    @Test
    void getTotalValueAnnot(){
        when(market.lookUpPrice("EBAY")).thenReturn(10.0);
        when(market.lookUpPrice("GOOGLE")).thenReturn(100.0);
        when(market.lookUpPrice("AMAZON")).thenReturn(200.0);

        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("GOOGLE", 3));

        double result = portfolio.getTotalValue();

        assertEquals(320.0, result);
        verify(market).lookUpPrice("EBAY");
        verify(market).lookUpPrice("GOOGLE");
    }

    @DisplayName("Test getTotalValue")
    @Test
    public void getTotalValueTest() {
        // 1.Prepare a mock to subtitute the remote service (or @Mock annotation)
        IStockmarketService mockedMarket = mock(IStockmarketService.class);

        // 2.Create a instance of the subject under test(Sut) and user the mock to set the (remote) service
        StocksPortfolio myStocks = new StocksPortfolio(mockedMarket);

        // 3.Load the SUT with the proper expectations (when ... thenReturn ...)
        when(mockedMarket.lookUpPrice("EBAY")).thenReturn(10.0);
        when(mockedMarket.lookUpPrice("GOOGLE")).thenReturn(100.0);
        when(mockedMarket.lookUpPrice("AMAZON")).thenReturn(200.0);


        // 4.Exercise the test (use the service in the SUT)
        myStocks.addStock(new Stock("EBAY", 2));
        myStocks.addStock(new Stock("GOOGLE", 3));

        double result = myStocks.getTotalValue();

        // 5.Verify the result (assert) and the use of the mock (verify)
        assertEquals(320.0, result); //junit style
        verify(mockedMarket).lookUpPrice("EBAY");
        verify(mockedMarket).lookUpPrice("GOOGLE");
        assertThat(result, is(320.0)); //hamcrest style

        verify(mockedMarket, times(2)).lookUpPrice(anyString());

    }
}
