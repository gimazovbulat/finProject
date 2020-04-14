package ru.itis.services.impl;

import org.springframework.stereotype.Component;
import ru.itis.dto.ChatMessageDto;
import ru.itis.dto.ChatRoomDto;

import java.util.*;

@Component
public class MessagesComponent {
    private final Map<String, List<ChatMessageDto>> messages;
    private final Map<Long, Set<String>> openRooms;

    public MessagesComponent() {
        messages = new HashMap<>();
        openRooms = new HashMap<>();
    }

    public void add(ChatMessageDto message) throws InterruptedException {

        // полученное сообщение добавляем для всех открытых страниц нашего приложения
        for (List<ChatMessageDto> pageMessages : messages.values()) {
            // перед тем как положить сообщение для какой-либо страницы
            // мы список сообщений блокируем
            synchronized (pageMessages) {
                // добавляем сообщение
                pageMessages.add(message);
                // говорим, что другие потоки могут воспользоваться этим списком
                pageMessages.notifyAll();
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
        if (openRooms.get(roomId).contains(pageId)) {
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
        throw new IllegalStateException();
    }
}
