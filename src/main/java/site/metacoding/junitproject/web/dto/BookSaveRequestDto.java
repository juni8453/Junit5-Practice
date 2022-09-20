package site.metacoding.junitproject.web.dto;

import site.metacoding.junitproject.domain.Book;

// 기본 생성자로 역직렬화를 수행, 현재 아무런 생성자 없으므로 @NoArgConstructor 불필요
public class BookSaveRequestDto {
    private String title;
    private String author;

    public Book toEntity() {
        return Book.builder()
            .title(title)
            .author(author)
            .build();
    }
}
