package site.metacoding.junitproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.BookResponseDto;
import site.metacoding.junitproject.web.dto.BookSaveRequestDto;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceMockTest {

    /*
        @InjectMocks 를 통해 @Mock 이 붙은 Mock 객체를 @InjectMocks 이 붙은 객체에 주입시킬 수 있다.
        현재 BookService 객체가 Repository, MailSender 을 주입받고 있기 떄문에 아래처럼 사용하면 된다.
     */
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    public void save() {
        // given
        String title = "title";
        String author = "title";
        BookSaveRequestDto dto = BookSaveRequestDto.builder()
            .title(title)
            .author(author)
            .build();

        // stub
        // when(실제 메소드 실행).thenReturn(호출 후 반환되는 값 미리 정의)
        // 실제 메소드 실행 부분의 인자값으로는 아무거나 들어간다는 뜻으로 any() 사용
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(dto.toEntity());
        Mockito.when(mailSender.sendMail()).thenReturn(true);

        // when
        BookResponseDto saveBookDto = bookService.save(dto);

        // then
        assertThat(saveBookDto.getTitle()).isEqualTo(title);
        assertThat(saveBookDto.getAuthor()).isEqualTo(author);
    }
}
