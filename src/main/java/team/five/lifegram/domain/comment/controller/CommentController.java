package team.five.lifegram.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.five.lifegram.domain.comment.dto.CommentRequestDto;
import team.five.lifegram.domain.comment.service.CommentService;
import team.five.lifegram.global.Security.AuthPayload;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/post/{postId}/comment")
    public void writeComment(@PathVariable Long postId, @Valid  @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal AuthPayload authPayload){
        commentService.writeComment(postId, commentRequestDto.getContent(), authPayload.userId());
    }

}
