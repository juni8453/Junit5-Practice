package site.metacoding.junitproject.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.junitproject.domain.Book;

@NoArgsConstructor
@Getter
public class BookResponseDto {

    // 응답 시 DB 에 저장되었기 때문에 id 값 반환 가능
    private Long id;
    private String title;
    private String author;

    public BookResponseDto toDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();

        return this;
    }
}
