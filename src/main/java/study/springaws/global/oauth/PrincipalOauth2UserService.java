package study.springaws.global.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import study.springaws.domain.user.domain.Role;
import study.springaws.domain.user.domain.User;
import study.springaws.domain.user.repository.UserRepository;
import study.springaws.global.oauth.provider.GoogleUserInfo;
import study.springaws.global.oauth.provider.Oauth2UserInfo;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest); // 프로바이더에서 제공받은 유저정보

        System.out.println("oAuth2User = " + oAuth2User);

        // 회원가입 강제로 진행
        Oauth2UserInfo oauth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oauth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else {
            log.info("현재 구글 로그인 서비스만 지원하고 있습니다.");
        }

        String username = oauth2UserInfo.getProvider() + "_" + oauth2UserInfo.getProviderId();
        String password = bCryptPasswordEncoder.encode("gudrhs11");

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            userEntity = User.builder()
                    .username(username)
                    .email(oauth2UserInfo.getEmail())
                    .password(password)
                    .role(Role.GUEST)
                    .nickname(oauth2UserInfo.getName())
                    .provider(oauth2UserInfo.getProvider())
                    .providerId(oauth2UserInfo.getProviderId())
                    .imgUrl(oauth2UserInfo.getPicUrl())
                    .build();

            userRepository.save(userEntity);
        } else {
            log.info("이미 가입 이력이 존재하는 회원입니다.");
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
