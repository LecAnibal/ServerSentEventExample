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
    public void  execute(){
        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0 ; i<500;i ++ ) {
            listOfNumbers.add(i);
        }

        listOfNumbers.forEach(number ->
                {

                        SocketTextHandler.sendMessage("1234", "message"+number);

                }
        );
    }
}
