package site.metacoding.junitproject.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.junitproject.domain.Book;

// 기본 생성자로 역직렬화를 수행, 현재 생성자가 있으므로 @NoArgConstructor 필요
@NoArgsConstructor
@Getter
public class BookSaveRequestDto {
    private String title;
    private String author;

    @Builder
    public BookSaveRequestDto(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book toEntity() {
        return Book.builder()
            .title(title)
            .author(author)
            .build();
    }
}
