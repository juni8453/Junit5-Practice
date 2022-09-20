package site.metacoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.web.dto.BookResponseDto;
import site.metacoding.junitproject.web.dto.BookSaveRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository repository;

    // 1. 책 등록
    // 런타임 예외 발생 시 자동 롤백이 되야하기 때문에
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResponseDto save(BookSaveRequestDto dto) {
        Book saveBook = repository.save(dto.toEntity());
        return new BookResponseDto().toDto(saveBook);
    }

    // 2. 책 목록 조회
    public List<BookResponseDto> getBooks() {
        return repository.findAll().stream()
            .map(new BookResponseDto()::toDto)
            .collect(Collectors.toList());

    // 2-1. 책 단건 조회

    // 3. 책 삭제

    // 4. 책 수정

    }
}
