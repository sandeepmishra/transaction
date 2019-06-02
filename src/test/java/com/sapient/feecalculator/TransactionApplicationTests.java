package com.sapient.feecalculator;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sapient.feecalculator.model.TransactionModel;
import com.sapient.feecalculator.util.CsvTransactionCalculator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionApplicationTests {

	
	@Autowired
	private CsvTransactionCalculator csvTransactionCalculator;
	
	@Test
	public void testReadTransactions() {
		List<TransactionModel> readTransactions = csvTransactionCalculator.readTransactions();
		assertTrue(readTransactions !=null);
	}
	
	@Test
	public void testPrintTransactions() {
		csvTransactionCalculator.printTransactions(models());
		assertTrue(true);
	}

	private static List<TransactionModel> models() {
		List<TransactionModel> readTransactions = new ArrayList<>();
		TransactionModel model = new TransactionModel("SAPEXTXN1", "GS","ICICI","BUY",parseDate("23/11/2013"),101.9,false, 0.0);
		readTransactions.add(model);
		model = new TransactionModel("SAPEXTXN2", "GS","ICICI","SELL",parseDate("23/11/2013"),101.9,false, 0.0);
		readTransactions.add(model);
		return readTransactions;
	}
	
	private static Date parseDate(String date) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date convertedCurrentDate = sdf.parse(date);
			return convertedCurrentDate;
		}catch(Exception  ex){
			return null;
		}
	}
}
