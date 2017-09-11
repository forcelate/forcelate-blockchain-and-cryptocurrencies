package com.jdv.exchange;

import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

public interface ReactiveExchange extends Exchange {
    Completable connect();

    Observable<Ticker> getTicker(CurrencyPair currencyPair);
    Observable<Trade> getTrades(CurrencyPair currencyPair);
}
