package com.dummybank.accounts.service;

import com.dummybank.accounts.dto.AccountsDto;
import com.dummybank.accounts.dto.CustomerDto;

public interface IAccountsService {
    void createAccount(CustomerDto customerDto);
    CustomerDto fetchAccount(String mobileNumber);
    boolean updateAccount(CustomerDto customerDto);
    boolean deleteAccount(String mobileNumber);
    boolean updateCommunicationStatus(Long accountNumber);
}
