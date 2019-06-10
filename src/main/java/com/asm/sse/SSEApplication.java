package com.asm.sse;

import com.asm.sse.service.NotificationService;
import com.asm.sse.util.ChannelEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

	@Scheduled(fixedRate = 3000)
	public void execute() {
		Map message = new HashMap();
		message.put("data", new Date());
		channelEmitter.getEmitters().entrySet().stream().forEach(i -> {
			message.put("id",i.getKey());
			notificationService.sendEventEmitter(i.getKey(),UUID.randomUUID().toString(),message,"NewEvent");
		});
	}


}
