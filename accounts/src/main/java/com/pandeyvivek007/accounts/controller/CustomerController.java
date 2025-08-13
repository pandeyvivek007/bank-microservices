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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Customer Details",
        description = "Operations related to customer accounts"
)
@RestController
@RequestMapping(path = "/api", produces = "application/json")
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);


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
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(@RequestHeader("eazybank-correlation-id") String correlationId,

                                                                @RequestParam
                                                                 @Pattern(regexp = "(${0,1}[0-9]{10})", message = "Mobile number must be 10 digits")
                                                                 String mobileNumber) {
        logger.debug("eazyBank-correlation-id found: {}", correlationId);

        CustomerDetailsDto customerDetailsDto = customersService.fetchCustomerDetails(mobileNumber, correlationId);

        return ResponseEntity.ok(customerDetailsDto);
    }
}
