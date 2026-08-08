package com.andrew.subscription_pricing_api.error;

public record ApiErrorResponse(
    String message,
    int status,
    long timestamp,
    String error,
    String field
) {

}
