package study.springaws.global.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import study.springaws.domain.user.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class PrincipalDetails implements OAuth2User {

    private final User user;
    private final Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new SimpleGrantedAuthority(user.getRole().getKey()));
        return collect;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return user.getNickname();
    }

    public String getUserPicUrl(){
        return user.getImgUrl();
    }

    // 타임리프 뷰단처리를 위한 편의 메소드
    public Long getUserId(){
        return user.getId();
    }
}
