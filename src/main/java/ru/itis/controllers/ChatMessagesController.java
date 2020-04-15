package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.ChatMessageDto;
import ru.itis.dto.ChatMessageFormDto;
import ru.itis.dto.ChatRoomDto;
import ru.itis.dto.UserDto;
import ru.itis.security.CurrentUser;
import ru.itis.services.impl.MessagesComponent;
import ru.itis.services.interfaces.ChatMessagesService;
import ru.itis.services.interfaces.ChatRoomsService;
import ru.itis.services.interfaces.UsersService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ChatMessagesController {
    private final ChatMessagesService chatMessagesService;
    private final UsersService usersService;
    private final ChatRoomsService chatRoomsService;
    @Autowired
    MessagesComponent messagesComponent;

    public ChatMessagesController(ChatMessagesService chatMessagesService,
                                  UsersService usersService,
                                  ChatRoomsService chatRoomsService) {
        this.chatMessagesService = chatMessagesService;
        this.usersService = usersService;
        this.chatRoomsService = chatRoomsService;
    }

    @GetMapping("/suppMessages")
    public ResponseEntity<List<ChatMessageDto>> getMessages(@RequestParam("roomId") Long roomId) {
        List<ChatMessageDto> techSupportMessages = chatMessagesService.getTechSupportMessages(roomId);
        System.out.println(techSupportMessages);
        return ResponseEntity.ok(techSupportMessages);
    }

    @PostMapping("/message")
    public ResponseEntity receiveMessage(@RequestBody ChatMessageFormDto chatMessageForm, @CurrentUser UserDetails userDetails) {
        String email = userDetails.getUsername();
        UserDto user = usersService.findUser(email);

        ChatRoomDto chatRoom = chatRoomsService.getRoom(chatMessageForm.getRoomId());

        ChatMessageDto chatMessage = ChatMessageDto.builder()
                .sender(user)
                .time(LocalDateTime.now())
                .text(chatMessageForm.getText())
                .chatRoom(chatRoom)
                .build();

        chatMessagesService.receiveMessage(chatMessage);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/message")
    public ResponseEntity<List<ChatMessageDto>> getMessage(@RequestParam("roomId") Long roomId,
                                                           @RequestParam("pageId") String pageId,
                                                           @CurrentUser UserDetails userDetails) throws InterruptedException {
        List<ChatMessageDto> messages = messagesComponent.get(roomId, pageId);
        return ResponseEntity.ok(messages);
    }
}
