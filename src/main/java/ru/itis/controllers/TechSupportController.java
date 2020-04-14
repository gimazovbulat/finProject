package ru.itis.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.dto.ChatRoomDto;
import ru.itis.services.interfaces.ChatRoomsService;

@Controller
public class TechSupportController {
    private final ChatRoomsService chatRoomsService;

    public TechSupportController(ChatRoomsService chatRoomsService) {
        this.chatRoomsService = chatRoomsService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("rooms/techSupport")
    public String getTechSupportRoomsPage() {
        return "techSupportRooms";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("rooms/techSupport/{id}")
    public String getTechSupportSingleRoomPage(@PathVariable("id") Long roomId, Model model) {
        ChatRoomDto room = chatRoomsService.getRoom(roomId);
        model.addAttribute("room", room);
        return "techSupportSingleRoom";
    }
}
