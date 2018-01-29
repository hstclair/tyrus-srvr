package com.dcc.scratch;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import java.util.*;

public class ProgrammaticServerApplicationConfig implements ServerApplicationConfig {

    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> set) {

        Set<ServerEndpointConfig> result = new HashSet<>();

        result.add(ServerEndpointConfig.Builder.create(ProgrammaticEndpoint.class, "/permutifier").build());

        return result;
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> set) {
        return Collections.emptySet();
    }
}
