package org.zerock.guestbook.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.entity.Guestbook;

import java.util.stream.IntStream;


@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    // 직관적인 테스트 이름을 확인하고 싶다면 아래 주석 풀어주세요
//    @DisplayName("1-100 데이터 삽입 테스트")
    public void insertDummies(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

}