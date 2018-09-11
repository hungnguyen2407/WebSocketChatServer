package net.webchat;

import javax.websocket.server.ServerEndpointConfig;

public class ChatServerEndPointConfigurator extends ServerEndpointConfig.Configurator {
    private static ChatServerEndpoint chatServer = new ChatServerEndpoint();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T)chatServer;
    }
}
