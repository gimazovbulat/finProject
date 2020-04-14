package ru.itis.dao.interfaces;

import ru.itis.models.ChatMessage;
import ru.itis.models.ChatRoom;

import java.util.List;

public interface ChatMessagesRepository {
    List<ChatMessage> getTechSupportMessages(ChatRoom chatRoom);

    void save(ChatMessage chatMessage);
}
