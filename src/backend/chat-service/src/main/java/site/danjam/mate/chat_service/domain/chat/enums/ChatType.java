package site.danjam.mate.chat_service.domain.chat.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatType {
    TEXT("채팅"), PROFILE("프로필공유");
    private final String chatType;
}
