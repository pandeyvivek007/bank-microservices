package com.pandeyvivek007.accounts.service;

import com.pandeyvivek007.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
