package org.zerock.guestbook.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook") //들어온 요청을 특성 method와 매핑하기 위해 사용
@Log4j2
public class GuestBookController {
    @GetMapping({"/","/list"})
    public String list(){
        //발생되는 모든 행위와 이벤트 정보를 시간에 따라 남겨둔 데이터
        //연속된 데이터의 기록
        log.info("list...........");
        return "/guestbook/list";
    }
}