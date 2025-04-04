package app.handong.feed.controller;

import app.handong.feed.service.LabSSEService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequestMapping("/api/lab")
@RestController
@RequiredArgsConstructor
public class LabSSEController {
    private final LabSSEService notificationService;

    @GetMapping(value = "/sub", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter subscribe() {
        return notificationService.subscribe();
    }

    @PostMapping("/broadcast")
    public void broadcast(@RequestBody String message) {
        notificationService.broadcast(message, "Broadcast message");
    }
}
