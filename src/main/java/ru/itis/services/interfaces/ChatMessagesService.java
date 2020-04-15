package ru.itis.services.interfaces;

import ru.itis.dto.ChatMessageDto;

import java.util.List;

public interface ChatMessagesService {
    List<ChatMessageDto> getTechSupportMessages(Long roomId);

    void receiveMessage(ChatMessageDto chatMessageDto);
}
