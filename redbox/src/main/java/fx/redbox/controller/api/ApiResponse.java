package fx.redbox.controller.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ApiResponse<T> {

    //상태코드, 응답메시지, 데이터
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

    public static<T> ApiResponse<T> res(final int statusCode, final String responseMessage, final T t) {
        return ApiResponse.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }
}
