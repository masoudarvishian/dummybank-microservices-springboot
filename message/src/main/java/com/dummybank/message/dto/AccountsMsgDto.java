package com.dummybank.message.dto;

public record AccountsMsgDto(Long accountNumber, String name, String email, String mobileNumber) {
}
