package site.metacoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.response.BookListResponseDto;
import site.metacoding.junitproject.web.dto.response.BookResponseDto;
import site.metacoding.junitproject.web.dto.request.BookSaveRequestDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository repository;
    private final MailSender mailSender;

    // 1. 책 등록
    // 런타임 예외 발생 시 자동 롤백이 되야하기 때문에
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResponseDto save(BookSaveRequestDto dto) {
        Book saveBook = repository.save(dto.toEntity());

        if (saveBook != null) {
            if (!mailSender.sendMail()) {
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }

        return saveBook.toDto();
    }

    // 2. 책 목록 조회
    @Transactional(readOnly = true)
    public BookListResponseDto getBooks() {

        List<BookResponseDto> dtos = repository.findAll().stream()
            .map(Book::toDto)
            .collect(Collectors.toList());

        return BookListResponseDto
            .builder()
            .books(dtos)
            .build();
    }

    // 2-1. 책 단건 조회
    @Transactional(readOnly = true)
    public BookResponseDto getBook(Long id) {
        Book findBook = repository.findById(id)
            .orElseThrow(NoSuchElementException::new);

        return findBook.toDto();
    }

    // 3. 책 삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(Long id) {
        Book findBook = repository.findById(id)
            .orElseThrow(NoSuchElementException::new);

        repository.delete(findBook);
    }

    // 4. 책 수정
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResponseDto update(Long id, BookSaveRequestDto dto) {
        // 최초 조회 시 Book 상태를 스냅샷에 저장
        Book findBook = repository.findById(id)
            .orElseThrow(NoSuchElementException::new);

        // 스냅샷에 저장한 Book 엔티티에 변화가 생겼기 때문에 더티체킹 발생
        // 트랜잭션 종료 시점에 변화가 있는 모든 엔티티 객체 (여기서는 Book) 를 찾아서 update
        findBook.update(dto.getTitle(), dto.getAuthor());

        return findBook.toDto();
    }
}
