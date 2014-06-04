package com.orangeandbronze.samples.banking.domainmodel;

import com.orangeandbronze.samples.banking.DebitException;

public class LimitedOverdraft implements OverdraftPolicy {

	private final double limit;

	public LimitedOverdraft(double limit) {
		super();
		this.limit = limit;
	}

	@Override
	public void preDebit(Account account, double amount) {
		final double newBalance = account.balance() - amount;
		if (newBalance < -limit) {
			throw new DebitException("Overdraft limit (of " + limit + ") exceeded: " + newBalance);
		}
	}

	@Override
	public void postDebit(Account account, double amount) {
	}

}
