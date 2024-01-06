package com.asm.sse;

import com.asm.sse.config.SocketTextHandler;
 import com.asm.sse.service.NotificationService;
import com.asm.sse.util.ChannelEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.io.IOException;
import java.util.*;

@EnableScheduling
@SpringBootApplication
public class SSEApplication {

	@Autowired
	ChannelEmitter channelEmitter;

	@Autowired
	NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplication.run(SSEApplication.class, args);
	}



	//@Scheduled(fixedRate = 3000)
	public void execute() {
		Map message = new HashMap();
		message.put("data", new Date());

		if(channelEmitter.getEmitters().size()>0){
			channelEmitter.getEmitters().entrySet().stream().forEach(i -> {
				message.put("id",i.getKey());
				System.out.println( "try to send :::::::" + i.getKey());
				try{


					notificationService.sendEventEmitter(i.getKey(),UUID.randomUUID().toString(),message,"NewEvent");
				} catch (Exception e ){
					channelEmitter.removeChannel(i.getKey());
					System.out.println("::::::: cached");

				}
				System.out.println("::::::: done");
			});
		}

	}


}
