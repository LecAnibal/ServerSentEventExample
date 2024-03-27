package com.asm.sse.controller;

import com.asm.sse.config.SocketTextHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @GetMapping("/test")
    public void execute() {
        System.out.println("test");
        List<Integer> listOfNumbers = new ArrayList<>();
        List<String> sesiones = new ArrayList<>();

        sesiones.add("1234");
        sesiones.add("7890");
        for (int i = 0; i < 500; i++) {
            listOfNumbers.add(i);
        }

        listOfNumbers.forEach(number ->
                {
                    sesiones.stream().forEach(sess -> {
                        SocketTextHandler.sendMessage(sess, "message" + number);
                    });

                }
        );
    }
}
