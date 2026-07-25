package com.andrew.subscription_pricing_api.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.subscription_pricing_api.dto.SubscriptionRequest;
import com.andrew.subscription_pricing_api.dto.SubscriptionResponse;
import com.andrew.subscription_pricing_api.service.SubscriptionPricingService;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionPricingService subscriptionPricingService;

    public SubscriptionController(SubscriptionPricingService subscriptionPricingService) {
        this.subscriptionPricingService = subscriptionPricingService;
    }

    @PostMapping("/calculate")
    public SubscriptionResponse calculate(
            @Valid @RequestBody SubscriptionRequest request) {
        return subscriptionPricingService.calculate(request);
    }
}
