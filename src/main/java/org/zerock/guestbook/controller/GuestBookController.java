package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook") //들어온 요청을 특성 method와 매핑하기 위해 사용
@Log4j2
@RequiredArgsConstructor// 자동 주입을 위한 어노테이션
public class GuestBookController {
    private final GuestbookService service; //final로 선언

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        //발생되는 모든 행위와 이벤트 정보를 시간에 따라 남겨둔 데이터
        //연속된 데이터의 기록
        log.info("list..........." + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));

    }

    //등록처리

    //Get방식- 화면 보여줌
    @GetMapping("/register")
    public void register(){
        log.info("register get...");
    }
    //Post방식 - 처리후에 목록페이지로 이동하도록 설계
    //RedirectAttributes - 한번만 화면에서 'msg'라는 변수를 사용할 수 있도록 처리
    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto..." + dto);
        Long gno = service.register(dto);
        //새로 추가된 엔티티의 번호

        //addFlashAttribute - 단한번만 데이터를전달
        redirectAttributes.addFlashAttribute("msg",gno);
        return "redirect:/guestbook/list";
    }

    @GetMapping("/read")
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("gno : " + gno);
        GuestbookDTO guestbookDTO = service.read(gno);
        model.addAttribute("dto",guestbookDTO);
    }

}