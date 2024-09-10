package com.study.login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTest {
    @DisplayName("1+2는 3이다") // 테스트 이름
    @Test // 테스트 메서드
    public void junitTest() {
        int a = 1;
        int b = 2;
        int sum = 3;
        Assertions.assertEquals(sum, a + b );
        // assertEquals 로 a+ b 의 값이 sum 과 같은지 확인
    }

    @DisplayName(" 1 + 3 은 4이다.")
    @Test
    public void junitFailedTest() { // 실패 테스트 하나라도 실패하면 전체 테스트를 실패
        int a = 1;
        int b = 3;
        int sum = 3;
        Assertions.assertEquals(sum, a + b );
    }
}
