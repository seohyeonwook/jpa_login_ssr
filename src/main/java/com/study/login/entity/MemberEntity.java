package com.study.login.entity;

import com.study.login.dto.MemberDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "member_table") // 디비에 클래스에 정의한대로 테이블이생성된다 name = 테이블 이름
public class MemberEntity { // db의 테이블 역할 jpa는 entity랑 repository에서 사용
    @Id // 프라이머리 키 (pk) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(unique = true) // unique 제약조건 추가
    private String memberEmail;// 카멜 작성법으로 작성하면 테이블에 들어갈땐 _로 바뀐다 member_email

    @Column // 제약조건 없으면 Column 생략 가능 알아서 만들어준다
    private String memberPassword;

    @Column
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDto memberDto) { // 빌더로 만들어도 됨
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberName(memberDto.getMemberName());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDto memberDto) { // 이건 업데이트용임
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberName(memberDto.getMemberName());
        return memberEntity;
    }

    //엔티티 클래스는 의존성 주입(Dependency Injection)의 대상이 아닙니다.
    // 서비스나 컨트롤러와 달리 엔티티는 단순히 데이터의 상태를 나타내기 때문에,
    // 의존성 주입을 통해 final 필드로 주입할 필요가 없습니다.

    //왜 new를 사용해야 하는가?
    //각기 다른 데이터를 가진 객체 생성: 엔티티는 데이터베이스의 각 행(row)과 대응되기 때문에
    // 각기 다른 데이터를 가진 여러 인스턴스가 필요합니다.
    //상태 변경 가능성: 엔티티의 상태는 언제든지 변경될 수 있어야 합니다.
    // 이는 주입받은 final 객체로는 불가능합니다.

    // 올바른 엔티티 생성 방법
    // 위에처럼 new를 하거나 build 패턴 사용

    // 서비스나 컨트롤러에서는 의존성 주입을 통해 객체를 관리하지만,
    // 엔티티는 다루는 데이터마다 새로운 인스턴스를 생성해야 하므로 주입받지 않습니다.

}
