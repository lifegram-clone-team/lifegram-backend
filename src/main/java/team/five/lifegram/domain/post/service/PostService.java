package team.five.lifegram.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.five.lifegram.domain.like.repository.LikeRepository;
import team.five.lifegram.domain.post.dto.DetailPostResponseDto;
import team.five.lifegram.domain.post.dto.PostRequestDto;
import team.five.lifegram.domain.post.dto.PostResponseDto;
import team.five.lifegram.domain.post.dto.UserProfilePostResponseDto;
import team.five.lifegram.domain.post.entity.Post;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;
import team.five.lifegram.global.Security.AuthPayload;
import team.five.lifegram.global.imageUpload.S3Upload;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final S3Upload s3Upload;

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPosts(int page, int size, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map((post)->PostResponseDto.of(post, likeRepository.existsByUserIdAndPostId(user.getId(), post.getId())));
    }

    public void createPost(PostRequestDto postRequestDto, MultipartFile image, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        if(!image.isEmpty()){
            try{
                String imagePath = s3Upload.uploadFiles(image, "images/post");
                Post post = new Post(imagePath, postRequestDto.getContent(), user);
                postRepository.save(post);
            } catch (Exception e){
                e.printStackTrace();
            }
        }else {
            throw new IllegalArgumentException("이미지 없이 게시글을 생성할 수 없습니다");
        }
    }

    @Transactional(readOnly = true)
    public DetailPostResponseDto getDetailPost(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return DetailPostResponseDto.of(post, likeRepository.existsByUserIdAndPostId(user.getId(), post.getId()));
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


    public Page<UserProfilePostResponseDto> getUserProfilePost(int page, int size, Long userId) {
        userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> posts = postRepository.findByUserId(userId, pageable);
        return posts.map(UserProfilePostResponseDto::new);

    public void deletePost(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if(!post.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("이 게시글에 삭제 권한이 없습니다.");
        }
        postRepository.delete(post);

    }
}