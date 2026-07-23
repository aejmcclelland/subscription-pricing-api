package com.andrew.subscription_pricing_api.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.andrew.subscription_pricing_api.dto.SubscriptionRequest;
import com.andrew.subscription_pricing_api.dto.SubscriptionResponse;
import com.andrew.subscription_pricing_api.model.BillingCycle;
import com.andrew.subscription_pricing_api.model.SubscriptionPlan;

class SubscriptionPricingServiceTests {

    private final SubscriptionPricingService service = new SubscriptionPricingService();

    @Test
    void shouldCalculateProMonthlySubscription() {

        SubscriptionRequest request = new SubscriptionRequest(
                5,
                SubscriptionPlan.PRO,
                BillingCycle.MONTHLY);

        SubscriptionResponse response = service.calculate(request);

        assertAll(
                () -> assertEquals(SubscriptionPlan.PRO, response.plan()),
                () -> assertEquals(5, response.userCount()),
                () -> assertEquals(BillingCycle.MONTHLY, response.billingCycle()),
                () -> assertEquals(BigDecimal.valueOf(100), response.monthlyCost()),
                () -> assertEquals(BigDecimal.valueOf(1200), response.annualCost()));

    }
    @Test
    void shouldCalculateBasicMonthlySubscription() {

        SubscriptionRequest request = new SubscriptionRequest(
                3,
                SubscriptionPlan.BASIC,
                BillingCycle.MONTHLY);

        SubscriptionResponse response = service.calculate(request);

        assertAll(
                () -> assertEquals(SubscriptionPlan.BASIC, response.plan()),
                () -> assertEquals(3, response.userCount()),
                () -> assertEquals(BillingCycle.MONTHLY, response.billingCycle()),
                () -> assertEquals(BigDecimal.valueOf(30), response.monthlyCost()),
                () -> assertEquals(BigDecimal.valueOf(360), response.annualCost()));

    }
    @Test
    void shouldCalculateEnterpriseAnnualSubscription() {

        SubscriptionRequest request = new SubscriptionRequest(
                10,
                SubscriptionPlan.ENTERPRISE,
                BillingCycle.ANNUAL);

        SubscriptionResponse response = service.calculate(request);

        assertAll(
                () -> assertEquals(SubscriptionPlan.ENTERPRISE, response.plan()),
                () -> assertEquals(10, response.userCount()),
                () -> assertEquals(BillingCycle.ANNUAL, response.billingCycle()),
                () -> assertEquals(BigDecimal.valueOf(400), response.monthlyCost()),
                () -> assertEquals(BigDecimal.valueOf(4800), response.annualCost()));

    }
    @Test
    void shouldCalculateBasicAnnualSubscription(){

        SubscriptionRequest request = new SubscriptionRequest(1, SubscriptionPlan.BASIC, BillingCycle.ANNUAL);

        SubscriptionResponse response = service.calculate(request);

        assertAll(
                () -> assertEquals(SubscriptionPlan.BASIC, response.plan()),
                () -> assertEquals(1, response.userCount()),
                () -> assertEquals(BillingCycle.ANNUAL, response.billingCycle()),
                () -> assertEquals(BigDecimal.valueOf(10), response.monthlyCost()),
                () -> assertEquals(BigDecimal.valueOf(120), response.annualCost()));
    }
    
}
