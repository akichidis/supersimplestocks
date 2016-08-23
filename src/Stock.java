import java.util.ArrayList;
import java.util.List;

/**
 * @author Anastasios Kichidis
 *
 * The Stock class holds all the data for a specific stock
 */
public class Stock {
	protected String symbol;
	protected int lastDividend,
				parValue;
	protected List<Trade> tradesList = new ArrayList<>();
	
	/**
	 * Constructs a new Common Stock. 
	 * 
	 * @param symbol		The stock's symbol (e.x TEA)
	 * @param lastDividend	The stock's last dividend
	 * @param parValue		The stock's par value
	 */
	public Stock(String symbol, int lastDividend, int parValue){
		this.symbol = symbol;
		this.lastDividend = lastDividend;
		this.parValue = parValue;
	}
	
	public void addTrade(Trade trade){
		tradesList.add(trade);
	}
	
	public String getSymbol(){
		return symbol;
	}
	
	public int getLastDividend(){
		return lastDividend;
	}
	
	public int getParValue(){
		return parValue;
	}
	
	public double calculateDividendYield(double tickerPrice){
		return tickerPrice > 0 ? (double)lastDividend / tickerPrice : 0;
	}
	
	public double calculatePERatio(double tickerPrice, double dividend){
		return dividend > 0 ? tickerPrice / dividend : 0;
	}
	
	
	/**
	 * Calculates the stock price by taking into consideration the trades
	 * provided on the last miliseconds
	 * 
	 * @param minsInMiliseconds
	 * @return
	 */
	public double calculateStockPrice(long miliseconds){
		long currentTimeNanos = System.nanoTime();
		double sumQuantity=0, sumNumerator = 0;
		
		for(Trade trade: tradesList)
		{
			if(trade.getTimestamp() + (miliseconds*1_000_000) >= currentTimeNanos)
			{
				sumNumerator += trade.getPrice()*trade.getShares();
				sumQuantity += trade.getShares();
			}
		}
		
		return sumQuantity > 0 ? sumNumerator / sumQuantity : 0;
	}
}
