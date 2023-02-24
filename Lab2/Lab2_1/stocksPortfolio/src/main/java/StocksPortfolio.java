import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private IStockmarketService stockmarket;
    private List<Stock> stocks;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<Stock>();
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public double getTotalValue() {
        double total = 0.0;

        for (Stock stock : this.stocks) {
            total += stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }

        return total;
    }

    public void setStockmarket(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public IStockmarketService getStockmarket() {
        return stockmarket;
    }

}
