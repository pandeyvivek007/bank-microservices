package com.pandeyvivek007.accounts.service.impl;

import com.pandeyvivek007.accounts.Repository.AccountsRepository;
import com.pandeyvivek007.accounts.Repository.CustomerRepository;
import com.pandeyvivek007.accounts.dto.AccountsDto;
import com.pandeyvivek007.accounts.dto.CardDto;
import com.pandeyvivek007.accounts.dto.CustomerDetailsDto;
import com.pandeyvivek007.accounts.dto.LoansDto;
import com.pandeyvivek007.accounts.entity.Accounts;
import com.pandeyvivek007.accounts.entity.Customer;
import com.pandeyvivek007.accounts.exception.ResourceNotFoundException;
import com.pandeyvivek007.accounts.mapper.AccountsMapper;
import com.pandeyvivek007.accounts.mapper.CustomerMapper;
import com.pandeyvivek007.accounts.service.ICustomersService;
import com.pandeyvivek007.accounts.service.client.CardsFeignClient;
import com.pandeyvivek007.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;



    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());

        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<CardDto> cardResponse = cardsFeignClient.fetchCardsDetails(mobileNumber);

        ResponseEntity<LoansDto> loansResponse = loansFeignClient.fetchLoansDetails(mobileNumber);

        customerDetailsDto.setCardDto(cardResponse.getBody());
        customerDetailsDto.setLoansDto(loansResponse.getBody());
        return  customerDetailsDto;
    }
}
