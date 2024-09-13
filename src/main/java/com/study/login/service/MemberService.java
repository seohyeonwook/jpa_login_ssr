package com.study.login.service;

import com.study.login.dto.MemberDto;
import com.study.login.entity.MemberEntity;
import com.study.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDto memberDto) { // 자동생성된 메소드
        // 1. dto -> entity 변환  - 방법은 여러가지 - dto에서 entity 변환시키는 작업은 entity클래스에서
        // 1.1 entity를 dto로 변환시키는 작업은 dto클래스에서 처리한다
        // 2. repository의 save메소드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto); // alt enter 치면 좌변 자동으로 만들어줌
        // 엔티티로 변환 후 저장
        memberRepository.save(memberEntity); // save우리가 만드는게 아니라jpa가 제공해주는 메소드 그래서 repositoy에 save메소드 없음
        // 3. repository 의 save메서드 호출 (조건. entity객체를 넘겨줘야함)
        // 4. 이렇게까지하면 jpa가 알아서 메소드만들어주고 회원가입 진행하면 db에 자료 넘겨줌
    }

    public MemberDto login(MemberDto memberDto) {
        // 1. 회원이 입력한 이메일로 DB에서 조회를 함 - 이건 repositoy에서 메소드 구현만하자
        // 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단

        Optional<MemberEntity> byMemberEmail =
                memberRepository.findByMemberEmail(memberDto.getMemberEmail());
        // MemberEntity 실제 사용하는건 entity인데 포장지로 optional사용
        // 하나를 조회할때는 optional 사용 아래에 각각 메소드 모양도 잘 보자
        if(byMemberEmail.isPresent()) {
            // isPresent() = Optional 객체에 포함된 값이 존재하는지 여부를 확인하는 메서드 있으면 true 없으면 false
            // 조회 결과가 있다( 해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get();// optional 객체 벗겨서entity email만 가지고온다
            if(memberEntity.getMemberPassword().equals(memberDto.getMemberPassword())) {
                // 비밀번호가 일치  - if 이메일이 인증되면 비밀번호if문으로 들어온다
                // entity 를 컨트롤러로 리턴해주려면 다시 dto로 바꿔줘야한다
                MemberDto dto = MemberDto.tomemberDto(memberEntity); // dto로 받아야하니까 자료형 dto
                return dto;
            } else {
                // 비밀번호 불일치 (로그인 실패)
                return null;
            }
        } else {
            // 여기걸리면 조회 결과가 없다 ( 해당 이메일을 가진 회원이 없다)
            return null;
        }
        
    }

    public List<MemberDto> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();// 자동완성 alt enter사용하자 여기서도 사용잘 하자 ; 끝에서 사용
        // repository 와 관련된건 무조건 entity 여기서도 그래서 리스트에 entity담겨온다
        // 그럼 당연히 entity -> dto
        List<MemberDto> memberDtoList = new ArrayList<>(); // dto로 담아갈 객체 생성
        // 위에는 entity 객체 하나를 dto로 변환했지만 리스트 쓰는 이유는 디비에 저장되어있는 모든걸 들고와야하니까
        for(MemberEntity memberEntity : memberEntityList) {
            memberDtoList.add(MemberDto.tomemberDto(memberEntity));
        }
        return memberDtoList;
    }

    public MemberDto findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDto memberDto = MemberDto.tomemberDto(memberEntity);
//            return memberDto;
            // 이거 3줄 이랑 아래 한줄이랑 같은 코드
            return MemberDto.tomemberDto(optionalMemberEntity.get());
                    // 위에랑 다르게 바로 리턴에 담아도 된다
                    // 코드 설명 optional 객체를 get() 까서 Dto로 변환해서 리턴한다
        } else {
            return null;
        }
    }

    public MemberDto updateForm(String myEmail) { // 이메일로 조회하고 만들어둔 findByMemberEmail 사용 단건이라 Optional 사용
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(myEmail);
        if(byMemberEmail.isPresent()) { // findById 메소드와 결국 동일
            return MemberDto.tomemberDto(byMemberEmail.get());
            // 코드 읽기 순서
            // optionalMemberEntity.get()으로 Optional에서 값을 꺼내오는 부분을 먼저 읽습니다.
            // 그 다음, 꺼내온 값을 MemberDto.toMemberDto() 메서드에 전달하여 변환하는 부분을 읽습니다.
            // 마지막으로 변환된 값을 return으로 반환하는 부분을 확인합니다.

            // 전체적인 순서 정리
            // optionalMemberEntity.get(): Optional에서 실제 값을 가져옵니다.
            // MemberDto.toMemberDto(...): 가져온 값을 MemberDto로 변환합니다.
            // return: 변환된 MemberDto 객체를 반환합니다.
        } else {
            return null;
        }// 주석
    }

    public void update(MemberDto memberDto) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDto));
    // save - id가 없으면 insert 해주지만 id가 들어오면 update쿼리를 날려주는 알다가도 모를친구
        // toUpdateMemberEntity = id 넣는 복사 + id 메서드
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    } // 주석 2 // 주석 3
}
