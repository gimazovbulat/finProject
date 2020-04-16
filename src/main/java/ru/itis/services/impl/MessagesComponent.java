package ru.itis.services.impl;

import org.springframework.stereotype.Component;
import ru.itis.dto.ChatMessageDto;

import java.util.*;

@Component
public class MessagesComponent {
    private final Map<String, List<ChatMessageDto>> messages;
    private final Map<Long, Set<String>> openRooms;

    public MessagesComponent() {
        messages = new HashMap<>();
        openRooms = new HashMap<>();
    }

    public void add(ChatMessageDto message) {
        Long roomId = message.getChatRoom().getId();
        Set<String> pages = openRooms.get(roomId);

        for (String page : pages) {
            synchronized (messages.get(page)) {
                messages.get(page).add(message);
                messages.get(page).notifyAll();
            }
        }
    }

    public List<ChatMessageDto> get(Long roomId, String pageId) throws InterruptedException {
        openRooms.computeIfAbsent(roomId, k -> new HashSet<>());
        openRooms.get(roomId).add(pageId);

        if (!messages.containsKey(pageId)) {
            // добавляем эту страницу в Map-у страниц
            messages.put(pageId, new ArrayList<>());
        }
        synchronized (messages.get(pageId)) {
            // если нет сообщений уходим в ожидание
            if (messages.get(pageId).isEmpty()) {
                messages.get(pageId).wait();
            }
        }
        // если сообщения есть - то кладем их в новых список
        List<ChatMessageDto> response = new ArrayList<>(messages.get(pageId));
        // удаляем сообщения из мапы
        messages.get(pageId).clear();
        return response;
    }
}
