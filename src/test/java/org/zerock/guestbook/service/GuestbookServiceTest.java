package org.zerock.guestbook.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
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
                .gno(101L)
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();

        System.out.println(service.register(guestbookDTO));
    }
}