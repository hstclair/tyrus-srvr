package com.dcc.scratch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.CloseReason;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;


public class RequestHandler implements MessageHandler.Whole<String> {

    Session session;
    RemoteEndpoint.Basic remoteEndpoint;

    Logger log = LogManager.getLogger(RequestHandler.class);

    public RequestHandler(Session session) {
        this.session = session;
        remoteEndpoint = session.getBasicRemote();
    }

    @Override
    public void onMessage(String message) {

        log.trace("received request {}\n", message);

        boolean disconnect = false;

        String reply = null;

        String[] components = message.split("\n", 2);

        switch (components[0]) {

            case "HELLO":

                log.trace("handshake");

                reply = "HELLO";

                break;

            case "PERMUTE":

                log.trace("processing PERMUTE request");

                if (components.length < 2) {
                    reply = "ERROR";

                    log.warn("bad PERMUTE request - no argument");
                } else
                    reply = "PERMUTE\n" + computePermutations(components[1]);

                log.trace("sending reply: {}");

                break;

            case "BYE":

                log.trace("end session");

                reply = "BYE";

                break;
        }

        try {
            if (reply != null) {

                log.trace("sending reply: {}", reply);

                remoteEndpoint.sendText(reply);
            }

            if (disconnect) {

                log.trace("disconnecting");

                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Client requested disconnect"));
            }

        } catch (IOException ex) {

            log.error("exception during request processing", ex);
        }
    }

    String computePermutations(String source) {

        StringBuilder result = new StringBuilder();

        log.trace("constructing permutifier");

        Permutifier permutifier = new Permutifier(source.split("\\s"));

        log.trace("constructed");

        int terms = permutifier.getPermutationCount();

        log.trace("expression has {} permutations", terms);

        for (int index = 0; index < terms; index++) {

            String[] elements = permutifier.getPermutation(index);

            result.append(String.join(" ", elements));
            result.append('\n');
        }

        log.trace("returning result");

        return result.toString();
    }
}
