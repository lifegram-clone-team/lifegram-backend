package team.five.lifegram.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.five.lifegram.domain.post.repository.PostRepository;
import team.five.lifegram.domain.user.dto.UserProfileResponseDto;
import team.five.lifegram.domain.user.entity.User;
import team.five.lifegram.domain.user.repository.UserRepository;
import team.five.lifegram.global.imageUpload.S3Upload;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final S3Upload s3Upload;

    @Transactional(readOnly = true)
    public UserProfileResponseDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        int postCount = postRepository.countByUser(user);
        UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(user.getUserName(), user.getImg_url(), postCount);
        return userProfileResponseDto;
    }

    @Transactional
    public void updateUserProfile(MultipartFile image, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        if(!image.isEmpty()) {
            try {
                String imagePath = s3Upload.uploadFiles(image, "images/profile");
                user.updateImgUrl(imagePath);
            } catch (Exception e){
                e.printStackTrace();
            }
        }else {
            throw new IllegalArgumentException("이미지 없이 프로필 사진을 수정할 수 없습니다.");
        }
    }
}
