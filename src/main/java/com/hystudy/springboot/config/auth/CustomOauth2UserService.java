package com.hystudy.springboot.config.auth;

import com.hystudy.springboot.config.auth.dto.OAuthAttributes;
import com.hystudy.springboot.config.auth.dto.SessionUser;
import com.hystudy.springboot.domain.user.User;
import com.hystudy.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // registrationId : 현재 로그인 진행중인 서비스를 구분하는 코드(구글,네이버 등등..)
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // 키가되는 필드값 -> PK과 같은의미
        // 구글은 기본 코드를 지원하나 네이버나 카카오는 지원 안함 구글로그인과 네이버 동시 지원시 사용

        // OAuthAttributes : OAuth2UserService 를 통해 가져온 OAuth2User attribute 를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        // SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey()))
                , attributes.getAttributes()
                , attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}
