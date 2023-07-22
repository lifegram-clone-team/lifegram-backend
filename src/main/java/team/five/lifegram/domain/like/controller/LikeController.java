package team.five.lifegram.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.five.lifegram.domain.like.service.LikeService;
import team.five.lifegram.global.Security.AuthPayload;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}/like")
    public void like(@PathVariable Long postId,  @AuthenticationPrincipal AuthPayload authPayload){
        likeService.likePost(postId, authPayload.userId());
    }

}
