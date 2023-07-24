package team.five.lifegram.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequestDto {
    @NotBlank
    @Size(max = 2200)
    private String content;
}