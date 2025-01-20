package site.danjam.mate.mate_service.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import site.danjam.mate.mate_service.enums.MateType;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.exception.BaseException;
import site.danjam.mate.mate_service.global.exception.Code;

public class DataConvert {
    @MethodDescription(description = "클라이언트로 입력 받은 Set을 String문자열로 변환하여 반환합니다. ")
    public static String setToString(Set<?> data){
        StringBuilder sb = new StringBuilder("[");
        for (Object element : data) {
            sb.append(element).append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    @MethodDescription(description = "클라이언트로 입력 받은 String mateType을 MateType으로 변환합니다.")
    public static MateType stringToMateType(String type){
        try{
            return MateType.valueOf(type.toUpperCase());
        }catch(IllegalArgumentException e){
            throw new BaseException(Code.INVALID_MATE_TYPE);
        }
    }

    @MethodDescription(description = "데이터베이스에 저장된 String을 Set형태로 변환한다. 클라이언트로부터 String 데이터를 Set형태로 전달할 때 사용")
    public static Set<String> stringToSet(String data) {

        // 입력이 비어있거나 빈 배열 형태이면 빈 Set 반환
        if (data == null || data.trim().equals("[]")) {
            return new HashSet<>();
        }

        // "["와 "]" 제거 후 데이터를 ','로 나누어 Set으로 변환
        String cleanedData = data.substring(1, data.length() - 1).trim(); // "["와 "]" 제거
        return new HashSet<>(Arrays.asList(cleanedData.split("\\s*,\\s*"))); //컴마를 기준으로 분리
    }
}
