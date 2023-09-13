package org.zerock.guestbook.service;

import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);
    //java8버전 부터는 인터페이스의 실제 내용을 가지는 코드를 default라는 키워드로 생성 가능
    // 기존에 추상 클래스를 통해서 전달해야 하는 실제 코드를 인터페이스에 선언 가능
    // 인터페이스 -> 추상 클래스 -> 구현 클래스  ===> 원래의 방식
    // 인터페이스 -> 구현 클래스  ====> default키워드로 코드 생성한 방식
    default Guestbook dtoToEntity(GuestbookDTO dto){
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
}
