package com.pandeyvivek007.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Accounts",
        description = "Schema for Account Details"
)
@Data
public class AccountsDto {

    @NotEmpty(message = "Account number cannot be empty")
    @Pattern(regexp = "($[0-9])", message = "Account number must be 10 digits")
    @Schema(
            description = "Account number of the customer",
            example = "1234567890"
    )
    private Long accountNumber;

    @Schema(
            description = "Type of the account",
            example = "Savings"
    )
    @NotEmpty(message = "Account type cannot be empty")
    private String accountType;

    @Schema(
            description = "Address of the branch",
            example = "123 Main St, City, Country"
    )
    @NotEmpty(message = "Branch address cannot be empty")
    private String branchAddress;
}
