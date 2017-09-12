package com.jdv.wamp;

import com.jdv.Application;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.wamp.jawampa.PubSubData;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.connection.IWampConnectorProvider;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

import java.util.concurrent.TimeUnit;

public class WampService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private static final int DEFAULT_RECONNECT_INTERVAL_IN_SECONDS = 5;

    private final String uri;
    private final String realm;

    private WampClient client;
    private WampClient.State clientState;

    public WampService(String uri, String realm) {
        this.uri = uri;
        this.realm = realm;
    }

    public Completable connect() {
        return Completable.create(completable -> {
            try {
                // connection
                IWampConnectorProvider connectorProvider = new NettyWampClientConnectorProvider();
                this.client = new WampClientBuilder()
                        .withConnectorProvider(connectorProvider)
                        .withUri(uri)
                        .withRealm(realm)
                        .withInfiniteReconnects()
                        .withReconnectInterval(DEFAULT_RECONNECT_INTERVAL_IN_SECONDS, TimeUnit.SECONDS)
                        .build();
                // status
                this.client.statusChanged().subscribe(state -> {
                    LOGGER.info("Poloniex websocket client status changed to " + state + ". Please wait...");
                    // negative case
                    boolean newStateDisconnectedState = (state instanceof WampClient.DisconnectedState);
                    boolean clientStateConnectingState = (this.clientState instanceof WampClient.ConnectingState);
                    if (newStateDisconnectedState && clientStateConnectingState) {
                        WampClient.DisconnectedState disconnectedState = (WampClient.DisconnectedState) state;
                        Throwable throwableReason = disconnectedState.disconnectReason();
                        if (throwableReason != null) {
                            completable.onError(throwableReason);
                        } else {
                            completable.onError(new RuntimeException("Wamp client problems. Please check configuration"));
                        }
                    }
                    // positive case
                    this.clientState = state;
                    boolean newStateConnectedState = (state instanceof WampClient.ConnectedState);
                    if (newStateConnectedState) {
                        LOGGER.info("Poloniex websocket client status changed to Connected. Hooray!");
                        completable.onComplete();
                    }
                });
                this.client.open();
            } catch (Exception e) {
                completable.onError(e);
            }
        });
    }

    public Observable<PubSubData> subscribeOnChannel(String channel) {
        boolean clientStateConnectedState = (this.clientState instanceof WampClient.ConnectedState);
        if (clientStateConnectedState) {
            return RxJavaInterop.toV2Observable(this.client.makeSubscription(channel));
        } else {
            return Observable.error(new RuntimeException("Wamp client problems. Client state is NOT connected"));
        }
    }
}
