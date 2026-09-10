package com.quanxiaoha.xiaolanshu.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping({"/api/auth", "/user"})
public class AuthController {
    private final AppStore store;

    public AuthController(AppStore store) {
        this.store = store;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of("success", true, "service", "xiaolanshu", "author", "chisa");
    }

    @PostMapping("/send-code")
    public Map<String, Object> sendCode(@RequestBody SendCodeRequest request) {
        String code = store.issueVerificationCode(request.phone());
        return Map.of("success", true, "message", "验证码已发送", "data", Map.of("code", code));
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        try {
            AppStore.Login login = store.login(request.phone(), request.code());
            return Map.of("success", true, "data", login);
        } catch (IllegalArgumentException ex) {
            return Map.of("success", false, "message", ex.getMessage());
        }
    }

    @GetMapping("/me")
    public Object me(@RequestHeader(value = "Authorization", required = false) String token) {
        Long userId = store.userId(token);
        return userId == null ? Map.of("success", false, "message", "未登录")
                : Map.of("success", true, "data", Map.of("userId", userId));
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader(value = "Authorization", required = false) String token) {
        store.logout(token);
    }

    public record SendCodeRequest(String phone) {}

    public record LoginRequest(String phone, String code, Integer type) {}
}

