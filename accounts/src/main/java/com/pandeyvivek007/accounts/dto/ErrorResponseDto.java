package com.pandeyvivek007.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Schema for Error Response Details"
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {
    @Schema(
            description = "API path where the error occurred"
    )
    private String apiPath;

    @Schema(
            description = "HTTP status code of the error"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Detailed error message"
    )
    private String errorMessage;

    @Schema(
            description = "Timestamp when the error occurred"
    )
    private LocalDateTime errorTime;
}
