package ru.itis.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dao.interfaces.ChatRoomRepository;
import ru.itis.dto.ChatRoomDto;
import ru.itis.dto.UserDto;
import ru.itis.models.ChatRoom;
import ru.itis.models.User;
import ru.itis.services.interfaces.ChatRoomsService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatRoomServiceImpl implements ChatRoomsService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Transactional
    @Override
    public List<ChatRoomDto> getTechSupportRoomsForUser(UserDto userDto) {
        List<ChatRoom> rooms = chatRoomRepository.getAllTechSupportRoomsForUser(User.fromUserDto(userDto));
        if (rooms.size() == 0) return Collections.emptyList();
        return rooms.stream().map(ChatRoom::toChatRoomDto).collect(Collectors.toList());
    }

    @Override
    public List<ChatRoomDto> getUsersRooms() {
        return null;
    }

    @Override
    public ChatRoomDto getRoom(Long id) {
        Optional<ChatRoom> optionalRoom = chatRoomRepository.findById(id);
        if (optionalRoom.isPresent()){
            ChatRoomDto chatRoomDto = ChatRoom.toChatRoomDto(optionalRoom.get());
            return chatRoomDto;
        }
        throw new IllegalStateException("room doesn't exist");
    }

    @Transactional
    @Override
    public void createRoom(ChatRoomDto chatRoomDto) {
        chatRoomRepository.save(ChatRoom.fromChatRoomDto(chatRoomDto));
    }

    @Override
    public List<ChatRoomDto> getTechSupportRoomsForAdmin() {
        List<ChatRoom> roomsForAdmin = chatRoomRepository.getAllTechSupportRoomsForAdmin();
        return roomsForAdmin.stream().map(ChatRoom::toChatRoomDto).collect(Collectors.toList());
    }
}
