package team.five.lifegram.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.five.lifegram.domain.comment.dto.CommentResponseDto;
import team.five.lifegram.domain.post.dto.DetailPostResponseDto;
import team.five.lifegram.domain.post.dto.PostRequestDto;
import org.springframework.transaction.annotation.Transactional;
import team.five.lifegram.domain.post.dto.PostRequestDto;
import team.five.lifegram.domain.post.dto.PostResponseDto;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPosts(int page, int size) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map((post)->PostResponseDto.builder()
                .postId(post.getId())
                .postImgUrl(post.getImage_url())
                .content(post.getContent())
                .likeCount(1L)
                .isLike(false)
                .commentCount(Long.valueOf(post.getComments().size()))
                .writer(post.getUser().getUserName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build());
    }

    public void createPost(PostRequestDto postRequestDto, MultipartFile image, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        String image_url = S3Upload(image);
        Post post = new Post(image_url, postRequestDto.getContent(), user);
        postRepository.save(post);
    }

    public String S3Upload(MultipartFile image) {
        System.out.println("이미지 업로드");
        return image.getOriginalFilename();
    }

    @Transactional(readOnly = true)
    public DetailPostResponseDto getDetailPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));
        return DetailPostResponseDto.builder()
                .postId(post.getId())
                .postImgUrl(post.getImage_url())
                .content(post.getContent())
                .likeCount(1L)
                .isLike(false)
                .commentCount(Long.valueOf(post.getComments().size()))
                .writer(post.getUser().getUserName())
                .writerImgUrl(post.getUser().getImg_url())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .comments(post.getComments().stream().map(CommentResponseDto::new).toList())
                .build();
    }

    @Transactional
    public LocalDateTime updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("게시글이 존재하지 않습니다."));
        String newContent = postRequestDto.getContent();
        if (newContent.length() > 1024) {
            throw new IllegalArgumentException("글자 수가 1024자 이하여야 합니다.");
        }
        if (newContent.isEmpty()) {
            throw new IllegalArgumentException("수정할 내용을 넣어주세요.");
        }
        post.update(postRequestDto.getContent());
        return post.getUpdatedAt();
    }
}
