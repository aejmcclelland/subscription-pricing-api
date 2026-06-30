package com.andrew.subscription_pricing_api.dto;

import java.math.BigDecimal;

import com.andrew.subscription_pricing_api.model.BillingCycle;
import com.andrew.subscription_pricing_api.model.SubscriptionPlan;

public record SubscriptionResponse(
    SubscriptionPlan plan,
    int userCount,
    BillingCycle billingCycle,
    BigDecimal monthlyCost,
    BigDecimal annualCost
) {
} 