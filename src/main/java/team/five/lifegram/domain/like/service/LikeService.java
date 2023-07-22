package team.five.lifegram.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.five.lifegram.domain.like.entity.Like;
import team.five.lifegram.domain.like.repository.LikeRepository;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void likePost(Long postId, Long userId) {
        Optional<Like> like = likeRepository.findByUserIdAndPostId(userId, postId);

        if(like.isPresent()){
            likeRepository.delete(like.get());
        }else {
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new IllegalArgumentException("없는 게시글입니다.")
            );
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new IllegalArgumentException("없는 사용자입니다.")
            );
            likeRepository.save(Like.likeOf(user, post));
        }

    }
}
