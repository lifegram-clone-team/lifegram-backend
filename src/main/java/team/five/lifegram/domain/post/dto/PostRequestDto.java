package team.five.lifegram.domain.post.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequestDto {
    @Size(max = 1024)
    private String content;
}
