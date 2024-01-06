package com.asm.sse.controller;

import com.asm.sse.service.NotificationService;
import com.asm.sse.util.ChannelEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
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
    public SseEmitter streamFlux(@PathVariable("id") String id, @RequestParam int tap, HttpServletResponse response) {
        SseEmitter emitter = new SseEmitter();
        emitter.onCompletion(() -> channelEmitter.removeChannel(id +'@'+tap));
        emitter.onTimeout(() -> channelEmitter.removeChannel(id +'@'+tap ));
        channelEmitter.addChannel(id +'@'+tap, emitter);
        response.addHeader("Access-Control-Allow-Origin", "*");
        return emitter;
    }

    @ExceptionHandler(value = AsyncRequestTimeoutException.class)
    public String asyncTimeout(AsyncRequestTimeoutException e) {
        return null;
    }


    @ExceptionHandler(value = IllegalStateException.class)
    public String asyncTimeout(IllegalStateException e) {
        return null;
    }




}
