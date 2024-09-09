package com.study.login.dto;

import com.study.login.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor // 기본생성자 자동생성
@AllArgsConstructor // 필드 생성자
@ToString
public class MemberDto {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    // dto entity는 의존성 주입 대상이 아니다 그래서 따로 객체 생성 안한다
    // eg) private final MemberService memberService;
    // entitiy나 dto는 데이터가 변해야 하기때문에 final 안 붙인다 둘다 필요한 시점에 new붙인다
    // 예를 들어 dto에서 entity로 객체 변환할때 혹은 entity에서 dto로 객체 변환할때

    // 근데 entity는 객체변환할때 new쓰는데 왜 dto는 new안쓰나??
    // dto는 제일먼저 데이터가 들어온다 그말은 컨트롤러에서 제일먼저 사용한다 그렇다면
    // service처럼 의존성 주입해주는 구문이있어야하는데 dto는 @Model이나 @RequestBody 에
    // new기능이 들어가있다 라고 생각하자

    // eg) public String save(@ModelAttribute MemberDto memberDto) {
    //    // DTO 객체는 여기서 이미 생성되고 요청 데이터로 초기화된 상태임

    public static MemberDto tomemberDto(MemberEntity memberEntity) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberEntity.getId());
        memberDto.setMemberEmail(memberEntity.getMemberEmail());
        memberDto.setMemberPassword(memberEntity.getMemberPassword());
        memberDto.setMemberName(memberEntity.getMemberName());
        return memberDto;
    }

}
