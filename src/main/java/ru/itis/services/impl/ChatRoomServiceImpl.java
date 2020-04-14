package ru.itis.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dao.interfaces.ChatRoomRepository;
import ru.itis.dto.ChatRoomDto;
import ru.itis.dto.UserDto;
import ru.itis.models.ChatRoom;
import ru.itis.models.Room;
import ru.itis.models.User;
import ru.itis.services.interfaces.ChatRoomsService;

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
        return rooms.stream().map(ChatRoom::toChatRoomDto).collect(Collectors.toList());
    }

    @Override
    public List<ChatRoomDto> getUsersRooms() {
        return null;
    }

    @Override
    public ChatRoomDto getRoom(Long id) {
        Optional<ChatRoom> optionalRoom = chatRoomRepository.getRoom(id);
        if (optionalRoom.isPresent()){
            ChatRoomDto chatRoomDto = ChatRoom.toChatRoomDto(optionalRoom.get());
            return chatRoomDto;
        }
        throw new IllegalStateException("room doesn't exist");
    }
}
