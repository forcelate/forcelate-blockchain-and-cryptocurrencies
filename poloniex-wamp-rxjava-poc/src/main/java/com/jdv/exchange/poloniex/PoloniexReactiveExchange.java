package com.jdv.exchange.poloniex;

import com.jdv.entity.Ticker;
import com.jdv.exchange.ReactiveExchange;
import com.jdv.wamp.WampService;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.poloniex.PoloniexExchange;
import ws.wamp.jawampa.PubSubData;

public class PoloniexReactiveExchange extends PoloniexExchange implements ReactiveExchange {
    private static final String URI = "wss://api.poloniex.com";
    private static final String REALM = "realm1";

    private final WampService wampService = new WampService(URI, REALM);

    @Override
    public Completable connect() {
        return wampService.connect();
    }

    @Override
    public Observable<Ticker> getTicker() {
        System.out.println("===> ticker");
        Observable<PubSubData> ticker = wampService.subscribeOnChannel("ticker");
        System.out.println(ticker);
        return ticker.map(
                pubSubData -> {
                    System.out.println("---");
                    System.out.println(pubSubData.arguments().get(1).asText());
                    System.out.println(pubSubData.arguments().get(2).asText());
                    System.out.println("---");
                    return Ticker.builder()
                            .build();
                }
        );
    }
}
