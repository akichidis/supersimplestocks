/**
 * @author Anastasios Kichidis
 * 
 * It's a representation of the preferred stocks.
 * The preferred stocks have a slightly different
 * implementation for the dividend yield calculation in
 * compared to common stocks
 */
public class PreferredStock extends Stock{
	private double fixedDividend; //expressed in percentage from 0-1

	/**
	 * Same as a Stock, but with the addition of fixed dividend parameter. 
	 * 
	 * @param symbol
	 * @param lastDividend
	 * @param fixedDividend
	 * @param parValue
	 */
	public PreferredStock(String symbol, int lastDividend, double fixedDividend, int parValue) {
		super(symbol, lastDividend, parValue);
		this.fixedDividend = fixedDividend;
	}
	
	public double getFixedDividend(){
		return fixedDividend;
	}

	@Override
	public double calculateDividendYield(double tickerPrice){
		return (fixedDividend*parValue) / tickerPrice;
	}
}
