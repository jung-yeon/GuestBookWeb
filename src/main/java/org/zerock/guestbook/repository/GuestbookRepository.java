package org.zerock.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.guestbook.entity.Guestbook;

// 1. JPA를 이용해서 구성하고, GuestbookServiceImpl클래스에 주입해서 사용하기위해 생성
public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, QuerydslPredicateExecutor<Guestbook> {
    /*
     * JPA의 쿼리 메서드의 기능과 @Query를 통해 많은 기능을 구현할 수는 있지만,
     *  선언할 때 고정도니 형태의 값을 가진다는 단점을 가짐
     * 복잡한 조합을 이용하는 경우 동적으로 쿼리를 생성해서 처리할 수 있는 기능이 필요
     * 이러한 상황을 처리할 수 있는 기술 --> Querydsl
     */
    /*
     * Querydsl을 이용하면 코드 내부에 상황에 맞는 쿼리를 생성할 수 있지만
     * 작성된 엔티티 클래스를 그대로 이용하는 것이 아닌 'Q도메인'이라는 것을 이용해야함
     * 추가적인 설정이 필요
     *
     * plugins 항목에 querydsl 관련 부분 추가
     * dependencies 항목에 필요한 라이브러리 추가
     */
}
