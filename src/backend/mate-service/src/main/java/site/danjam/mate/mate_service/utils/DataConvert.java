package site.danjam.mate.mate_service.utils;

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
}
