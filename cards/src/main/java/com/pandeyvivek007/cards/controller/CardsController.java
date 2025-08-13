package com.pandeyvivek007.cards.controller;

import com.pandeyvivek007.cards.dto.CardDto;
import com.pandeyvivek007.cards.dto.CardsContactInfoDto;
import com.pandeyvivek007.cards.dto.ErrorResponseDto;
import com.pandeyvivek007.cards.dto.ResponseDto;
import com.pandeyvivek007.cards.exception.ResourceNotFoundException;
import com.pandeyvivek007.cards.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "CRUD REST APIs for Cards Management",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);


    private final ICardService iCardService;

    public CardsController(ICardService iCardService) {
        this.iCardService = iCardService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam
                                                      @Pattern(regexp = "(${0,1}[0-9]{10})", message = "Mobile number must be 10 digits")
                                                      String mobileNumber) {
        if(mobileNumber != null) {
            iCardService.createCard(mobileNumber);
            return ResponseEntity.ok(new ResponseDto(HttpStatus.CREATED, "Card created successfully"));
        } else {
            return ResponseEntity.badRequest().body(new ResponseDto(HttpStatus.BAD_REQUEST, "Invalid card data"));
        }
    }


    @Operation(
            summary = "Fetch Card Details REST API",
            description = "REST API to fetch card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardDto> fetchCard(@RequestHeader("eazybank-correlation-id") String correlationId,
                                                @RequestParam
                                                 @Pattern(regexp = "(${0,1}[0-9]{10})", message = "Mobile number must be 10 digits")
                                                 String mobileNumber) {
        logger.debug("eazyBank-correlation-id found: {}", correlationId);
        if (mobileNumber != null) {
            CardDto cardDto = iCardService.getCardDetails(mobileNumber);
            if (cardDto != null) {
                return ResponseEntity.ok(cardDto);
            }
        }

        throw new ResourceNotFoundException("Cards", "mobileNumber", mobileNumber);
    }

    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CardDto cardDto) {
        if (cardDto != null && cardDto.getMobileNumber() != null) {
            iCardService.updateCardDetails(cardDto);
            return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "Card updated successfully"));
        } else {
            throw  new ResourceNotFoundException("Card", "mobileNumber", cardDto.getMobileNumber());

        }
    }

    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to delete Card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam
                                                      @Pattern(regexp = "(${0,1}[0-9]{10})", message = "Mobile number must be 10 digits")
                                                      String mobileNumber) {
        if (mobileNumber != null) {
            iCardService.deleteCard(mobileNumber);
            return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "Card deleted successfully"));
        } else {
            throw new ResourceNotFoundException("Card", "mobileNumber", mobileNumber);
        }
    }


    @Operation(
            summary = "Fetch build version of REST API",
            description = "REST API to fetch build version of cards service"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildVersion);
    }


    @Operation(
            summary = "Fetch contact details of cards REST API",
            description = "REST API to fetch contact details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> cardsContactInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }
}
