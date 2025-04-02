package site.danjam.mate.common.exception.user_service;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class MinioException extends BaseException {
    public MinioException() {
        super(Code.USER_INTERNAL_SEVER_MINIO_ERROR);
    }

    public MinioException(String message) {
        super(Code.USER_INTERNAL_SEVER_MINIO_ERROR, message);
    }
}
