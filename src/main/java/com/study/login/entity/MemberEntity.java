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

    //이 코드에서 `new MemberEntity()`를 사용하는 이유는 새로운 `MemberEntity` 객체를 만들어서,
    // 그 객체의 속성들을 `memberDto`로부터 받아온 값들로 채우기 위해서예요.
    //
    //쉽게 말해서:
    //
    //1. **새 객체 생성 (`new MemberEntity()`)**:
    //   - 아무것도 없는 빈 `MemberEntity` 객체를 만들어요. 이건 마치 빈 종이를 준비하는 것과 같아요.
    //
    //2. **속성 설정 (`setId`, `setMemberEmail` 등)**:
    //   - `memberDto`에서 가져온 데이터를 새로 만든 `MemberEntity` 객체에 채워 넣어요. 마치 빈 종이에 필요한 정보를 써 넣는 것처럼요.
    //
    //3. **반환**:
    //   - 이제 필요한 데이터가 채워진 `MemberEntity` 객체를 반환해요. 이 객체는 다른 메서드나 클래스에서 사용될 준비가 되어 있어요.
    //
    //### 왜 `new`를 해야 할까?
    //
    //- **기존 객체가 없는 경우**: 업데이트할 때 사용하는 객체는 새로운 정보로 채워진 새로운 객체여야 해요.
    // 기존의 객체를 재사용하는 게 아니라, 새로운 정보를 담은 완전한 새로운 객체를 만들어야죠.
    //
    //- **독립적인 객체 생성**: 이렇게 하면 어떤 기존의 `MemberEntity` 객체에도 영향을 주지 않고
    // , 완전히 독립된 객체가 생성돼요.
    //
    //예를 들어, 학교에서 새로운 학생을 등록한다고 생각해 봐요:
    //- 새로운 학생을 등록하려면 먼저 빈 학생 기록지를 꺼내고(`new MemberEntity()`),
    //- 이름, 나이, 학번 같은 정보를 기록지에 써넣고(`set` 메서드 사용),
    //- 완성된 기록지를 학교 시스템에 등록하는 거죠(반환).
    //
    //이 과정이 `new MemberEntity()`와 속성 설정(`set` 메서드) 그리고 반환 과정과 비슷해요!
    //
    //즉, 새로운 객체를 만들어서 필요한 정보를 넣어주고 그 객체를 반환하는 것이에요.

    // 올바른 엔티티 생성 방법
    // 위에처럼 new를 하거나 build 패턴 사용

    // 서비스나 컨트롤러에서는 의존성 주입을 통해 객체를 관리하지만,
    // 엔티티는 다루는 데이터마다 새로운 인스턴스를 생성해야 하므로 주입받지 않습니다.

}
