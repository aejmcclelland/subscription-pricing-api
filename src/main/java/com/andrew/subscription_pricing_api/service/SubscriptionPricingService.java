package com.andrew.subscription_pricing_api.service;

import com.andrew.subscription_pricing_api.dto.SubscriptionRequest;
import com.andrew.subscription_pricing_api.dto.SubscriptionResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SubscriptionPricingService {

    public SubscriptionResponse calculate(SubscriptionRequest request) {
        BigDecimal monthlyPricePerUser = switch (request.plan()) {
            case BASIC -> BigDecimal.valueOf(10);
            case PRO -> BigDecimal.valueOf(20);
            case ENTERPRISE -> BigDecimal.valueOf(40);
        };

        BigDecimal monthlyCost = monthlyPricePerUser.multiply(
                BigDecimal.valueOf(request.userCount())
        );
        BigDecimal annualCost = monthlyCost.multiply(BigDecimal.valueOf(12));

        return new SubscriptionResponse(
                request.plan(),
                request.userCount(),
                request.billingCycle(),
                monthlyCost,
                annualCost
        );
    }
}
