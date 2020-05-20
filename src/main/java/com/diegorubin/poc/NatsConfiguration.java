package com.diegorubin.poc;

import java.io.IOException;

import io.nats.client.Connection;
import io.nats.client.Nats;

public class NatsConfiguration {

    public Connection buildConnection() throws IOException, InterruptedException {
        return Nats.connect("nats://localhost:4222");
    }
}
