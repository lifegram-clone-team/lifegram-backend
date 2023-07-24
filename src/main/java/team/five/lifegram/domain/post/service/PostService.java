package team.five.lifegram.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.five.lifegram.domain.post.dto.DetailPostResponseDto;
import team.five.lifegram.domain.post.dto.PostRequestDto;
import team.five.lifegram.domain.post.dto.PostResponseDto;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;

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
        return posts.map(PostResponseDto::of);
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
        return DetailPostResponseDto.of(post);
    }

    @Transactional
    public void updatePost(Long postId, PostRequestDto postRequestDto, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("게시글이 존재하지 않습니다."));
        if (post.getUser().getId() != userId) {
            throw new IllegalArgumentException("이 게시글에 수정 권한이 없습니다.");
        }
        post.updateContent(postRequestDto.getContent());
    }
}