package com.orangeandbronze.samples.banking.domainmodel;

public interface AccountRepository {

	Account findById(String fromAccountId);

}
