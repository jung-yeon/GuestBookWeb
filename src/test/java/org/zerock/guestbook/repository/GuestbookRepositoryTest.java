package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    // 직관적인 테스트 이름을 확인하고 싶다면 아래 주석 풀어주세요
    @DisplayName("1-100 데이터 삽입 테스트")
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

    @Test
    @DisplayName("제목/내용 수정 테스트")
    //3번의 sql
    public void updateTest(){
        //존재하는 번호로 테스트
        //위에서 1~100까지 삽입했으니 그사이 아무 아이디나 넣으면 됨
        Optional<Guestbook> result = guestbookRepository.findById(100L);
        if(result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title...");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);
        }
    }

    @Test
    @DisplayName("단일 항목 검색 테스트")
    public void searchSingleTest(){
        //20개 출력
        Pageable pageable = PageRequest.of(0,20, Sort.by("gno").descending());
        // 동적으로 처리하기 위해 Q도메인 클래스 얻어옴
        QGuestbook qGuestbook = QGuestbook.guestbook;

        //제목에 1이라는 글자가 있는 엔티티들을 검색
        String keyword= "1";

        // BooleanBuilder : where문에 and나 or같은 키워드와 결합시킴
        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression expression = qGuestbook.title.contains(keyword);
        //where문에 and나 or같은 키워드와 결합
        builder.and(expression);

        // BooleanBuilder는 GuestRepository에 추가된 QuerydslPredicateExcultor 인터페이스의 findAll()을 사용할 수 있음
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(System.out::println);

        //페이지 처리와 동시에 검색 처리 가능
    }

    @Test
    @DisplayName("다중 항목 검색 테스트")

    //제목 혹은 내용에 특정한 키워드가 있고 gno가 0보다 크다라는 조건 처리
    public void searchMultiTest(){
        //10개 출력
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());
        // 동적으로 처리하기 위해 Q도메인 클래스 얻어옴
        QGuestbook qGuestbook = QGuestbook.guestbook;

        //제목에 1이라는 글자가 있는 엔티티들을 검색
        String keyword= "1";

        // BooleanBuilder : where문에 and나 or같은 키워드와 결합시킴
        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        //where문에 and나 or같은 키워드와 결합
        builder.and(exAll);

        // 'gno가 20보다 크다' 라는 조건
        builder.and(qGuestbook.gno.gt(20L));

        // BooleanBuilder는 GuestRepository에 추가된 QuerydslPredicateExcultor 인터페이스의 findAll()을 사용할 수 있음
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(System.out::println);

        //페이지 처리와 동시에 검색 처리 가능
    }
}