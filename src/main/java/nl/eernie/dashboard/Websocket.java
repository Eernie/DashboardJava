package nl.eernie.dashboard;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/rest/ws")
public class Websocket {
    @OnOpen
    public void open(Session session) {
        System.out.println(session);
    }

    @OnClose
    public void close(Session session) {
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
    }
}
