package com.example.oauthjwt.service;

import com.example.oauthjwt.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    //리소스서버에서 제공되는 유저 정보
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {


        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("oAuth2User: {}", oAuth2User);

        //서비스가 네이버에서 온건지, 구글에서 온건지 확인하기 위한 registrationId
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //리스폰스를 받을 변수
        OAuth2Response oAuth2Response = null;

        if(registrationId.equals("naver")){
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }else if(registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }else{

            return null;
        }

        //로그인
        // 리소스 서버에서 oauth2servcie로 데이터를 받으면 최종적으로 oauth2user라는 dto에 담아서 앞단인 provicer에 넘겨주면 로그인 진행

        //유저네임: 리소스서버에서 받은 값은 해당 유저들이 겹칠 수 있기 때문에, 특정하게 우리 서버에서 관리할 수 있는 username을 만들어야한다.
        String username = oAuth2Response.getProvider() + " "+ oAuth2Response.getProviderId();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setName(oAuth2Response.getName());
        userDTO.setRole("ROLE_USER");

        return new CustomOAuth2User(userDTO);
    }
}
