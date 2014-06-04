package com.orangeandbronze.samples.banking;

public interface MoneyTransferService {

	BankingTransaction transfer(
			String fromAccountId, String toAccountId,
			double amount);

}
