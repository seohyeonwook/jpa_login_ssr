package com.study.login.controller.test;

import com.study.login.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest // 테스트용 애플리케이션 컨텍스트 생성
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성 -
// MockMvc 는 애플리케이션을 서보에 배포하지 않고도 테스트옹 MVC 환경을 만들어 요청 및 전송, 응답기능 제공
class TestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach // 테스트 실행 전 실해하는 메서드
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach // 테스트 실행 후 실행하는 메서드
    public void cleaUp() {
        memberRepository.deleteAll();
    }
    //---------------여기 까지 테스트 코드 작성 이제부터는 TestController 의 로직 테스트 코드 작성 -------------------//

    @DisplayName("getAllMembers: 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception {
        // given - 멤버를 저장
        final String url = "/test";
        Member savedMember = memberRepository.save(new Member(1L, "홍길동")); // 데이터 sql 안만들어서 빨간줄

        // when - 멤버 리스트를 조회하는 API를 호출
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON);

        // then - 응답 코드가 200ok 이고 반환받은 값 중에 0번쨰 요소의 id와 name이 저장된 값과 같은지 확인합니다.
        result
                .andExpect(status().isOK)())
        .andE

    }

}