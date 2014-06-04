package com.orangeandbronze.samples.banking.domainmodel;

import com.orangeandbronze.samples.banking.DebitException;

public class NoOverdraftAllowed implements OverdraftPolicy {

	@Override
	public void preDebit(Account account, double amount) {
		if (account.balance() - amount < 0) {
			throw new DebitException("Insufficient funds");
		}
	}

	@Override
	public void postDebit(Account account, double amount) {
	}

}
