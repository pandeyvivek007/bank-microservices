package com.pandeyvivek007.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
@Getter
@Setter
@AllArgsConstructor
public class ResponseDto {

    @Schema(
            description = "Status code in the response"
    )
    private HttpStatus status;

    @Schema(
            description = "Status message in the response"
    )
    private String message;
}
