package com.andrew.subscription_pricing_api.dto;

import com.andrew.subscription_pricing_api.model.BillingCycle;
import com.andrew.subscription_pricing_api.model.SubscriptionPlan;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SubscriptionRequest(
        @Min(1) int userCount,
        @NotNull SubscriptionPlan plan,
        @NotNull BillingCycle billingCycle
) {
}