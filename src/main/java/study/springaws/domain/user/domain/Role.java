package study.springaws.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    MANAGER("ROLE_MANAGER", "운영자");

    private final String key;
    private final String title;
}
