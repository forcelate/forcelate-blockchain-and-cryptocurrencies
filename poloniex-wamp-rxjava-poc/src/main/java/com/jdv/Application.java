package com.jdv;

import com.jdv.exchange.ReactiveExchange;
import com.jdv.exchange.poloniex.PoloniexReactiveExchange;
import com.jdv.utils.ReactiveExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class Application {
    public static void main(String[] args) {
        System.out.println("Poloniex WAMP RxJava PoC started...");
        ExchangeSpecification poloniexExchangeSpecification = new ExchangeSpecification(PoloniexReactiveExchange.class.getName());
        ReactiveExchange poloniexReactiveExchange = ReactiveExchangeFactory.INSTANCE.createExchange(poloniexExchangeSpecification);
        poloniexReactiveExchange.connect().blockingAwait();
        // ticker
        CurrencyPair currencyPair = CurrencyPair.ETH_BTC;
        poloniexReactiveExchange.getTicker(currencyPair).forEach(ticker -> {
            System.out.println(new Date() + ": Poloniex WebSockets [TICKER]: " + ticker.toString());
        });
        // all trades
        poloniexReactiveExchange.getTrades(currencyPair).forEach(trade -> {
            System.out.println(new Date() + ": Poloniex WebSockets [TRADES]: " + trade);
        });

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        try {
            console.readLine();
        } catch (Exception e) {
            // ignore
        }

    }
}
