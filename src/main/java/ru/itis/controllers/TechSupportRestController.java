package ru.itis.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.ChatRoomDto;
import ru.itis.dto.UserDto;
import ru.itis.models.ChatRoomType;
import ru.itis.security.CurrentUser;
import ru.itis.services.interfaces.ChatRoomsService;
import ru.itis.services.interfaces.UsersService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TechSupportRestController {
    private final ChatRoomsService chatRoomsService;
    private final UsersService usersService;

    public TechSupportRestController(ChatRoomsService chatRoomsService,
                                     UsersService usersService) {
        this.chatRoomsService = chatRoomsService;
        this.usersService = usersService;
    }

    @PostMapping("/techSupport/room")
    public ResponseEntity createRoom(@CurrentUser UserDetails userDetails) {
        String email = userDetails.getUsername();
        UserDto user = usersService.findUser(email);

        List<UserDto> chatters = new ArrayList<>();
        chatters.add(user);

        int count = chatRoomsService.getTechSupportRoomsForUser(user).size();

        chatRoomsService.createRoom(ChatRoomDto.builder()
                .name("TechSuppRoom No" + count)
                .active(true)
                .chatters(chatters)
                .chatRoomType(ChatRoomType.TO_SUPPORT)
                .build());

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/api/techSupport/rooms")
    public ResponseEntity<List<ChatRoomDto>> getTechSupportRooms(@CurrentUser UserDetails userDetails) {
        String email = userDetails.getUsername();
        UserDto user = usersService.findUser(email);
        List<ChatRoomDto> rooms = chatRoomsService.getTechSupportRoomsForUser(user); //todo add pagination
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/api/admin/techSupport/rooms")
    public ResponseEntity<List<ChatRoomDto>> getTechSupportRoomsForAdmin() {
        List<ChatRoomDto> rooms = chatRoomsService.getTechSupportRoomsForAdmin(); //todo add pagination
        return ResponseEntity.ok(rooms);
    }
}
