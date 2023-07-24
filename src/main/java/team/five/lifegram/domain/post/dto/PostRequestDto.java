package team.five.lifegram.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequestDto {
    @NotBlank(message = "내용을 입력해 주세요.")
    @Size(max = 1024, message = "내용은 최대 1024자까지 입력 가능합니다.")
    private String content;
}