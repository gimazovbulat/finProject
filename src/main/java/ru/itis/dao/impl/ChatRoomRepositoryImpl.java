package ru.itis.dao.impl;

import org.springframework.stereotype.Repository;
import ru.itis.dao.interfaces.ChatRoomRepository;
import ru.itis.models.ChatRoom;
import ru.itis.models.ChatRoomType;
import ru.itis.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ChatRoomRepositoryImpl implements ChatRoomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ChatRoom> getAllTechSupportRoomsForUser(User user) {
        return entityManager
                .createQuery("select chatRoom from ChatRoom chatRoom join chatRoom.chatters chatter where chatRoom.chatRoomType = ?1 and chatter = ?2")
                .setParameter(1, ChatRoomType.TO_SUPPORT)
                .setParameter(2, user)
                .getResultList();
    }

    @Override
    public Optional<ChatRoom> getRoom(Long id) {
        ChatRoom chatRoom = (ChatRoom) entityManager.createQuery("select chatRoom from ChatRoom chatRoom where chatRoom.id = ?1")
                .setParameter(1, id)
                .getSingleResult();
        return Optional.of(chatRoom);
    }
}
