package site.danjam.mate.user_service.global.common.minio.exception;

import site.danjam.mate.user_service.global.exception.BaseException;
import site.danjam.mate.user_service.global.exception.Code;

public class MinioException extends BaseException {

    public MinioException() {
        super(Code.INTERNAL_SEVER_MINIO_ERROR);
    }

    public MinioException(String message) {
        super(Code.INTERNAL_SEVER_MINIO_ERROR, message);
    }
}
