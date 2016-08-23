import java.util.Date;

/**
 * @author Anastasios Kichidis
 * 
 * The Trade class holds all the necessary information
 * for a specific trade. Every trade is linked with a specific stock
 */
public class Trade implements Comparable<Trade>{
	private long timestamp;
	private int shares;
	private TradeIndicator indicator;
	private int price;
	
	public Trade(int shares, TradeIndicator indicator, int price){
		this.timestamp = System.currentTimeMillis();
		this.shares = shares;
		this.indicator = indicator;
		this.price = price;
	}
	
	public long getTimestamp(){
		return timestamp;
	}
	
	public int getShares(){
		return shares;
	}
	
	public TradeIndicator getTradeIndicator(){
		return indicator;
	}
	
	public int getPrice(){
		return price;
	}
	
	
	/**
	 * We want to compare the Trades on a descending order according
	 * to their timestamps, in order to be able to have the earlier ones
	 * on the top of our list
	 */
	public int compareTo(Trade trade){
		return - (this.timestamp > trade.timestamp ? 1 : (this.timestamp == trade.timestamp ? 0 : -1));
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder("timestamp=");
		
		builder
		.append(new Date(timestamp))
		.append(", indicator=")
		.append(indicator.value)
		.append(", shares=")
		.append(shares)
		.append(", price=")
		.append(price);
		
		return builder.toString();
	}
}

enum TradeIndicator{
	BUY("buy"),
	SELL("sell");
	
	String value;

	TradeIndicator(String indicator){
		this.value = indicator;
	}
	
	public static TradeIndicator fromString(String indicator){
		switch(indicator)
		{
			case "buy":
				return BUY;
			default:
				return SELL;
		}
	}
}
