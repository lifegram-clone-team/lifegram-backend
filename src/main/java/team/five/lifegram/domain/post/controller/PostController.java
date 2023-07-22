package team.five.lifegram.domain.post.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.five.lifegram.domain.post.dto.DetailPostResponseDto;
import team.five.lifegram.domain.post.dto.PostResponseDto;
import team.five.lifegram.domain.post.service.PostService;
import team.five.lifegram.global.Security.AuthPayload;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public Page<PostResponseDto> getPosts(@RequestParam("page") int page, @RequestParam("size") int size) {
        return postService.getPosts(page-1, size);
    }

    @PostMapping("")
    public void createPost(@RequestPart String content, @RequestPart String image, @AuthenticationPrincipal AuthPayload authPayload) {
        Long userId = authPayload.userId();
        postService.createPost(content, image, userId);
    }

    @GetMapping("{postId}")
    public DetailPostResponseDto getDetailPost(@PathVariable Long postId) {
        return postService.getDetailPost(postId);
    }
}
