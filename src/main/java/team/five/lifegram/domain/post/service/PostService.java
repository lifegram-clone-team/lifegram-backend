package team.five.lifegram.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.five.lifegram.domain.post.dto.DetailPostResponseDto;
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
    public Page<PostResponseDto> getPosts(int page, int size) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostResponseDto::new);
    }

    public void createPost(String content, String image, Long userId) {
        //이미지 업로드 과정


        String image_url = image;
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        Post post = new Post(image_url, content, user);
        postRepository.save(post);
    }

    public DetailPostResponseDto getDetailPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));
        return new DetailPostResponseDto(post);
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
