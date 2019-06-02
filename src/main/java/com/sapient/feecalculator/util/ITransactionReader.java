package com.sapient.feecalculator.util;

import java.util.List;

import com.sapient.feecalculator.model.TransactionModel;

public interface ITransactionReader {
	
	 List<TransactionModel> readTransactions();
	 
	 void printTransactions(List<TransactionModel> transactions);

}
 