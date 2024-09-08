package com.study.login.controller;

import com.study.login.dto.MemberDto;
import com.study.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor // 서비스 객체 만들어주는 어노테이션
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    // * 계속 나오는 model 과 같이 쓰는 attribute 이해하기 -- SSR에서만 사용
    // model.addAttribute는 스프링 MVC에서 컨트롤러가 뷰(HTML)로 데이터를 전달할 때 사용하는 메서드입니다.
    // 쉽게 말해서, 컨트롤러에서 데이터를 뷰로 넘겨줄 때 model.addAttribute를 사용해 데이터를 담아서 보내는 역할을 합니다.
    //
    //쉽게 이해하기
    //컨트롤러는 데이터를 처리하고, 뷰는 그 데이터를 화면에 보여주는 역할을 합니다.
    // 이때, 컨트롤러에서 뷰로 데이터를 넘겨주려면 그릇이 필요합니다.
    // 그 그릇이 바로 Model입니다.

//--------------------------------------------------------------------------------------------------------------------

    // 회원가입 페이지 출력 요청 - 회원가입 요청이아님 페이지 만드는거임
    @GetMapping("/member/save") //링크 클릭하는건 다 get요청 정보 공개 하면 안되면 post
    public String saveForm() {
        return "save"; // 요청이 오면 save html로 보내준다
    }

    // dto -> controller -> service -> repository -> db

//    @PostMapping("/member/save") // 이게 회원가입 - 1번 방법
//    public String save(@RequestParam("memberEmail") String memberEmail,
//                       @RequestParam("memberPassword") String memberPassword,
//                       @RequestParam("memberName") String memberName) { // dto안쓰고 일일이 받는형식 - 이런것도 알아야지 객체가 넘어오는방식도 이해하지
//        // RequestParam post요청 보낼때 name의 변수이름을 담아서() 옆에 String에 옮겨 담는다
//        System.out.println("컨트롤러 자체가 실행되는지 확인용");
//        System.out.println("memberEmail = " + memberEmail + ", memberPassword = " + memberPassword + ", memberName = " + memberName);
//        // soutp 누르면 매개변수를 자동으로 이렇게 매개변수가 잘 넘어오는지 확인한다
//        return "index";
//    }

    @PostMapping("/member/save") // 회원가입 -2번 방법
    public String save(@ModelAttribute MemberDto memberDto) {
//        Model은 컨트롤러에서 뷰(HTMl)로 데이터 전달할때 씀
        // 일반적인 리액트나 프론트에서 데이터 받을때는 JSON형식으로 받기때문에Model안씀
        System.out.println("memberDto = " + memberDto);
//        MemberService memberService = new MemberService(); 이방법 안쓰고 위에 객체 주입방식(생성자 주입)으로 사용
        memberService.save(memberDto); // save에 빨간줄 있을때 올리고 create하면 service 클래스에 메소드 자동생성해줌
        return "login"; // 회원가입 완료되면 로그인 페이지 보내기
    }
//--------------------------------------------------------------------------------------------------------------------
    @GetMapping("/member/login")// 로그인페이지 띄워줌
    public String longinForm() {
        return "login"; // "/member/login" 주소요청이오면 login페이지 띄워준다
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpSession httpSession) { // sesstion방식 적용
        MemberDto loginResult = memberService.login(memberDto); // 로그인 성공여부 알려줘야하니까 loginResult에 값 저장
        // 자료형과 호출한 메소드의 반환타입은 일치해야한다 아마 login도 반환타입이 MemberDto 아닐까
        if(loginResult != null) {
            //login 성공
            httpSession.setAttribute("loginEmail", loginResult.getMemberEmail());
            // 로그인한 회원에 이메일 정보(get)를 세션("loginEmail")에 담아준다 이걸 담은 "loginEmail"을 타임리프같은곳에서 사용한다

            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    @GetMapping("/member/") // 멤버 리스트 불러오기 - 디비에 모든 데이터 가지고온다
    public String findAll(Model model) {// Model- 싫어나르는 역할
        List<MemberDto> memberDtoList = memberService.findAll(); // 다양한객체를 같이담아갈땐 map
        // 어떠한 html로 가져갈 데이터가 있다면 model 사용 -- ssr
        model.addAttribute("memberList",memberDtoList);
        return "list";
    }

    //-----------------------------------------------------------------------------------------------------------------

    @GetMapping("/member/{id}") // {} 사용하면 @PathVariable {}에 있는 값을 long에 담아온다
    public String findById(@PathVariable Long id, Model model) {
        MemberDto memberDto = memberService.findById(id); // 조회하는 데이터가 하나면 dto 여러개면 리스트
        model.addAttribute("member",memberDto);
        return "detail";
    }

    //-----------------------------------------------------------------------------------------------------------------

    @GetMapping("/member/update")
    public String updateForm(HttpSession session , Model model) {
        // 세션에 있는 이메일 정보를 가지고와서 나의 전체정보를 디비에서 가져와서 모델에 담아서 update.html로 가져간다
        String myEmail = (String) session.getAttribute("loginEmail");
        //getAttribute 는 리턴타입이 오브젝트인데 우리가 담으려고하는 자료형은 스트링이라 그릇보다 내용물이 크다 그래서 형변환 해야함
        MemberDto memberDto  = memberService.updateForm(myEmail);
        model.addAttribute("updateMember",memberDto);
        // service 에서 받아온 memberDto를 "updateMember"로 담고 return"update" 로 간다
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDto memberDto) {
        memberService.update(memberDto);
        return "redirect:/member/" + memberDto.getId();
    }

    //-----------------------------------------------------------------------------------------------------------------

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
    }

    //-----------------------------------------------------------------------------------------------------------------

    @GetMapping("/member/logout") // 간단하게 구현 실제로는 안 이럼 흐름만 알자
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
}

