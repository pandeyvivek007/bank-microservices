package com.pandeyvivek007.accounts.service;

import com.pandeyvivek007.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Creates a new account for the given customer.
     *
     * @param customerDto the customer details for account creation
     */

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccountDetails(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);


}
