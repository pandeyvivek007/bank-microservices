package com.pandeyvivek007.accounts.mapper;

import com.pandeyvivek007.accounts.dto.AccountsDto;
import com.pandeyvivek007.accounts.dto.CustomerDto;
import com.pandeyvivek007.accounts.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        if (customerDto == null) {
            return null;
        }

        if (customer == null) {
            customer = new Customer();
        }

        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());


        return customer;
    }

    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        if (customer == null) {
            return null;
        }

        if (customerDto == null) {
            customerDto = new CustomerDto();
        }

        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());

        return customerDto;
    }
}
