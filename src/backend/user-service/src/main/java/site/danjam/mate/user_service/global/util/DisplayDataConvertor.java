package site.danjam.mate.user_service.global.util;

public class DisplayDataConvertor {
    public static String displayBirthYear(String birth){
        if (birth == null){
            return null;
        }
        return birth.substring(2,4);
    }

    public static String displayEntryYear(Integer entryYear){
        if (entryYear == null){
            return null;
        }
        return entryYear.toString().substring(2,4)+"학번";
    }
}
