package com.sapient.feecalculator;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sapient.feecalculator.model.TransactionModel;
import com.sapient.feecalculator.util.CsvTransactionCalculator;

@SpringBootApplication
public class TransactionApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TransactionApplication.class);
		System.out.println("This Applciation is currently only processing csv file.");

		List<TransactionModel> readTransactions = getHelloService().readTransactions();
		getHelloService().printTransactions(readTransactions);

	}

	@Bean
	public static CsvTransactionCalculator getHelloService() {
		return new CsvTransactionCalculator();
	}

}
