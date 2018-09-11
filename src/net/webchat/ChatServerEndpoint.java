package net.webchat;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/chat", configurator = ChatServerEndPointConfigurator.class)
public class ChatServerEndpoint {

    static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void handleOpen(Session session) {
        users.add(session);
    }

    @OnMessage
    public void handleMessage(String message, Session userSession) throws IOException {
        String username = (String) userSession.getUserProperties().get("username");
        if (username == null) {
            userSession.getUserProperties().put("username", message);
            userSession.getBasicRemote().sendText("System: you are connectd as " + message);
        } else {
            for (Session session : users) {
                session.getBasicRemote().sendText(username + ": " + message);
            }
        }
    }

    @OnClose
    public void handleClose(Session session) throws IOException {
        String username = (String) session.getUserProperties().get("username");
        users.remove(session);
        for (Session s : users) {
            s.getBasicRemote().sendText("System: " + username + " had left.");
        }
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

}
