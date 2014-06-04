package com.orangeandbronze.samples.banking.domainmodel;

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

	public String id() {
		return id;
	}

	public double balance() {
		return balance;
	}

	public void debit(double amount) {
		this.overdraftPolicy.preDebit(this, amount);
		this.balance = this.balance - amount;
		this.overdraftPolicy.postDebit(this, amount);
	}

	public void credit(double amount) {
		this.balance = this.balance + amount;
	}

}
