package org.zerock.guestbook.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;


@SpringBootTest
class GuestbookServiceTest {

    @Autowired
    private GuestbookService service;

    @Test
    @DisplayName("GuestbookDTO를 Guestbook 엔티티로 변환 테스트")
    //실제로 DB에 저장되지는 X
    public void testRegister() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("테스트제목")
                .content("테스트 내용")
                .writer("이정짱")
                .build();

        System.out.println(service.register(guestbookDTO));
    }


    @Test
    @DisplayName("목록 처리 테스트_엔티티 객체들 DTO객체로 변환")
    //Page<Guestbook>이 List<GuestBookDTO>로 정상적으로 변환되어
    //GuestbookDTO타입으로 출력됨
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO,Guestbook> resultDTO = service.getList(pageRequestDTO);
        System.out.println("PREV : "+ resultDTO.isPrev());
        System.out.println("NEXT : "+ resultDTO.isNext());
        System.out.println("TOTAL : "+ resultDTO.getTotalPage());
//        System.out.println("-------------------------------------------");
        for(GuestbookDTO guestbookDTO: resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
        System.out.println("===========================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}