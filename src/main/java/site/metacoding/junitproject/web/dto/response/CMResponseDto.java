package site.metacoding.junitproject.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CMResponseDto<T> {

    private Integer code; // 1 성공, -1 실패
    private String message; // 에러 Or 성공 메세지
    private T body; // 응답에 대한 Body

    @Builder
    public CMResponseDto(Integer code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }
}
