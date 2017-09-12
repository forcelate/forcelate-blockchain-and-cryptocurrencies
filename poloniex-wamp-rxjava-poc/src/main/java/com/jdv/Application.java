package com.jdv;

import com.jdv.exchange.ReactiveExchange;
import com.jdv.exchange.poloniex.PoloniexReactiveExchange;
import com.jdv.utils.ReactiveExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.info("Poloniex WAMP RxJava PoC started...");
        ExchangeSpecification poloniexExchangeSpecification = new ExchangeSpecification(PoloniexReactiveExchange.class.getName());
        ReactiveExchange poloniexReactiveExchange = ReactiveExchangeFactory.INSTANCE.createExchange(poloniexExchangeSpecification);
        poloniexReactiveExchange.connect().blockingAwait();
        // ticker
        CurrencyPair currencyPair = CurrencyPair.ETH_BTC;
        poloniexReactiveExchange.getTicker(currencyPair).forEach(ticker -> {
            LOGGER.info("Poloniex WebSockets [TICKER]: " + ticker.toString());
        });
        // all trades
        poloniexReactiveExchange.getTrades(currencyPair).forEach(trade -> {
            LOGGER.info("Poloniex WebSockets [TRADES]: " + trade);
        });

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        try {
            console.readLine();
        } catch (Exception e) {
            // ignore
        }

    }
}
