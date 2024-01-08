package org.zerock.guestbook.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.repository.GuestbookRepository;


@SpringBootTest
class GuestbookTest {
    @Autowired
    GuestbookRepository guestbookRepository;

    @Test
    @DisplayName("의존성 주입 문제 확인 테스트")
    public void testClass(){
        System.out.println(guestbookRepository.getClass().getName());
    }
}