package site.metacoding.junitproject.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.junitproject.domain.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// 기본 생성자로 역직렬화를 수행, 현재 생성자가 있으므로 @NoArgConstructor 필요
@NoArgsConstructor
@Getter
public class BookSaveRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 50)
    private String title;

    @NotBlank(message = "저자를 입력해주세요.")
    @Size(min = 2, max = 20)
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
