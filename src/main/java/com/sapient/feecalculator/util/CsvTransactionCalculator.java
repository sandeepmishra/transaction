package com.sapient.feecalculator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;

import com.sapient.feecalculator.model.TransactionModel;

public class CsvTransactionCalculator implements ITransactionReader {

	@Override
	public List<TransactionModel> readTransactions() {
		List<TransactionModel> transactionas = new ArrayList<>();
		try {
			InputStream resource = new ClassPathResource("transaction.csv").getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
			String line = null;// new String(Files.readAllBytes(resource.toPath()));
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				if (tokens.length > 0) {
					TransactionModel transaction = new TransactionModel(tokens[0].trim(), tokens[1].trim(),
							tokens[2].trim(), tokens[3].trim(), TransactionUtils.parseDate(tokens[4].trim()),
							TransactionUtils.parseMarketValue(tokens[5].trim()),
							TransactionUtils.getPriority(tokens[6].trim()),
							TransactionUtils.parseMarketValue(tokens[6]));
					transactionas.add(transaction);
					processTransactions(transactionas, transaction);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return transactionas;
	}

	@Override
	public void printTransactions(List<TransactionModel> transactions) {
		Map<Boolean, Map<Date, Map<String, Map<String, List<TransactionModel>>>>> preorityMap = groupingByClientId(
				transactions);

		preorityMap.forEach((byPreKey, byDateMap) -> {
			byDateMap.forEach((byDateKey, byTransactionTypeMap) -> {
				byTransactionTypeMap.forEach((byTypeKey, byIdMap) -> {
					byIdMap.forEach((k, v) -> {
						// sorting list by clientId, transactionType, transacationDate and preority
						Collections.sort(v,
								Comparator.comparing(TransactionModel::getClientId)
										.thenComparing(TransactionModel::getTransactionType)
										.thenComparing(TransactionModel::getTransactionDate)
										.thenComparing(TransactionModel::getPriority));
						for (TransactionModel model : v) {
							System.out.println(model.toString());
						}
					});
				});
			});
		});
	}

	private void processTransactions(List<TransactionModel> transactionas, TransactionModel transaction) {
		if (transactionas != null && !transactionas.isEmpty()) {
			if (isIntradayTransaction(transactionas, transaction)) {
				transaction.setTransactionFees(10.0);
			} else if (transaction.getPriority() == true) {
				transaction.setTransactionFees(500.0);
			} else if (transaction.getPriority() == false) {
				if ((transaction.getTransactionType().equalsIgnoreCase("SELL"))
						|| (transaction.getTransactionType().equalsIgnoreCase("WITHDRAW"))) {
					transaction.setTransactionFees(100.0);
				} else if ((transaction.getTransactionType().equalsIgnoreCase("BUY"))
						|| (transaction.getTransactionType().equalsIgnoreCase("DEPOSIT"))) {
					transaction.setTransactionFees(50.0);
				}
			}
		}
	}

	// grouping transactions by clientId, transactionType, transactionDate, preority
	private Map<Boolean, Map<Date, Map<String, Map<String, List<TransactionModel>>>>> groupingByClientId(
			List<TransactionModel> transactionas) {

		return transactionas.stream()
				.collect(Collectors.groupingBy(TransactionModel::getPriority, threeLevelGrouping(transactionas)));
	}

	private Collector<TransactionModel, ?, Map<Date, Map<String, Map<String, List<TransactionModel>>>>> threeLevelGrouping(
			List<TransactionModel> transactionas) {
		return Collectors.groupingBy(TransactionModel::getTransactionDate, groupByCityAndPetName());
	}

	private Collector<TransactionModel, ?, Map<String, Map<String, List<TransactionModel>>>> groupByCityAndPetName() {
		return Collectors.groupingBy(TransactionModel::getClientId,
				Collectors.groupingBy(TransactionModel::getTransactionType));
	}

	private boolean isIntradayTransaction(List<TransactionModel> transactionas, TransactionModel transaction) {
		boolean isIntraDayTransaction = false;
		TransactionModel temp = null;
		if (transactionas.size() > 0) {
			for (TransactionModel trans : transactionas) {
				if (trans.getClientId().equals(transaction.getClientId())
						&& trans.getSecurityId().equals(transaction.getSecurityId())
						&& trans.getTransactionDate().equals(transaction.getTransactionDate())) {
					if ((trans.getTransactionType().equalsIgnoreCase("BUY")
							&& transaction.getTransactionType().equalsIgnoreCase("SELL"))
							|| (trans.getTransactionType().equalsIgnoreCase("SELL")
									&& transaction.getTransactionType().equalsIgnoreCase("BUY"))) {
						isIntraDayTransaction = true;
						temp = trans;
						break;
					}
				}

			}

			if (isIntraDayTransaction && temp != null) {
				transactionas.remove(temp);
				temp.setTransactionFees(10.0);
				transactionas.add(temp);
			}

		} else {
			isIntraDayTransaction = false;
		}

		return isIntraDayTransaction;
	}

}
