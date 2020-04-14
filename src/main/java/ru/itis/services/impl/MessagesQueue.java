package ru.itis.services.impl;

import org.springframework.stereotype.Component;
import ru.itis.dto.ChatMessageDto;
import ru.itis.dto.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessagesQueue {
    private final Map<Long, Map<Long, List<ChatMessageDto>>> unreadMessages;

    public MessagesQueue() {
        unreadMessages = new ConcurrentHashMap<>();
    }

    public void add(ChatMessageDto message) throws InterruptedException {
        synchronized (unreadMessages.get(message.getChatRoom().getId()).get(message.getSender().getId())) {
            for (int i = 0; i < unreadMessages.get(message.getChatRoom().getId()).entrySet().size(); i ++){
                for (UserDto user : message.getChatRoom().getChatters()) {
                    unreadMessages.get(message.getChatRoom().getId()).get(user.getId()).add(message);
                }
            }
            System.out.println("add" + unreadMessages.get(message.getChatRoom().getId()).get(message.getSender().getId()));
            for (Map.Entry<Long, List<ChatMessageDto>> entry : unreadMessages.get(message.getChatRoom().getId()).entrySet()) {
                entry.getValue().notifyAll();
            }
        }

    }

    public List<ChatMessageDto> get(Long roomId, UserDto userDto) throws InterruptedException {
        unreadMessages.computeIfAbsent(roomId, k -> new HashMap<>());
        unreadMessages.get(roomId).computeIfAbsent(userDto.getId(), k -> new ArrayList<>());
        synchronized (unreadMessages.get(roomId).get(userDto.getId())) {
            if (unreadMessages.get(roomId).get(userDto.getId()).isEmpty()) {
                unreadMessages.get(roomId).get(userDto.getId()).wait();
            }
        }
        List<ChatMessageDto> messages = new ArrayList<>(unreadMessages.get(roomId).get(userDto.getId()));
        unreadMessages.get(roomId).get(userDto.getId()).clear();

        return messages;
    }
}
