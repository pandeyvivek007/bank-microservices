package com.pandeyvivek007.accounts.service.impl;

import com.pandeyvivek007.accounts.Repository.AccountsRepository;
import com.pandeyvivek007.accounts.Repository.CustomerRepository;
import com.pandeyvivek007.accounts.constants.AccountsConstants;
import com.pandeyvivek007.accounts.dto.AccountsDto;
import com.pandeyvivek007.accounts.dto.CustomerDto;
import com.pandeyvivek007.accounts.entity.Accounts;
import com.pandeyvivek007.accounts.entity.Customer;
import com.pandeyvivek007.accounts.exception.CustomerAlreadyExistsException;
import com.pandeyvivek007.accounts.exception.ResourceNotFoundException;
import com.pandeyvivek007.accounts.mapper.AccountsMapper;
import com.pandeyvivek007.accounts.mapper.CustomerMapper;
import com.pandeyvivek007.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Constructor to initialize the repositories.
     *
     * @param customerDto the repository for account operations
     *
     */

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already exists with given mobile number: " + customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);

        System.out.println(createAccount(savedCustomer).getAccountNumber());

        accountsRepository.save(createAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
       if(customerDto.getAccountsDto() != null){
           Accounts accounts = accountsRepository.findById(customerDto.getAccountsDto().getAccountNumber()).orElseThrow(
                   () -> new ResourceNotFoundException("Accounts", "accountNumber", customerDto.getAccountsDto().getAccountNumber().toString())
           );

           AccountsMapper.mapToAccounts(customerDto.getAccountsDto(), accounts);
           accounts = accountsRepository.save(accounts);

           Long customerId = accounts.getCustomerId();
           Customer customer = customerRepository.findById(customerId).orElseThrow(
                     () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
           );

           CustomerMapper.mapToCustomer(customerDto, customer);
           customerRepository.save(customer);
           isUpdated = true;
       }
       return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    /**
     * Creates a new account for the given customer.
     *
     * @param customer the customer for whom the account is created
     * @return a new Accounts object with the generated account details
     */

    private Accounts createAccount(Customer customer) {
        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
        System.out.println("Generated Account Number: " + randomAccountNumber);

        newAccounts.setAccountNumber(randomAccountNumber);
        newAccounts.setAccountType(AccountsConstants.SAVINGS);
        newAccounts.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccounts;
    }
}
