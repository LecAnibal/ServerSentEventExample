package com.asm.sse.service;

import com.asm.sse.util.ChannelEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    ChannelEmitter channelEmitter;

    public void sendEventEmitter(String id, String messageId,Object o,String evenType) {
        Optional<SseEmitter> optionalEmitter = channelEmitter.getEmitter(id);

        if (optionalEmitter.isPresent()) {
            SseEmitter emitter = optionalEmitter.get();
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .id(messageId)
                    .data(o)
                    .name(evenType)
                    .reconnectTime(0);
            try {
                emitter.send(event);
                System.out.println("Send message to [" + id + "] > [" + evenType + "] with content :: " + o.toString());
            } catch (IOException e) {
                System.out.println("Could not send message to [" + id + "] " + e);
            }
        } else{
            System.out.println("Could not found data with id [" + id + "] ");

        }
    }
}
