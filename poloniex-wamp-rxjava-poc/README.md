# poloniex-wamp-rxjava-poc
Poloniex WAMP RxJava PoC — Сonsole application to retrieve Poloniex public data using RxJava

### Warning
1. Connection creation could take up to 3-5 minutes
2. Poloniex API is blocked in Ukraine (API return 403 HTTP status code). You could use VPN

### Notes
Poloniex Ticker - DONE

Poloniex Trades - UNDER DEVELOPMENT / NOT WORKING. 

Websockets `wss://api.poloniex.com` has issue with currency pair channel (e.g. ETH_BTC)
