package team.five.lifegram.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.five.lifegram.domain.user.dto.FollowingRequestDto;
import team.five.lifegram.domain.user.dto.FollowingResponseDto;
import team.five.lifegram.domain.user.dto.UserProfileResponseDto;
import team.five.lifegram.domain.user.dto.UserProfileSearchResponseDto;
import team.five.lifegram.domain.user.service.UserService;
import team.five.lifegram.global.Security.AuthPayload;

import java.util.List;

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

    @PutMapping("")
    public void updateUserProfile(@RequestPart(name = "image") MultipartFile image, @AuthenticationPrincipal AuthPayload authPayload) {
        Long userId = authPayload.userId();
        userService.updateUserProfile(image, userId);
    }

    @GetMapping("/search")
    public List<UserProfileSearchResponseDto> findUser(@RequestParam String qName, @AuthenticationPrincipal AuthPayload authPayload) {
        Long userId = authPayload.userId();
        return userService.findUser(qName, userId);
    }

    @PostMapping("/following")
    public void followUser(@RequestBody FollowingRequestDto followingRequestDto, @AuthenticationPrincipal AuthPayload authPayload) {
        Long fromUserId = authPayload.userId();
        Long toUserId = followingRequestDto.getUserId();
        userService.followUser(fromUserId, toUserId);
    }

    @GetMapping("/following")
    public List<FollowingResponseDto> findFollowingUser(@AuthenticationPrincipal AuthPayload authPayload) {
        Long userId = authPayload.userId();
        return userService.findFollowingUser(userId);
    }
}
