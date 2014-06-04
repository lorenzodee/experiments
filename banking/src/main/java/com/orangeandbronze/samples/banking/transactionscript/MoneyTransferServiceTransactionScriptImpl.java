package com.orangeandbronze.samples.banking.transactionscript;

import com.orangeandbronze.samples.banking.BankingTransaction;
import com.orangeandbronze.samples.banking.BankingTransactionRepository;
import com.orangeandbronze.samples.banking.DebitException;
import com.orangeandbronze.samples.banking.MoneyTransferService;
import com.orangeandbronze.samples.banking.MoneyTransferTransaction;

public class MoneyTransferServiceTransactionScriptImpl implements MoneyTransferService {

	private AccountDao accountDao;
	private BankingTransactionRepository bankingTransactionRepository;
	private double limit;

	public MoneyTransferServiceTransactionScriptImpl(
			AccountDao accountDao, BankingTransactionRepository bankingTransactionRepository) {
		super();
		this.accountDao = accountDao;
		this.bankingTransactionRepository = bankingTransactionRepository;
	}

	@Override
	public BankingTransaction transfer(
			String fromAccountId, String toAccountId,
			double amount) {
		Account fromAccount = accountDao.findById(fromAccountId);
		Account toAccount = accountDao.findById(toAccountId);
		final double newBalance = fromAccount.getBalance() - amount;
		switch (fromAccount.getOverdraftPolicy()) {
		case NEVER:
			if (newBalance < 0) {
				throw new DebitException("Insufficient funds");
			}
			break;
		case ALLOWED:
			if (newBalance < -limit) {
				throw new DebitException(
						"Overdraft limit (of " + limit + ") exceeded: " + newBalance);
			}
			break;
		default:
			throw new RuntimeException("Unknown overdraft policy");
		}
		fromAccount.setBalance(newBalance);
		toAccount.setBalance(toAccount.getBalance() + amount);
		BankingTransaction moneyTransferTransaction =
				new MoneyTransferTransaction(fromAccountId, toAccountId, amount);
		bankingTransactionRepository.addTransaction(
				moneyTransferTransaction);
		
		return moneyTransferTransaction;
	}

}
