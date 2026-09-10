package com.quanxiaoha.xiaolanshu.app;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class AppStore {
    private final AtomicLong userSequence = new AtomicLong(10000);
    private final AtomicLong noteSequence = new AtomicLong(100000);
    private final Map<String, User> usersByPhone = new ConcurrentHashMap<>();
    private final Map<String, Long> userIdByToken = new ConcurrentHashMap<>();
    private final Map<Long, Note> notes = new ConcurrentHashMap<>();
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    public String issueVerificationCode(String phone) {
        String code = "123456";
        verificationCodes.put(phone, code);
        return code;
    }

    public Login login(String phone, String code) {
        if (!"123456".equals(code) && !java.util.Objects.equals(code, verificationCodes.get(phone))) {
            throw new IllegalArgumentException("验证码错误，开发环境验证码为 123456");
        }
        User user = usersByPhone.computeIfAbsent(phone,
                key -> new User(userSequence.incrementAndGet(), key, "xiaolanshu用户" + userSequence.get()));
        String token = UUID.randomUUID().toString().replace("-", "");
        userIdByToken.put(token, user.id());
        return new Login(token, user);
    }

    public Long userId(String token) {
        return token == null ? null : userIdByToken.get(token.replace("Bearer ", ""));
    }

    public void logout(String token) {
        if (token != null) {
            userIdByToken.remove(token.replace("Bearer ", ""));
        }
    }

    public Note publish(Long creatorId, String title, String content, List<String> imageUris) {
        long id = noteSequence.incrementAndGet();
        Note note = new Note(id, creatorId, title, content == null ? "" : content,
                imageUris == null ? List.of() : List.copyOf(imageUris), LocalDateTime.now(), 0, 0);
        notes.put(id, note);
        return note;
    }

    public Note findNote(long id) {
        return notes.get(id);
    }

    public List<Note> listNotes() {
        List<Note> result = new ArrayList<>(notes.values());
        result.sort(Comparator.comparing(Note::createdAt).reversed());
        return result;
    }

    public Note like(long id) {
        return notes.computeIfPresent(id, (key, note) -> note.withLikeTotal(note.likeTotal() + 1));
    }

    public record User(long id, String phone, String nickname) {}

    public record Login(String token, User user) {}

    public record Note(long id, long creatorId, String title, String content, List<String> imageUris,
                       LocalDateTime createdAt, int likeTotal, int collectTotal) {
        Note withLikeTotal(int value) {
            return new Note(id, creatorId, title, content, imageUris, createdAt, value, collectTotal);
        }
    }
}

