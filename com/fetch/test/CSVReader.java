package com.fetch.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
	
	//Function to read data from CSV and store in list of Transactions
	public static List<Transaction> readFileFromCSV (String fileName){
        List<Transaction> transactions = new ArrayList <> (); 
        Path pathToFile = Paths.get(fileName);

        try(BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)){
            br.readLine();
            String line = br.readLine();
//            System.out.println(line);
            while (line != null) {
                String [] data = line.split(",");
                //convert string array to list
                Transaction t;
				try {
					t = Transaction.createTransaction(data[0].substring(1, data[0].length()-1), data[1], data[2].substring(1,data[2].length()-1));
					transactions.add(t);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                

                line = br.readLine();
            }
        }catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
        return transactions;
    }
}
