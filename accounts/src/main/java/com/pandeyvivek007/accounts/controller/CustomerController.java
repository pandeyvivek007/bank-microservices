package com.pandeyvivek007.accounts.controller;

import com.pandeyvivek007.accounts.dto.CardDto;
import com.pandeyvivek007.accounts.dto.CustomerDetailsDto;
import com.pandeyvivek007.accounts.dto.LoansDto;
import com.pandeyvivek007.accounts.service.ICustomersService;
import com.pandeyvivek007.accounts.service.client.CardsFeignClient;
import com.pandeyvivek007.accounts.service.client.LoansFeignClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Customer Details",
        description = "Operations related to customer accounts"
)
@RestController
@RequestMapping(path = "/api", produces = "application/json")
@Validated
public class CustomerController {

    private final ICustomersService customersService;

    public CustomerController(ICustomersService customersService) {
        this.customersService = customersService;
    }


    @Operation(
            summary = "Fetch Customer details",
            description = "This endpoint retrieves the Customer details."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Customer details fetched successfully"
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(@RequestParam
                                                                 @Pattern(regexp = "(${0,1}[0-9]{10})", message = "Mobile number must be 10 digits")
                                                                 String mobileNumber) {
        CustomerDetailsDto customerDetailsDto = customersService.fetchCustomerDetails(mobileNumber);

        return ResponseEntity.ok(customerDetailsDto);
    }
}
