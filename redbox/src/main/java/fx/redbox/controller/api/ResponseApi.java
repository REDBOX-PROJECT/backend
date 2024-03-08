package fx.redbox.controller.api;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ResponseApi {

    //상태코드, 응답메시지, 데이터
    private int statusCode;
    private String responseMessage;
    private Object data;

    public ResponseApi(final int statusCode, final String responseMessage, final Object data) {
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
    public static ResponseApi fail(final int statusCode, final String responseMessage) {
        return ResponseApi.builder()
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }

    public static ResponseApi success(final String responseMessage, final Object data) {
        return ResponseApi.builder()
                .data(data)
                .statusCode(StatusCode.OK) // success 는 기본으로 StatusCode.OK 를 가진다.
                .responseMessage(responseMessage)
                .build();
    }

    public static ResponseApi success(final String responseMessage) {
        return ResponseApi.builder()
                .statusCode(StatusCode.OK) // success 는 기본으로 StatusCode.OK 를 가진다.
                .responseMessage(responseMessage)
                .build();
    }
}

