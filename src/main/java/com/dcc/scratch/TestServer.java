package com.dcc.scratch;

import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestServer {

    public void runServer() {


        Server server = new Server("localhost", 8025, "/websockets", null, ProgrammaticServerApplicationConfig.class);
//        Server server = new Server("localhost", 8025, "/websockets", null, EchoEndpoint.class);

        try {
            server.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Please press <Enter> to stop the server.\n");
            reader.readLine();
        } catch (IOException | DeploymentException ex) {
            ex.printStackTrace();
        } finally {
            server.stop();
        }
    }

}
