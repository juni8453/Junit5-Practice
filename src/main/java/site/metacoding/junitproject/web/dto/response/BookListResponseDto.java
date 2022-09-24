package site.metacoding.junitproject.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookListResponseDto {
    List<BookResponseDto> books;

    @Builder
    public BookListResponseDto(List<BookResponseDto> books) {
        this.books = books;
    }
}
