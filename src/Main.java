import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Main {
	private static Map<String, Stock> stocksMap = new HashMap<>();
	
	public static void main(String[] args) {
		//initliaze the stocksMap
		stocksMap.put("TEA", new Stock("TEA", 0, 100));
		stocksMap.put("POP", new Stock("POP", 8, 100));
		stocksMap.put("ALE", new Stock("ALE", 23, 60));
		stocksMap.put("GIN", new PreferredStock("GIN", 8, 0.02, 100));
		stocksMap.put("JOE", new Stock("JOE", 13, 250));
		
		//now beginning reading the commands
		Scanner scanner = new Scanner(System.in);
		String command;
		
		
		System.out.println("--------- Available Commands ---------\n" +
						   "Calculate stock dividend yield: sdy\n" + 
						   "Calculate P/E ratio: per\n" +
						   "Record trade: rt\n" +
						   "Calculate stock price: sp\n" +
						   "Calculate GBCE: gbce\n"
						   +"* Exit: exit");
		
		//Read the commands
		do{
			System.out.print("\n----------------------------\n" + 
							 "Please enter a command: ");
			command = scanner.nextLine();
			
			//now do the necessary action according to the
			//provided command
			try {
				actOnGivenCommand(command, scanner);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		}while(!command.equals("exit"));
		
	}

	
	/**
	 * This function performs an action according to the provided command. Moreover,
	 * the Scanner object is passed in order for the following functions to capture
	 * the input via keyboard
	 * 
	 * @param command
	 * @param scanner
	 * @throws Exception
	 */
	private static void actOnGivenCommand(String command, Scanner scanner) throws Exception{
		
		switch(command)
		{
			case "sdy":
				calculateDividendYield(scanner);
				break;
			case "per":
				calculatePERatio(scanner);
				break;
			case "rt":
				recordTrade(scanner);
				break;
			case "sp":
				calculateStockPrice(scanner);
				break;
			case "gbce":
				calculateGBCE();
				break;
			case "exit":
				System.out.println("Closing application...");
				break;
			default:
				throw new Exception("Command not found!");
		}
	}

	
	/**
	 * Calculates the stock dividend yield
	 * 
	 * @param scanner
	 */
	private static void calculateDividendYield(Scanner scanner){
		System.out.println("\nStock Dividend Yield_____");
		System.out.print("Please enter stock name: ");
		String stockname = scanner.nextLine();
		
		Stock stock = stocksMap.get(stockname);
		
		if(stock != null)
		{
			System.out.print("Please enter ticker price: ");
			double price = Double.valueOf(scanner.nextLine());
			
			System.out.println("Result: " + stock.calculateDividendYield(price) + "\n");
		}
		else
		{
			System.err.println("Stock has not been found!\n");
		}
	}
	
	
	/**
	 * Retrieves all the necessary data for given stock in order
	 * to calculate the stock's PE ratio
	 * 
	 * @param scanner
	 */
	private static void calculatePERatio(Scanner scanner){
		System.out.println("\nStock PE Ratio_______");
		System.out.print("Please enter stock name: ");
		String stockname = scanner.nextLine();
		
		Stock stock = stocksMap.get(stockname);
		
		if(stock != null)
		{
			System.out.print("Enter ticker price: ");
			double price = Double.valueOf(scanner.nextLine());
			
			System.out.print("Enter dividend (leave blank to calculate with Last Dividend) : ");
			String divStr = scanner.nextLine();
			double dividend;
			
			if(!divStr.isEmpty())
				dividend = Double.valueOf(divStr);
			else
				dividend = stock.getLastDividend();
				
			System.out.println("Result: " + stock.calculatePERatio(price, dividend)+ "\n");
		}
		else
		{
			System.err.println("Stock has not been found!\n");
		}
	}
	
	
	/**
	 * Records a given trade for the specified stock
	 * 
	 * @param scanner
	 */
	private static void recordTrade(Scanner scanner){
		System.out.println("\nRecord stock trade_____");
		System.out.print("Please enter stock name: ");
		String stockname = scanner.nextLine();
		
		Stock stock = stocksMap.get(stockname);
		
		if(stock != null)
		{
			System.out.print("Enter num of shares: ");
			int shares = Integer.valueOf(scanner.nextLine());
			
			System.out.print("Enter trade indicator (buy/sell): ");
			String tradeIndicator = scanner.nextLine();
			
			System.out.print("Enter trade price: ");
			int price = Integer.valueOf(scanner.nextLine());
			
			//record the trade
			stock.addTrade(new Trade(shares, TradeIndicator.fromString(tradeIndicator), price));
		}
		else
		{
			System.err.println("Stock has not been found!\n");
		}	
	}
	
	
	/**
	 * Calculates the stock price for a given stock
	 * 
	 * @param scanner
	 */
	private static void calculateStockPrice(Scanner scanner){
		System.out.println("\nStock Price__________");
		System.out.print("Please enter stock name: ");
		String stockname = scanner.nextLine();
		
		Stock stock = stocksMap.get(stockname);
		
		if(stock != null)
		{
			System.out.println("Stock price: " + stock.calculateStockPrice(900_000));
		}
		else
		{
			System.err.println("Stock has not been found!\n");
		}	
	}
	
	
	/**
	 * Calculates the GBCE index based on the prices of all stocks
	 * 
	 * @param scanner
	 */
	private static void calculateGBCE(){
		System.out.println("\nCalculate GBCE__");
		Iterator<String> itr = stocksMap.keySet().iterator();
		Stock stock;
		long product = 1;
		double stockPrice;
		
		while(itr.hasNext())
		{
			stock = stocksMap.get(itr.next());
			
			stockPrice = stock.calculateStockPrice(900_000);
			
			product *= stockPrice;
		}
		
		System.out.println("GBCE: " + Math.sqrt(product));
	}
}
