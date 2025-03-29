package site.danjam.mate.common.exception;

public class MinioException extends BaseException {
    public MinioException() {
        super(Code.USER_INTERNAL_SEVER_MINIO_ERROR);
    }

    public MinioException(String message) {
        super(Code.USER_INTERNAL_SEVER_MINIO_ERROR, message);
    }
}
