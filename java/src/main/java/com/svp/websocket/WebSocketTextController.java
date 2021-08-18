package com.svp.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketTextController {
	
	@Autowired
    	SimpMessagingTemplate template;
	
	
	public ResponseEntity<Void> sendMessage(@DestinationVariable String to,@RequestBody TextMessageDTO textMessageDTO) {
		template.convertAndSend("/topic/message/"+to, textMessageDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@MessageMapping("/sendMessage/{to}")
	
	public void receiveMessage(@DestinationVariable String to, @Payload TextMessageDTO textMessageDTO) {
		// receive message from client
		System.out.println(to);
		template.convertAndSend("/topic/message/"+to, textMessageDTO);
		
	}
	
	
	@SendTo("/topic/message")
	public TextMessageDTO broadcastMessage(@Payload TextMessageDTO textMessageDTO) {
		return textMessageDTO;
	}

	@Scheduled(fixedDelay = 2000)
	public void thisISTest(){
		System.out.println("working");
	}
}
