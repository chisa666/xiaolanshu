package com.quanxiaoha.xiaolanshu.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/notes", "/note"})
public class NoteController {
    private final AppStore store;

    public NoteController(AppStore store) {
        this.store = store;
    }

    @GetMapping
    public Map<String, Object> list() {
        return Map.of("success", true, "data", store.listNotes());
    }

    @GetMapping("/{id}")
    public Object detail(@PathVariable long id) {
        AppStore.Note note = store.findNote(id);
        return note == null ? Map.of("success", false, "message", "笔记不存在")
                : Map.of("success", true, "data", note);
    }

    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.CREATED)
    public Object publish(@RequestHeader(value = "Authorization", required = false) String token,
                          @RequestBody PublishRequest request) {
        Long userId = store.userId(token);
        if (userId == null) {
            return Map.of("success", false, "message", "请先登录");
        }
        return Map.of("success", true, "data", store.publish(userId, request.title(), request.content(), request.imageUris()));
    }

    @PostMapping("/{id}/like")
    public Object like(@PathVariable long id) {
        AppStore.Note note = store.like(id);
        return note == null ? Map.of("success", false, "message", "笔记不存在")
                : Map.of("success", true, "data", note);
    }

    public record PublishRequest(String title, String content, List<String> imageUris) {}
}

