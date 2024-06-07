package com.dummybank.accounts.service.client;

import com.dummybank.accounts.dto.LoansDto;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;

@Component
public class LoansFallback implements LoansFeignClient{

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }

}
