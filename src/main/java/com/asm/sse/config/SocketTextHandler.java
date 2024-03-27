package com.asm.sse.config;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    public static  Map<String, WebSocketSession> idToActiveSession = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        String auctionId = (String) session.getAttributes().get("auctionId");

        String payload = message.getPayload();
        JSONObject jsonObject = new JSONObject(payload);


        for (Map.Entry<String, WebSocketSession> otherSession : idToActiveSession.entrySet()) {
            if (otherSession.getKey().equals((String) session.getAttributes().get("auctionId"))) {
                otherSession.getValue().sendMessage(new TextMessage("Hi " + jsonObject.get("user") + " , you are connect by "+ auctionId+ " account"));

            };
        }

    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        idToActiveSession.put((String) session.getAttributes().get("auctionId"), session);
        System.out.println((String) session.getAttributes().get("auctionId"));
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        idToActiveSession.remove((String) session.getAttributes().get("auctionId"));
        System.out.println((String) session.getAttributes().get("auctionId"));

        super.afterConnectionClosed(session, status);
    }

   public static void sendMessage(String id , String message) {
        idToActiveSession.entrySet().stream()
                .filter(it-> it.getKey().contains(id+"@tab="))
                .peek(it -> System.out.println(it.getKey()))
                .forEach(it-> {
                    try {
                        it.getValue().sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        System.out.println("can't send message to account id : ${it}  "+ e.getMessage());
                        throw new RuntimeException(e);
                    }
                });
     }

}
