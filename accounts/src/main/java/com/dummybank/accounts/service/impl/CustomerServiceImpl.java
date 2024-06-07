package com.dummybank.accounts.service.impl;

import com.dummybank.accounts.dto.AccountsDto;
import com.dummybank.accounts.dto.CardsDto;
import com.dummybank.accounts.dto.CustomerDetailsDto;
import com.dummybank.accounts.dto.LoansDto;
import com.dummybank.accounts.entity.Accounts;
import com.dummybank.accounts.entity.Customer;
import com.dummybank.accounts.exception.ResourceNotFoundException;
import com.dummybank.accounts.mapper.AccountsMapper;
import com.dummybank.accounts.mapper.CustomerMapper;
import com.dummybank.accounts.repository.AccountsRepository;
import com.dummybank.accounts.repository.CustomerRepository;
import com.dummybank.accounts.service.ICustomerService;
import com.dummybank.accounts.service.client.CardsFeignClient;
import com.dummybank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if (cardsDtoResponseEntity != null) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if (loansDtoResponseEntity != null) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }

        return customerDetailsDto;
    }
}
