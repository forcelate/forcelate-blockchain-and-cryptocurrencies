package com.jdv.exchange.poloniex;

import com.fasterxml.jackson.databind.JsonNode;
import com.jdv.exchange.ReactiveExchange;
import com.jdv.wamp.WampService;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.poloniex.PoloniexAdapters;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.poloniex.PoloniexUtils;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexTicker;

import java.math.BigDecimal;

public class PoloniexReactiveExchange extends PoloniexExchange implements ReactiveExchange {
    private static final String URI = "wss://api.poloniex.com";
    private static final String REALM = "realm1";

    private final WampService wampService = new WampService(URI, REALM);

    @Override
    public Completable connect() {
        return wampService.connect();
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair) {
        return wampService.subscribeOnChannel("ticker").map(
                pubSubData -> {
                    PoloniexMarketData marketData = new PoloniexMarketData();
                    marketData.setLast(new BigDecimal(pubSubData.arguments().get(1).asText()));
                    marketData.setLowestAsk(new BigDecimal(pubSubData.arguments().get(2).asText()));
                    marketData.setHighestBid(new BigDecimal(pubSubData.arguments().get(3).asText()));
                    marketData.setPercentChange(new BigDecimal(pubSubData.arguments().get(4).asText()));
                    marketData.setBaseVolume(new BigDecimal(pubSubData.arguments().get(5).asText()));
                    marketData.setQuoteVolume(new BigDecimal(pubSubData.arguments().get(6).asText()));
                    marketData.setHigh24hr(new BigDecimal(pubSubData.arguments().get(8).asText()));
                    marketData.setLow24hr(new BigDecimal(pubSubData.arguments().get(9).asText()));
                    PoloniexTicker ticker = new PoloniexTicker(marketData, PoloniexUtils.toCurrencyPair(pubSubData.arguments().get(0).asText()));
                    return PoloniexAdapters.adaptPoloniexTicker(ticker, ticker.getCurrencyPair());
                })
                .filter(ticker -> ticker.getCurrencyPair().equals(currencyPair));
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair) {
        String channel = currencyPair.toString().replace("/", "_");
        System.out.println("CHANNEL: " + channel);
        return wampService.subscribeOnChannel(channel).map(
                pubSubData -> {
                    for (int i = 0; i < pubSubData.arguments().size(); i++) {
                        JsonNode item = pubSubData.arguments().get(i);
                        String tradeType = item.get("type").asText();
                        System.out.println("===>");
                        System.out.println("tradeType: " + tradeType);
                        System.out.println("===>");
                        if ("newTrade".equals(tradeType)) {

                        } else {

                        }
                    }
                    return null;
                });
    }
}
