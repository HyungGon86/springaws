package study.springaws.global.oauth.provider;

public interface Oauth2UserInfo {

    String getProvider();

    String getProviderId();

    String getEmail();

    String getName();
}
