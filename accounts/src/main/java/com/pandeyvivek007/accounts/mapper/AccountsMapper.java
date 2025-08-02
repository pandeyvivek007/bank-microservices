package com.pandeyvivek007.accounts.mapper;

import com.pandeyvivek007.accounts.dto.AccountsDto;
import com.pandeyvivek007.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto) {
        if (accounts == null) {
            return null;
        }

        if (accountsDto == null) {
            accountsDto = new AccountsDto();
        }

        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());

        return accountsDto;

    }

    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        if (accountsDto == null) {
            return null;
        }

        if (accounts == null) {
            accounts = new Accounts();
        }

        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());

        return accounts;
    }

}
