package edu.dongnao.rental.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * 服务接口通用结果
 * @author Traveler
 * @date 2019/12/21 18:14
 */
@Data
public class ServiceResult<T> implements Serializable {

    private static final long serialVersionUID = 6500659621960354716L;

    public static final ServiceResult<Boolean> SUCCESS_RESULT = new ServiceResult<>(true, "操作成功", true);

    public static final ServiceResult<Boolean> FAIL_RESULT = new ServiceResult<>(false, "操作失败", false);

    private boolean success;

    private String message;

    private T result;

    public ServiceResult(boolean success) {
        this.success = success;
    }

    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ServiceResult(boolean success, String message, T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public static <T> ServiceResult<T> of(T result) {
        ServiceResult<T> serviceResult = new ServiceResult<>(true);
        serviceResult.setResult(result);
        return serviceResult;
    }
}
