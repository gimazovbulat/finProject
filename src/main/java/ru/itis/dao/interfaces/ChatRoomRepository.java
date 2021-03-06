package ru.itis.dao.interfaces;

import ru.itis.models.ChatRoom;
import ru.itis.models.User;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {
    List<ChatRoom> getAllTechSupportRoomsForUser(User user);
    List<ChatRoom> getAllTechSupportRoomsForAdmin();
    Optional<ChatRoom> findById(Long id);
    void save(ChatRoom chatRoom);
}
