package com.asm.sse.controller;

import com.asm.sse.service.NotificationService;
import com.asm.sse.util.ChannelEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class SSEController {

    @Autowired
    ChannelEmitter channelEmitter;

    @Autowired
    NotificationService notificationService;

    @GetMapping(path = "/stream/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamFlux(@PathVariable("id") String id , HttpServletResponse response) {
        SseEmitter emitter = new SseEmitter();
        emitter.onCompletion(() -> channelEmitter.removeChannel(id));
        emitter.onTimeout(() -> channelEmitter.removeChannel(id));
        channelEmitter.addChannel(id, emitter);
        response.addHeader("Access-Control-Allow-Origin", "*");
        return emitter;
    }

    @ExceptionHandler(value = AsyncRequestTimeoutException.class)
    public String asyncTimeout(AsyncRequestTimeoutException e) {
        return null;
    }



}
