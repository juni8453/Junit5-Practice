package site.metacoding.junitproject.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookResponseDto {

    // 응답 시 DB 에 저장되었기 때문에 id 값 반환 가능
    private Long id;
    private String title;
    private String author;

    @Builder
    public BookResponseDto(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
