package site.danjam.mate.mate_service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;

public class ValidationUtil {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MethodDescription(description = "메이트 프로필 생성시 inputDTO의 validation을 확인하는 메서드")
    public static <T> String validateInput(Object inputDTO, Class<T> dtoType) {
        if (inputDTO == null) {
            return "입력 프로필 값이 존재하지 않습니다.";
        }

        try {
            // ObjectMapper를 사용하여 입력 데이터를 지정된 DTO 타입으로 변환
            T dto = objectMapper.convertValue(inputDTO, dtoType);

            // 유효성 검사 수행
            Set<ConstraintViolation<T>> violations = validator.validate(dto);

            // 검증 실패 시 메시지 생성
            if (!violations.isEmpty()) {
                return violations.stream()
                        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                        .collect(Collectors.joining("\n"));
            }

            // 검증 성공
            return null;

        } catch (IllegalArgumentException e) {
            // ObjectMapper 변환 실패 메시지 처리
            String fullMessage = e.getMessage();
            String keyMessage = fullMessage.split(" \\(")[0]; // 첫 번째 괄호 이전까지 추출
            return "유효하지 않은 입력 데이터입니다. " + keyMessage + " - 기대 타입: " + dtoType.getSimpleName();
        }
    }
}
