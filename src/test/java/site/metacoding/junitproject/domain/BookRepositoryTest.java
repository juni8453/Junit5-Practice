package site.metacoding.junitproject.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩한다. (단위 테스트)
public class  BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    // @BeforeEach 는 다음 테스트 메서드 까지만 하나의 트랜잭션으로 본다. (전체 X)
    @BeforeEach
    public void initGivenData() {
        String title = "새로운 책의 Title";
        String author = "새로운 책의 Author";

        Book newBook = Book.builder()
            .title(title)
            .author(author)
            .build();
        repository.save(newBook);
    }

    // 1. 책 등록 테스트
    @Test
    @DisplayName("책을 하나 등록합니다.")
    public void saveTest() {

        // given (데이터 준비)
        Book newBook = Book.builder()
            .title("새로운 책의 Title")
            .author("새로운 책의 author")
            .build();

        // when (테스트 실행)
        Book saveBook = repository.save(newBook);

        // then (검증, 실제 값 / 기대 값)
        assertThat(saveBook.getTitle()).isEqualTo(newBook.getTitle());
        assertThat(saveBook.getAuthor()).isEqualTo(newBook.getAuthor());
    }

    // 2. 책 조회 테스트
    @Test
    @DisplayName("책 목록을 조회합니다.")
    public void findAllTest() {
        String title = "새로운 책의 Title";
        String author = "새로운 책의 Author";

        // when
        List<Book> findBooks = repository.findAll();

        // then
        assertThat(findBooks.get(0).getTitle()).isEqualTo(title);
        assertThat(findBooks.get(0).getAuthor()).isEqualTo(author);

    }

    // 2-1. 책 단건 조회 테스트
    // PK 값을 가지는 Test 에는 멱등성 보장을 위해 새로운 테이블 자체를 만드는 것이 좋다.
    @Test
    @Sql("classpath:db/tableInit.sql")
    @DisplayName("책 목록 중 한 건을 조회합니다.")
    public void findDetailTest() {
        String title = "새로운 책의 Title";
        String author = "새로운 책의 Author";

        // when
        Book findBook = repository.findById(1L).get();

        // then
        assertThat(findBook.getTitle()).isEqualTo(title);
        assertThat(findBook.getAuthor()).isEqualTo(author);

    }

    // 3. 책 삭제 테스트
    // PK 값을 가지는 Test 에는 멱등성 보장을 위해 새로운 테이블 자체를 만드는 것이 좋다.
    @Test
    @Sql("classpath:db/tableInit.sql")
    @DisplayName("해당 책의 PK 값이 주어졌을 때 해당 책이 삭제됩니다.")
    public void deleteTest() {
        // given
        Long id = 1L;

        // when
        repository.deleteById(id);

        // then
        // isPresent() -> False -> 값이 없다 -> 지워진 값이 없어야하기 때문에 성공
        assertFalse(repository.findById(id).isPresent());

    }

    // 4. 책 수정 테스트
}
