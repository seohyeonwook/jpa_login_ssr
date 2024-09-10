package com.study.login;

import org.junit.jupiter.api.*;
// 실행 순서

//@BeforeAll

//@BeforeEach
//test1
//@AfterEach

//@BeforeEach
//test2
//@AfterEach

//@BeforeEach
//test3
//@AfterEach

//@BeforeEach
//test4
//@AfterEach

//@AfterAll

public class JUnitCycleTest {
    @BeforeAll // 전체 테스트를 시작하기 전에 1회 실행하므로 메서드는 static으로 선언
                // eg) 데이터 베이스를 연결하거나 테스트 환경을 초기화 할 때 사용됨
    static void beforeAll() {
        System.out.println("@BeforeAll");
    }
    @BeforeEach // 테스트 케이스를 시작하기 전마다 실행
                // eg) 테스트 메서드에서 사용하는 객체 초기화 or 테스트에 필요한 값을 미리 넣을때 사용
                // 각 인스턴스에 대해 메서드를 호출해야 하므로 메서드는 static이 아니어야 함
    public void beforeEach() {
        System.out.println("@BeforeEach");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }
    @Test
    public void test3() {
        System.out.println("test3");
    }
    @Test
    public void test4() {
        System.out.println("test4");
    }

    @AfterAll // 전체 테스트 마치고 종료하기 전에 1회 실행하므로 메서드는static으로 선언
                // eg ) 데이터 베이스 연결을 종료할 때나 공통적으로 사용하는 자원을 해제
    static void afterAll() {
        System.out.println("@AfterAll");
    }
    @AfterEach // 테스트 케이스를 종료하기 전마다 실행
                // eg ) 테스트 이후에 특정 데이터를 삭제해야하는 경우
    public void afterEach() {
        System.out.println("@AfterEach");
    }
}
