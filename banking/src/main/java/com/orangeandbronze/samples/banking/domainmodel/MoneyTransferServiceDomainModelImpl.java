package com.orangeandbronze.samples.banking.domainmodel;

import com.orangeandbronze.samples.banking.BankingTransaction;
import com.orangeandbronze.samples.banking.BankingTransactionRepository;
import com.orangeandbronze.samples.banking.MoneyTransferService;
import com.orangeandbronze.samples.banking.MoneyTransferTransaction;

public class MoneyTransferServiceDomainModelImpl implements MoneyTransferService {

	private AccountRepository accountRepository;
	private BankingTransactionRepository bankingTransactionRepository;

	public MoneyTransferServiceDomainModelImpl(
			AccountRepository accountRepository,
			BankingTransactionRepository bankingTransactionRepository) {
		super();
		this.accountRepository = accountRepository;
		this.bankingTransactionRepository = bankingTransactionRepository;
	}

	@Override
	public BankingTransaction transfer(
			String fromAccountId, String toAccountId,
			double amount) {
		Account fromAccount = accountRepository.findById(fromAccountId);
		Account toAccount = accountRepository.findById(toAccountId);
		
		fromAccount.debit(amount);
		toAccount.credit(amount);

		BankingTransaction moneyTransferTransaction =
				new MoneyTransferTransaction(fromAccountId, toAccountId, amount);
		bankingTransactionRepository.addTransaction(
				moneyTransferTransaction);
		
		return moneyTransferTransaction;
	}

}
