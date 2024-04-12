package com.polarbookshop.dispatcherservice;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project dispatcher-service
 * @org Cerebrau
 */
public record OrderAcceptedMessage(
    Long orderId
) {}
