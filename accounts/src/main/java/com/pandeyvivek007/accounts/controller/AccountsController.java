package com.pandeyvivek007.accounts.controller;

import com.pandeyvivek007.accounts.constants.AccountsConstants;
import com.pandeyvivek007.accounts.dto.CustomerDto;
import com.pandeyvivek007.accounts.dto.ErrorResponseDto;
import com.pandeyvivek007.accounts.dto.ResponseDto;
import com.pandeyvivek007.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Accounts",
        description = "Operations related to customer accounts"
)
@RestController
@RequestMapping(path = "/api", produces = "application/json")
@Validated
public class AccountsController {

    @Autowired
    private IAccountsService iAccountsService;

    @Operation(
            summary = "Create a new customer account",
            description = "This endpoint allows you to create a new customer account with the provided details."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Account created successfully"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch account details",
            description = "This endpoint retrieves the account details for a customer based on their mobile number."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account details fetched successfully"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountsDetails(@RequestParam
                                                                @Pattern(regexp = "(${0,1}[0-9]{10})", message = "Mobile number must be 10 digits")
                                                                    String mobileNumber) {
        CustomerDto customerDto = iAccountsService.fetchAccountDetails(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);

    }

    @Operation(
            summary = "Update account details",
            description = "This endpoint allows you to update the account details of a customer."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete a customer account",
            description = "This endpoint allows you to delete a customer account based on their mobile number."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account deleted successfully"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "(${0,1}[0-9]{10})", message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

}
