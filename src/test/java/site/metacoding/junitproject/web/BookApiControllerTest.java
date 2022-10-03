package site.metacoding.junitproject.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.web.dto.request.BookSaveRequestDto;

import static org.assertj.core.api.Assertions.*;

// 통합 테스트 (컨트롤러만 테스트하는게 아닌 모든 레이어를 테스트하는 것)
// 단위 테스트를 원한다면 Mock 사용하면 됨.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    // @BeforeAll 을 통해 모든 테스트 메소드에서 사용되는 객체를 테스트 클래스 한 번에 초기화 하기 위해 static 선언
    // @BeforeAll 은 static 선언이 되어있는 객체만 정의할 수 있다.
    private static ObjectMapper objectMapper;
    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        objectMapper = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach
    public void initData() {
        String title = "title" ;
        String author = "author" ;
        Book book = Book.builder()
            .title(title)
            .author(author)
            .build();

        bookRepository.save(book);
    }

    // 1. 책 저장 테스트
    @Test
    public void saveTest() throws JsonProcessingException {
        // given
        String title = "저장 Title";
        String author = "저장 Author";

        BookSaveRequestDto dto = BookSaveRequestDto.builder()
            .title(title)
            .author(author)
            .build();

        String body = objectMapper.writeValueAsString(dto);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange("/api/v1/book", HttpMethod.POST, request, String.class);

        // then
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String bodyTitle = documentContext.read("$.body.title");
        String bodyAuthor = documentContext.read("$.body.author");

        assertThat(bodyTitle).isEqualTo(dto.getTitle());
        assertThat(bodyAuthor).isEqualTo(dto.getAuthor());
    }

    // 2. 책 목록보기 테스트
    @Test
    public void readBooksTest() {
        // given

        // when
        // GET 이기 떄문에 Body 없음 -> null
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange("/api/v1/book", HttpMethod.GET, request, String.class);

        // then
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int code = documentContext.read("$.code");
        String title = documentContext.read("$.body.books[0].title");
        String author = documentContext.read("$.body.books[0].author");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("title");
        assertThat(author).isEqualTo("author");
    }
}
