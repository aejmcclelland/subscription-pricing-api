package com.andrew.subscription_pricing_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.andrew.subscription_pricing_api.service.SubscriptionPricingService;

@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTests {

    @Autowired 
    private MockMvc mockMvc;

    @MockitoBean
    private SubscriptionPricingService subscriptionPricingService;

    
}
