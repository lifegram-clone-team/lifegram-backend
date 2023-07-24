package team.five.lifegram.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.five.lifegram.domain.user.dto.UserProfileResponseDto;
import team.five.lifegram.domain.user.service.UserService;
import team.five.lifegram.global.Security.AuthPayload;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public UserProfileResponseDto getUserProfile(@AuthenticationPrincipal AuthPayload authPayload) {
        Long userId = authPayload.userId();
        return userService.getUserProfile(userId);
    }
}
