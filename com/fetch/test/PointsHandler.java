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
    		if(t.getPoints() >= 0) {
    			finalList.add(t);
    			payerMap.put(t.getPayer(), payerMap.getOrDefault(t.getPayer(), 0L) + t.getPoints());
    			totalPoints += t.getPoints();
    		}
    		else {
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
    	
    	if(Math.abs(amountToSpend) > totalPoints) {
			System.out.println("User does not have enough points to spend : "+amountToSpend);
			return null;
		}
    	
    	while(amountToSpend > 0) {
    		
			Transaction lastTransaction = currList.get(0);
    		
    		if(lastTransaction.getPoints() >= amountToSpend) {
    			long pointsLeft = lastTransaction.getPoints() - amountToSpend;
    			lastTransaction.setPoints(pointsLeft);
    			payerMap.put(lastTransaction.getPayer(), payerMap.get(lastTransaction.getPayer()) - amountToSpend);
    			amountToSpend = 0;
    		}
    		else {
    			amountToSpend -= lastTransaction.getPoints();
    			
    			// Remove lastTransaction from list
    			currList.remove(0);
    			
    			// Update points in map
    			payerMap.put(lastTransaction.getPayer(), payerMap.get(lastTransaction.getPayer()) - lastTransaction.getPoints());
    			
    		}
    		
    	}
    	
    	return currList;
    }
    
}