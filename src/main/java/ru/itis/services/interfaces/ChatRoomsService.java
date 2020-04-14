package ru.itis.services.interfaces;

import ru.itis.dto.ChatRoomDto;
import ru.itis.dto.UserDto;

import java.util.List;

public interface ChatRoomsService {
    List<ChatRoomDto> getTechSupportRoomsForUser(UserDto userDto);
    List<ChatRoomDto> getUsersRooms();
    ChatRoomDto getRoom(Long id);
}
