package ru.itis.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.dto.ChatRoomDto;
import ru.itis.services.interfaces.ChatRoomsService;

import java.util.UUID;

@Controller
@Profile("mvc")
public class TechSupportController {
    private final ChatRoomsService chatRoomsService;

    public TechSupportController(ChatRoomsService chatRoomsService) {
        this.chatRoomsService = chatRoomsService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/techSupport/rooms/")
    public String getTechSupportRoomsPage() {
        return "techSupportRooms";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/techSupport/rooms/{id}")
    public String getTechSupportSingleRoomPage(@PathVariable("id") Long roomId, Model model) {
        ChatRoomDto room = chatRoomsService.getRoom(roomId);
        model.addAttribute("room", room);
        model.addAttribute("pageId", UUID.randomUUID());
        return "techSupportSingleRoom";
    }

    @GetMapping("/admin/techSupport/rooms/")
    public String getTechSupportRoomsPageForAdmin() {
        return "adminTechSupportRooms";
    }
}
