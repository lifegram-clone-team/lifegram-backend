package team.five.lifegram.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.five.lifegram.domain.comment.entity.Comment;
import team.five.lifegram.domain.comment.repository.CommentRepository;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void writeComment(Long postId, String content, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("없는 게시물입니다.")
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("없는 사용자입니다.")
        );

        Comment comment = Comment.commentOf(post, content ,user);

        commentRepository.save(comment);
    }

    //이규행 : 코멘트 => user 연결 후 유저확인도 할 예정
    public void deleteComment(Long postId, Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글입니다.")
        );

        if(comment.getPost().getId() != postId){
            throw new IllegalArgumentException("해당 게시글에 댓글이 아닙니다.");
        }

        if(comment.getUser().getId() != userId){
            throw new IllegalArgumentException("내가 작성한 댓글이 아닙니다.");
        }
        commentRepository.delete(comment);
    }





}
