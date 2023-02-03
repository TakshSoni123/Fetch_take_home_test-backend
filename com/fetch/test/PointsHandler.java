package com.fetch.test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PointsHandler {
	
	public static final String READ_FILE = "resources/transactions.csv";
	
	public static Map<String, Long> payerMap;
	public static long totalPoints;
	
    public static void main(String[] args) {
    	
    	//Initialize our final map for payer points
    	payerMap = new HashMap<>();
    	totalPoints = 0;
    	
    	//Call function to read from transactions.csv
    	List<Transaction> list = CSVReader.readFileFromCSV(READ_FILE);
    	
    	if(list==null) {
    		System.out.println("Please check transactions.csv file");
    	}
    	
    	System.out.println("\nlIST OF TRANSACTIONS READ FROM FILE:-");
    	System.out.println("Payer | Points | Timestamp");
    	for(Transaction t : list) {
    		System.out.println(t.getPayer() + " | "+t.getPoints()+" | "+t.getTimestamp());
    	}
    	
    	//Sort list based on timestamp so that we can traverse through it from the oldest transaction to the newest
    	list.sort((e1, e2) -> e1.getTimestamp().compareTo(e2.getTimestamp()));
    	
    	//Call function to traverse our transaction list and make adjustments to our 
    	List<Transaction> finalList = traverseTransactions(list);
    	
    	if(finalList == null) {
    		System.out.println("Exiting the program due to failure");
    		return;
    	}
    	if(args.length >0) {
    		long finalAmountToSpend = Long.valueOf(args[0]);
        	finalList = spend(finalList, finalAmountToSpend);
        	totalPoints -= finalAmountToSpend;
    	}
    	if(finalList == null) {
    		System.out.println("Exiting the program due to incorrect input");
    		return;
    	}
    	
//    	for(Transaction t : finalList) {
//    		System.out.println(t.getPayer() + " "+t.getPoints()+" "+t.getTimestamp());
//    	}
    	
    	
    	System.out.println("\nAll payer point balances after spending :- ");
    	
    	Set<String> payers = payerMap.keySet();
    	
    	for(String p : payers) {
    		System.out.println(p+" : "+payerMap.get(p));
    	}
    	
    	System.out.println("\nTotal Points : "+ totalPoints);
    	
    }

    
    public static List<Transaction> traverseTransactions(List<Transaction> list){
    	
    	List<Transaction> finalList = new ArrayList<>();
    	
    	for(Transaction t : list) {
    		
    		//If points are earned by the user, directly store them
    		if(t.getPoints() >= 0) {
    			finalList.add(t);
    			payerMap.put(t.getPayer(), payerMap.getOrDefault(t.getPayer(), 0L) + t.getPoints());
    			totalPoints += t.getPoints();
    		}
    		else { //Else if the points are spent (i.e. negative points), we need to call the spend function to follow all the spending protocols
//    			System.out.println("Spending now : "+t.getPoints());
    			finalList = spend(finalList, Math.abs(t.getPoints()));
    			if(finalList == null) {
    				return null;
    			}
    			totalPoints -= Math.abs(t.getPoints());
    			
    		}
//    		System.out.println(totalPoints);
    	}
    	
    	return finalList;
    }
    
    public static List<Transaction> spend(List<Transaction> currList, long amountToSpend){
    	
    	//If the amount to be spent is greater than the total points with the user, we will return with an error message
    	if(Math.abs(amountToSpend) > totalPoints) {
			System.out.println("User does not have enough points to spend : "+amountToSpend);
			return null;
		}
    	
    	// Reduce points from transactions until amount to be spent is 0
    	while(amountToSpend > 0) {
    		
			Transaction oldestTransaction = currList.get(0);
    		
			//If we can directly subtract the remaining amount from oldest transaction points
    		if(oldestTransaction.getPoints() >= amountToSpend) {
    			long pointsLeft = oldestTransaction.getPoints() - amountToSpend;
    			oldestTransaction.setPoints(pointsLeft);
    			payerMap.put(oldestTransaction.getPayer(), payerMap.get(oldestTransaction.getPayer()) - amountToSpend);
    			amountToSpend = 0;
    		}
    		else {
    			amountToSpend -= oldestTransaction.getPoints();
    			
    			// Remove oldestTransaction from list as it does not matter any more
    			currList.remove(0);
    			
    			// Update points in map
    			payerMap.put(oldestTransaction.getPayer(), payerMap.get(oldestTransaction.getPayer()) - oldestTransaction.getPoints());
    			
    		}
    		
    	}
    	
    	return currList;
    }
    
}