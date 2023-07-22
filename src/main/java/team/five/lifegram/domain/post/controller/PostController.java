package team.five.lifegram.domain.post.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.five.lifegram.domain.post.dto.DetailPostResponseDto;
import team.five.lifegram.domain.post.dto.PostRequestDto;
import team.five.lifegram.domain.post.dto.PostResponseDto;
import team.five.lifegram.domain.post.service.PostService;
import team.five.lifegram.global.Security.AuthPayload;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

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


    @PutMapping("/{postId}")
    public ResponseEntity<LocalDateTime> updatePost (@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        try {
            LocalDateTime updatedAt = postService.updatePost(postId, postRequestDto);
            return ResponseEntity.ok(updatedAt);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}