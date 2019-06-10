package com.asm.sse.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Scope(value = "singleton")
@Component
public class ChannelEmitter {
    private final Map<String, SseEmitter> emitters = new HashMap<>();

    public void addChannel(String key, SseEmitter emitter) {
        emitters.put(key, emitter);
    }

    public void removeChannel(String key) {
        emitters.remove(key);
    }

    public Optional<SseEmitter> getEmitter(String id) {
        return Optional.of(emitters.get(id));
    }

    public Map<String, SseEmitter> getEmitters() {
        return emitters;
    }

}
