package com.diegorubin.poc;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import io.nats.client.Connection;
import io.nats.client.Message;

@Path("/echo")
public class ReactiveResource {

    @Inject
    Connection nc;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String echo() throws InterruptedException, TimeoutException, ExecutionException {

        Future<Message> incoming = nc.request("echo", "message".getBytes(StandardCharsets.UTF_8));
        Message msg = incoming.get(500, TimeUnit.MILLISECONDS);
        String response = new String(msg.getData(), StandardCharsets.UTF_8);

        return response;
    }
}
