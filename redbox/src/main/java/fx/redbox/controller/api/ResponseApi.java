package fx.redbox.controller.api;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ResponseApi<T> {

    //상태코드, 응답메시지, 데이터
    private int statusCode;
    private String responseMessage;
    private T data;

    public ResponseApi(final int statusCode, final String responseMessage, final T data) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    /*
    '짐작한대로 작동한다면 잘 작성된 코드이다.',
    메서드는 한 추상화 단계만 진행할 것
    add -> 더하다 , 추가하다.
    add(){
        // 데이터 초기화 2
         // 데이터 검증 2
        // 더하기 추상화 1단계
        // 반환
    }
     */
    public static<T> ResponseApi<T> fail(final int statusCode, final String responseMessage) {
        return ResponseApi.<T>builder()
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }

    public static<T> ResponseApi<T> success(final String responseMessage, final T data) {
        return ResponseApi.<T>builder()
                .data(data)
                .statusCode(StatusCode.OK) // success 는 기본으로 StatusCode.OK 를 가진다.
                .responseMessage(responseMessage)
                .build();
    }

    public static<T> ResponseApi<T> success(final String responseMessage) {
        return ResponseApi.<T>builder()
                .statusCode(StatusCode.OK) // success 는 기본으로 StatusCode.OK 를 가진다.
                .responseMessage(responseMessage)
                .build();
    }
}


/*
package fx.redbox.controller.api;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiResponse<T> {

    private int statusCode;
    private String responseMessage;
    private T data;

    public ApiResponse(int statusCode, String responseMessage, T data) {

        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public static<T> ApiResponse res(final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }

    public static<T> ApiResponse resOk(final String responseMessage) {
        return res(HttpStatus.OK.value(), responseMessage, null);
    }


    public static<T> ApiResponse<T> res(final int statusCode, final String responseMessage, final T t) {
        return ApiResponse.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }

    public static<T> ApiResponse<T> resOK(final String responseMessage, final T t) {
        return ApiResponse.<T>builder()
                .data(t)
                .statusCode(HttpStatus.OK.value())
                .responseMessage(responseMessage)
                .build();
    }
}
*/