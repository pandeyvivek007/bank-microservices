package com.pandeyvivek007.accounts.service.client;

import com.pandeyvivek007.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {


    @GetMapping(value = "api/fetch", consumes = "application/json")
    public ResponseEntity<CardDto> fetchCardsDetails(@RequestParam String mobileNumber);
}
