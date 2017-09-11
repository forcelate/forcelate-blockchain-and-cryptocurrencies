package com.jdv.exchange;

import com.jdv.entity.Ticker;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.Exchange;

public interface ReactiveExchange extends Exchange {
    Completable connect();

    Observable<Ticker> getTicker();
}
