package team.five.lifegram.domain.comment.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    //TODO 클래스 시작 코드는 클래스 명과 1 칸 줄간격을 가져야 한다.
    //TODO 끝나는 괄호는 마지막 코드 바로 아래 칸에 작성을 한다.
    //TODO DTO에 사용하는 validation은 jakarta.validation.constraints에 있는 것을 사용한다.
    @Column(nullable = false)
    String content;
}
