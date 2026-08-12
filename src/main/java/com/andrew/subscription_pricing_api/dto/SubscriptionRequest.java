package com.andrew.subscription_pricing_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import com.andrew.subscription_pricing_api.model.BillingCycle;
import com.andrew.subscription_pricing_api.model.SubscriptionPlan;
import com.andrew.subscription_pricing_api.model.SupportedCurrency;

public record SubscriptionRequest(
                @Min(1) int userCount,
                @NotNull SubscriptionPlan plan,
                @NotNull BillingCycle billingCycle,
                @NotNull SupportedCurrency currency)

{

}