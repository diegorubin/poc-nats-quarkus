package com.diegorubin.poc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class AppLifecycleBean {

    @ApplicationScoped
    public Connection buildConnection() throws IOException, InterruptedException {
        return Nats.connect("nats://localhost:4222");
    }

    void onStart(@Observes StartupEvent event) throws IOException, InterruptedException {

        Connection replyConnection = buildConnection();
        Dispatcher dispatcher = buildConnection().createDispatcher((msg) -> {
            String request = new String(msg.getData(), StandardCharsets.UTF_8);
            System.out.println(request);
            replyConnection.publish(msg.getReplyTo(), "response from reply".getBytes(StandardCharsets.UTF_8));
        });

        dispatcher.subscribe("echo");
    }
}
