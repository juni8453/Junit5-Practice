package site.metacoding.junitproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSenderStub;
import site.metacoding.junitproject.web.dto.response.BookResponseDto;
import site.metacoding.junitproject.web.dto.request.BookSaveRequestDto;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class BookServiceTest {

    @Autowired
    private BookRepository repository;

    // 문제점
    // 서비스만 테스트하고 싶지만, Repository 레이어가 함께 테스트 된다.
    // Repository 레이어는 이미 테스트가 종료됐는데 굳이 무겁게 같이 할 필요가 없다.

    // 해결책
    // 가짜 환경을 만들어서 가짜 Repository 를 만들어주자.
    // 그럼 진짜 Repository 를 메모리에 로딩할 필요가 없다.
    @Test
    public void save() {
        // given
        String title = "title";
        String author = "author";
        BookSaveRequestDto dto = BookSaveRequestDto.builder()
            .title(title)
            .author(author)
            .build();

        // stub
        MailSenderStub mailSenderStub = new MailSenderStub();

        // when
        BookService bookService = new BookService(repository, mailSenderStub);
        BookResponseDto saveBookDto = bookService.save(dto);

        // then (실제 값 / 예상 값)
        assertThat(saveBookDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(saveBookDto.getAuthor()).isEqualTo(dto.getAuthor());

    }
}
