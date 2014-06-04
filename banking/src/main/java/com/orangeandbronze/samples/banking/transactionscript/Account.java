package com.orangeandbronze.samples.banking.transactionscript;

// @Entity
public class Account {

	// @Id
	private String id;
	private double balance;
	private OverdraftPolicy overdraftPolicy;

	public Account(String id, double balance, OverdraftPolicy overdraftPolicy) {
		this.id = id;
		this.balance = balance;
		this.overdraftPolicy = overdraftPolicy;
	}

	public String getId() {
		return id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public OverdraftPolicy getOverdraftPolicy() {
		return overdraftPolicy;
	}

	public void setOverdraftPolicy(OverdraftPolicy overdraftPolicy) {
		this.overdraftPolicy = overdraftPolicy;
	}

	public static enum OverdraftPolicy {
		NEVER, ALLOWED
	}

	Account() {
		// as needed by ORM
	}

}
