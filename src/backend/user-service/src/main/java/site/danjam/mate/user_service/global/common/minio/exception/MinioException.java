package site.danjam.mate.user_service.global.common.minio.exception;

import static site.danjam.mate.user_service.global.exception.Code.INTERNAL_SEVER_MINIO_ERROR;

import site.danjam.mate.user_service.global.exception.BaseException;

public class MinioException extends BaseException {

    public MinioException() {
        super(INTERNAL_SEVER_MINIO_ERROR);
    }

    public MinioException(String message) {
        super(INTERNAL_SEVER_MINIO_ERROR, message);
    }
}
