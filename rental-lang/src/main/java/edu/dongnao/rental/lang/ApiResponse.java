package edu.dongnao.rental.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Traveler
 * @date 2019/12/21 18:28
 */
@Data
public class ApiResponse implements Serializable {

    private static final long serialVersionUID = 3831892783501379013L;

    private int code;

    private String message;

    private Object data;

    private boolean more;

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse ofSuccess(Object data) {
        return new ApiResponse(Status.SUCCESS.code, Status.SUCCESS.message, data);
    }

    public static ApiResponse ofMessage(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    public enum Status {
        /**
         * 成功
         */
        SUCCESS(200, "OK"),
        /**
         * Bad Request
         */
        BAD_REQUEST(400, "Bad Request"),
        /**
         * Not Found
         */
        NOT_FOUND(404, "Not Found"),
        /**
         * Unknown Internal Error
         */
        INTERNAL_SERVER_ERROR(500, "Unknown Internal Error"),
        /**
         *无效参数
         */
        NOT_VALID_PARAM(40005, "Not valid Params"),
        /**
         * Operation not supported
         */
        NOT_SUPPORTED_OPERATION(40006, "Operation not supported"),
        /**
         * 未授权
         */
        NOT_LOGIN(50000, "Not Login");

        private int code;

        private String message;

        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
