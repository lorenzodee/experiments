package com.orangeandbronze.samples.banking;

import java.util.Date;


public class MoneyTransferTransaction implements BankingTransaction {

	private String fromAccountId;
	private String toAccountId;
	private double amount;
	private Date date;

	public MoneyTransferTransaction(
			String fromAccountId, String toAccountId,
			double amount) {
		this(fromAccountId, toAccountId, amount, new Date());
	}

	public MoneyTransferTransaction(
			String fromAccountId, String toAccountId,
			double amount, Date date) {
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
		this.date = date;
	}

	public String fromAccountId() {
		return fromAccountId;
	}

	public String toAccountId() {
		return toAccountId;
	}

	public double amount() {
		return amount;
	}

	public Date date() {
		return date;
	}

	MoneyTransferTransaction() {
		// as needed by ORM
	}

}
