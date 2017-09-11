package com.jdv;

import com.jdv.exchange.ReactiveExchange;
import com.jdv.exchange.poloniex.PoloniexReactiveExchange;
import com.jdv.utils.ReactiveExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

public class Application {
    public static void main(String[] args) {
        System.out.println("Poloniex WAMP RxJava PoC started...");
        ExchangeSpecification poloniexExchangeSpecification = new ExchangeSpecification(PoloniexReactiveExchange.class.getName());
        ReactiveExchange poloniexReactiveExchange = ReactiveExchangeFactory.INSTANCE.createExchange(poloniexExchangeSpecification);
        poloniexReactiveExchange.connect().blockingAwait();
        System.out.println("===> #1");
        poloniexReactiveExchange.getTicker().forEach(ticker -> {
            System.out.println("===");
            System.out.println(ticker);
            System.out.println("===");
        });
        System.out.println("===> #2");
    }
}
