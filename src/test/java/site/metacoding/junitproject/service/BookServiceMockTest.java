package site.metacoding.junitproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.response.BookListResponseDto;
import site.metacoding.junitproject.web.dto.response.BookResponseDto;
import site.metacoding.junitproject.web.dto.request.BookSaveRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        String author = "author";
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
        assertThat(saveBookDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(saveBookDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

    @Test
    public void findAllTest() {
        // given

        // stub
        List<Book> books = new ArrayList<>();
        books.add(Book.builder().id(1L).title("title1").author("author1").build());
        books.add(Book.builder().id(2L).title("title2").author("author2").build());
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        // when
        BookListResponseDto bookListResponseDto = bookService.getBooks();

        // then
        assertThat(bookListResponseDto.getBooks().size()).isEqualTo(2);
        assertThat(bookListResponseDto.getBooks().get(0).getId()).isEqualTo(1L);
        assertThat(bookListResponseDto.getBooks().get(1).getId()).isEqualTo(2L);
    }

    @Test
    public void getBook() {
        // given
        Long id = 1L;

        // stub
        String title = "title";
        String author = "author";
        Book newBook = new Book(id, title, author);
        Optional<Book> book = Optional.of(newBook);

        Mockito.when(bookRepository.findById(Mockito.any())).thenReturn(book);

        // when
        BookResponseDto bookResponseDto = bookService.getBook(id);

        // then
        assertThat(bookResponseDto.getId()).isEqualTo(1L);
        assertThat(bookResponseDto.getTitle()).isEqualTo(newBook.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(newBook.getAuthor());
    }

    @Test
    public void update() {
        // given
        Long id = 1L;
        String updateTitle = "수정 title";
        String updateAuthor = "수정 author";
        BookSaveRequestDto dto = new BookSaveRequestDto(updateTitle, updateAuthor);

        // stub
        String originTitle = "기존 title";
        String originAuthor = "기존 author";
        Book newBook = new Book(id, originTitle, originAuthor);
        Optional<Book> book = Optional.of(newBook);
        Mockito.when(bookRepository.findById(Mockito.any())).thenReturn(book);

        // when
        BookResponseDto bookResponseDto = bookService.update(id, dto);

        // then
        assertThat(bookResponseDto.getId()).isEqualTo(1L);
        assertThat(bookResponseDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(dto.getAuthor());
    }
}
