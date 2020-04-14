package ru.itis.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.ChatRoomDto;
import ru.itis.dto.UserDto;
import ru.itis.security.CurrentUser;
import ru.itis.services.interfaces.ChatRoomsService;
import ru.itis.services.interfaces.UsersService;

import java.util.List;

@RestController
public class RoomsController {
    private final ChatRoomsService chatRoomsService;
    private final UsersService usersService;

    public RoomsController(ChatRoomsService chatRoomsService, UsersService usersService) {
        this.chatRoomsService = chatRoomsService;
        this.usersService = usersService;
    }

    @GetMapping(path = "/rooms")
    public ResponseEntity<List<ChatRoomDto>> getTechSupportRooms(@CurrentUser UserDetails userDetails) {
        String email = userDetails.getUsername();
        UserDto user = usersService.findUser(email);
        List<ChatRoomDto> rooms = chatRoomsService.getTechSupportRoomsForUser(user); //todo add pagination
        return ResponseEntity.ok(rooms);
    }
}
