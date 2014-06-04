package com.orangeandbronze.samples.banking.domainmodel;

public interface OverdraftPolicy {

	void preDebit(Account account, double amount);

	void postDebit(Account account, double amount);

}
