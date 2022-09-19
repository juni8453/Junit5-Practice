package site.metacoding.junitproject.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩한다. (단위 테스트)
public class  BookRepositoryTest {

    @Autowired
    private BookRepository repository;

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

    // 2-1. 책 단건 조회 테스트

    // 3. 책 수정 테스트

    // 4. 책 삭제 테스트
}
