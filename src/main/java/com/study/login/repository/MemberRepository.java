package com.study.login.repository;

import com.study.login.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// 레파지토리는 interface로 만들기 - 까먹지말기
// 레파지토리가 데이터베이스랑 작업하는 최종 단계 항상 디비에 entity 로 넘겨줘야함
public interface MemberRepository extends JpaRepository<MemberEntity, Long > {
    //                                        <어떤 entity 다룰것이냐 , 이 entity에 pk >

    // 이메일로 회원 정보 조회 - 로그인 (select * from member_table where member_email =?)
    //                              이 작업을 아래코드 한줄로 해준거다
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
    // Optional - null 방지
    // repository 에서 주고받는 객체는 무조건 entity

}
