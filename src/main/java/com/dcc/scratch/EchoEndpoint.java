package com.dcc.scratch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


@ServerEndpoint(value="/echo")
public class EchoEndpoint extends Endpoint {

    Logger log = LogManager.getLogger(EchoEndpoint.class);

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
//        RemoteEndpoint.Basic b = session.getBasicRemote();

        session.addMessageHandler(new RequestHandler(session));

        System.out.println("Connected");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {

        System.out.printf("Closing - %s:%s\n", closeReason.getCloseCode(), closeReason.getReasonPhrase());
    }

//
//    @OnMessage
//    public String onMessage(String message, Session session) {
//        return message;
//    }
}
