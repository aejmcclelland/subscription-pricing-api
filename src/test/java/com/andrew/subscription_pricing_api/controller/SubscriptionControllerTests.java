package com.andrew.subscription_pricing_api.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.andrew.subscription_pricing_api.dto.SubscriptionRequest;
import com.andrew.subscription_pricing_api.dto.SubscriptionResponse;
import com.andrew.subscription_pricing_api.model.BillingCycle;
import com.andrew.subscription_pricing_api.model.SubscriptionPlan;
import com.andrew.subscription_pricing_api.service.SubscriptionPricingService;

@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private SubscriptionPricingService subscriptionPricingService;

        @Test
        void shouldReturnCalculatedSubscription() throws Exception {
                SubscriptionRequest request = new SubscriptionRequest(
                                5,
                                SubscriptionPlan.PRO,
                                BillingCycle.MONTHLY);
                SubscriptionResponse response = new SubscriptionResponse(
                                SubscriptionPlan.PRO,
                                5,
                                BillingCycle.MONTHLY,
                                BigDecimal.valueOf(100),
                                BigDecimal.valueOf(1200));

                when(subscriptionPricingService.calculate(request)).thenReturn(response);

                mockMvc.perform(post("/api/subscriptions/calculate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                  "userCount": 5,
                                                  "plan": "PRO",
                                                  "billingCycle": "MONTHLY"
                                                }
                                                """))
                                .andExpect(status().isOk())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.plan").value("PRO"))
                                .andExpect(jsonPath("$.userCount").value(5))
                                .andExpect(jsonPath("$.billingCycle").value("MONTHLY"))
                                .andExpect(jsonPath("$.monthlyCost").value(100))
                                .andExpect(jsonPath("$.annualCost").value(1200));

                verify(subscriptionPricingService).calculate(request);
        }

        @Test
        void shouldReturnBadRequestWhenUserCountIsZero() throws Exception {
                mockMvc.perform(post("/api/subscriptions/calculate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                        "userCount": 0,
                                                        "plan": "PRO",
                                                        "billingCycle": "MONTHLY"
                                                }
                                                """))
                                .andExpect(status().isBadRequest());

                verifyNoInteractions(subscriptionPricingService);
        }

        @Test
        void shouldReturnBadRequestWhenPlanIsMissing() throws Exception {
                mockMvc.perform(post("/api/subscriptions/calculate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                  "userCount": 5,
                                                  "billingCycle": "MONTHLY"
                                                }
                                                """))
                                .andExpect(status().isBadRequest());
                verifyNoInteractions(subscriptionPricingService);
        }

        @Test
        void shouldReturnBadRequestWhenBillingCycleIsMissing() throws Exception {
                mockMvc.perform(post("/api/subscriptions/calculate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                  "userCount": 5,
                                                  "plan": "PRO"
                                                }
                                                """))
                                .andExpect(status().isBadRequest());
                verifyNoInteractions(subscriptionPricingService);
        }
}
