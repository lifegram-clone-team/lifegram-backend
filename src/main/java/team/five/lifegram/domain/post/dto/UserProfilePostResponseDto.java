package team.five.lifegram.domain.post.dto;

import lombok.Getter;
import team.five.lifegram.domain.post.entity.Post;

import static team.five.lifegram.global.util.HttpUtils.parseS3Url;

//TODO DTO는 중복되는 코드를 줄이고 불변 객체로 사용하기 위해 record 데이터 유형을 사용한다.
//TODO 데이터의 변환은 팩토리 메서드로 처리를 하시죠. (생성자로 하는 방식은 X)
@Getter
public class UserProfilePostResponseDto {
    private Long postId;
    private String profileImgUrl;

    public UserProfilePostResponseDto(Post post) {
        this.postId = post.getId();
        this.profileImgUrl = parseS3Url("images/post",post.getImage_url());
    }
}
