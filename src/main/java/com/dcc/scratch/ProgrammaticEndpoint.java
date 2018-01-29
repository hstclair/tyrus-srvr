package com.dcc.scratch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

public class ProgrammaticEndpoint extends Endpoint {

    Logger log = LogManager.getLogger(ProgrammaticEndpoint.class);

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

        log.trace("Connected");

        session.addMessageHandler(new RequestHandler(session));
    }
}
