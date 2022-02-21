package com.hystudy.springboot.config.auth;

import com.hystudy.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOauth2UserService customOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // .csrf().disable().headers().frameOptions().disable() : h2-console 화면을 사용하기 위해 해당 옵션들을 disable
        http.csrf().disable().headers().frameOptions().disable()
            .and()
                // authorizeRequests :URL 별 권한관리를 설정하는 옵션의 시작점, 이 옵션이 선언되어야 antMatches 옵션을 사용가능
                .authorizeRequests()
                // antMatchers : 권한관리 대상을 지정하는 옵션, URL, HTTP 메소드별로 관리가능
                // -> permitAll : 해당 url 전체 열람 권한 부여
                // -> hasRole : User 권한을 가진사람만 권한 부여
                .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                // anyRequest : 위의 url 을 제외한 모든 URL
                .anyRequest().authenticated() // 나머지 URL 은 모두 인증된 사용자들에게만 허용
            .and()
                .logout().logoutSuccessUrl("/") // logout -> 로그아웃 기능의 집입점, logoutSuccessUrl -> 로그아웃 성공 시 URL
            .and()
                // oauth2Login : oauth2Login -> oauth2 로그인 진입점
                // userInfoEndpoint : Oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                // userService : 소셜 로그인 성공 시 후속 조치를 진행 할 UserService 인터페이스의 구현체를 등록
                // -> 리소스 서버(소셜 서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
                .oauth2Login().userInfoEndpoint().userService(customOauth2UserService);
    }
}
