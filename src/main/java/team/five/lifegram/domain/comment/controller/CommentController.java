package team.five.lifegram.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.five.lifegram.domain.comment.dto.CommentRequestDto;
import team.five.lifegram.domain.comment.service.CommentService;
import team.five.lifegram.global.Security.AuthPayload;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    //TODO 한 줄에 100자 넘어가지 않도록 한다.
    //TODO 간격은 공백 1개로 한다.
    //TODO 코드간의 줄 간격은 1칸으로 한다.
    @PostMapping("/{postId}/comment")
    public void writeComment(@PathVariable Long postId, @Valid  @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal AuthPayload authPayload){
        commentService.writeComment(postId, commentRequestDto.getContent(), authPayload.userId());
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal AuthPayload authPayload){
        commentService.deleteComment(postId, commentId, authPayload.userId());
    }


}
