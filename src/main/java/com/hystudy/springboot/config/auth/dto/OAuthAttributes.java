package com.hystudy.springboot.config.auth.dto;

import com.hystudy.springboot.domain.user.Role;
import com.hystudy.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes,String nameAttributeKey,String name,String email,String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    // OAuth2User 에서 반환하는 사용자정보는 Map 이기 때문에 하나하나 변환해야함
    public static OAuthAttributes of(String registrationId,String userNameAttributeName,Map<String,Object> attributes){
        if("naver".equals(registrationId)){
            return ofNaver("id",attributes);
        }else if("kakao".equals(registrationId)){
            return ofKaKao("id",attributes);
        }
        return ofGoogle(userNameAttributeName,attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName,Map<String,Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName,Map<String,Object> attributes){
        Map<String,Object> response = (Map<String,Object>)attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("picture"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofKaKao(String userNameAttributeName,Map<String,Object> attributes){
        Map<String,Object> kakao_account = (Map<String,Object>)attributes.get("kakao_account");
        Map<String,Object> profile = (Map<String,Object>)kakao_account.get("profile");
        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) kakao_account.get("email"))
                .picture((String) profile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // 처음 가입시 생성 / 가입할 때의 기본권한 GUEST / 클래스 생성이 끝나면 같은 패키지의 SessionUser 클래스 생성
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
