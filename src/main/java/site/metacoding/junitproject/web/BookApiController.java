package site.metacoding.junitproject.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.response.BookListResponseDto;
import site.metacoding.junitproject.web.dto.response.BookResponseDto;
import site.metacoding.junitproject.web.dto.request.BookSaveRequestDto;
import site.metacoding.junitproject.web.dto.response.CMResponseDto;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class BookApiController {

    private final BookService bookService;

    // 1. 책 등록
    // 상태코드 뿐만 아니라 필요하다면 Client 로 Body 또한 넘겨주는 것이 좋다.
    // 와일드카드를 사용해 커스텀 응답을 보낼 수 있다.
    @PostMapping("/book")
    public ResponseEntity<?> saveBook(@Valid @RequestBody BookSaveRequestDto dto) {
        BookResponseDto saveBook = bookService.save(dto);
        CMResponseDto<?> cmResponseDto = CMResponseDto.builder()
            .code(1)
            .message("책 저장 성공")
            .body(saveBook)
            .build();

        return new ResponseEntity<>(cmResponseDto, HttpStatus.CREATED);
    }

    // 2. 책 목록 조회
    @GetMapping("/book")
    public ResponseEntity<?> getBooks() {
        BookListResponseDto findBooks = bookService.getBooks();
        CMResponseDto<?> cmResponseDto = CMResponseDto.builder()
            .code(1)
            .message("책 목록조회 성공")
            .body(findBooks)
            .build();

        return new ResponseEntity<>(cmResponseDto, HttpStatus.OK);
    }

    // 2-1. 책 단건 조회
//    @GetMapping
    public ResponseEntity<BookResponseDto> getBook() {
        return null;
    }

    // 3. 책 삭제
//    @DeleteMapping
    public void deleteBook() {

    }

    // 4. 책 수정
//    @PostMapping
    public void updateBook() {

    }
}
