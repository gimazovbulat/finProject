package ru.itis.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dao.interfaces.ChatMessagesRepository;
import ru.itis.dao.interfaces.ChatRoomRepository;
import ru.itis.dto.ChatMessageDto;
import ru.itis.models.ChatMessage;
import ru.itis.models.ChatRoom;
import ru.itis.services.interfaces.ChatMessagesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatMessageServiceImpl implements ChatMessagesService {
    private final ChatMessagesRepository chatMessagesRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessagesComponent messagesComponent;

    public ChatMessageServiceImpl(ChatMessagesRepository chatMessagesRepository,
                                  ChatRoomRepository chatRoomRepository,
                                  MessagesComponent messagesComponent) {
        this.chatMessagesRepository = chatMessagesRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.messagesComponent = messagesComponent;
    }

    @Override
    public List<ChatMessageDto> getTechSupportMessages(Long roomId) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.getRoom(roomId);
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            return chatMessagesRepository
                    .getTechSupportMessages(chatRoom)
                    .stream()
                    .map(ChatMessage::toChatMessageDto)
                    .collect(Collectors.toList());

        }
        throw new IllegalStateException("room doesn't exist");
    }

    @Transactional
    @Override
    public void receiveMessage(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = ChatMessage.fromChatMessageDto(chatMessageDto);
        chatMessagesRepository.save(chatMessage);
        try {
            messagesComponent.add(chatMessageDto);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}