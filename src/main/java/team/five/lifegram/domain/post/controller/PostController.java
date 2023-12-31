package team.five.lifegram.domain.post.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.five.lifegram.domain.post.dto.DetailPostResponseDto;
import team.five.lifegram.domain.post.dto.PostRequestDto;
import team.five.lifegram.domain.post.dto.PostResponseDto;
import team.five.lifegram.domain.post.dto.UserProfilePostResponseDto;
import team.five.lifegram.domain.post.service.PostService;
import team.five.lifegram.global.Security.AuthPayload;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public Page<PostResponseDto> getPosts(@RequestParam("page") int page, @RequestParam("size") int size, @AuthenticationPrincipal AuthPayload authPayload) {
        Long userId = authPayload.userId();
        return postService.getPosts(page-1, size, userId);
    }

    @PostMapping("")
    public void createPost(@Valid @RequestPart(name = "post") PostRequestDto postRequestDto, @RequestPart MultipartFile image, @AuthenticationPrincipal AuthPayload authPayload) {
        Long userId = authPayload.userId();
        postService.createPost(postRequestDto, image, userId);
    }

    @GetMapping("/{postId}")
    public DetailPostResponseDto getDetailPost(@PathVariable Long postId, @AuthenticationPrincipal AuthPayload authPayload) {
        Long userId = authPayload.userId();
        return postService.getDetailPost(postId, userId);
    }

    @PutMapping("/{postId}")
    public void updatePost (@PathVariable Long postId, @Valid @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal AuthPayload authPayload) {
        Long userId = authPayload.userId();
        postService.updatePost(postId, postRequestDto, userId);
    }


    @GetMapping("user")
    public Page<UserProfilePostResponseDto> getUserProfilePost (@RequestParam("page") int page, @RequestParam("size") int size, @AuthenticationPrincipal AuthPayload authPayload) {
        Long userId = authPayload.userId();
        return postService.getUserProfilePost(page - 1, size, userId);
    }

    @DeleteMapping("/{postId}")
    public void deletePost (@PathVariable Long postId, @AuthenticationPrincipal AuthPayload authPayload) {
        Long userId = authPayload.userId();
        postService.deletePost(postId, userId);

    }
}