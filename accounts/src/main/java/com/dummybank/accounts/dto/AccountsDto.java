package com.dummybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountsDto {
    @Schema(
            description = "Account number of dummy bank account", example = "1234567890"
    )
    @NotEmpty
    @Pattern(regexp = "^$|[0-9]{10}", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of dummy bank account", example = "Saving"
    )
    @NotEmpty(message = "Account type cannot be null or empty")
    private String accountType;

    @Schema(
            description = "Branch address of dummy bank account", example = "123 New York"
    )
    @NotEmpty(message = "Branch address cannot be null or empty")
    private String branchAddress;
}
