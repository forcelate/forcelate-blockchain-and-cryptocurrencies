package com.jdv.utils;

import com.jdv.exchange.ReactiveExchange;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReactiveExchangeFactory {
    INSTANCE;

    public ReactiveExchange createExchange(ExchangeSpecification exchangeSpecification) {
        String exchangeClassName = exchangeSpecification.getExchangeClassName();
        try {
            Class exchangeProviderClass = Class.forName(exchangeClassName);
            if (Exchange.class.isAssignableFrom(exchangeProviderClass)) {
                ReactiveExchange exchange = (ReactiveExchange) exchangeProviderClass.newInstance();
                exchange.applySpecification(exchangeSpecification);
                return exchange;
            } else {
                throw new RuntimeException(exchangeClassName + "class is not assignable from 'org.knowm.xchange.Exchange'");
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Problem starting Exchange. Please contact administrator", e);
        }
    }
}
