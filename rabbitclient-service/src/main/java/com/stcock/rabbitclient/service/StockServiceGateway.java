package com.stcock.rabbitclient.service;

import com.stcock.rabbitclient.service.model.TradeRequest;

public interface StockServiceGateway {
    void send(TradeRequest request);
}
