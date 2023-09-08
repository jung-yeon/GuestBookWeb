package org.zerock.guestbook.entity;


import lombok.*;

import javax.persistence.*;


// 실제 테이블을 생성할 Guestbook 클래스 생성
@Entity
@Getter
@Builder
//@AllArgsConstructor와 @NoArgsConstructor: 이 어노테이션은 모든 필드를 인자로 받는 생성자와 인자가 없는 기본 생성자를 자동으로 생성
@AllArgsConstructor
@NoArgsConstructor
//@ToString: 롬복의 @ToString 어노테이션은 객체를 문자열로 표현할 때 모든 필드를 포함한 문자열을 생성
@ToString
public class Guestbook extends BaseEntity{
    /*
     *@Id 및 @GeneratedValue: gno 필드는 엔티티의 주키(primary key)로 사용됩니다.
     *  @GeneratedValue 어노테이션은 주키 값이 자동으로 생성되며,
     * GenerationType.IDENTITY를 통해 자동 증가되는 식별자를 사용하도록 설정
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gno;

    @Column(length = 100, nullable = false)
    private  String title;

    @Column(length = 1500, nullable = false)
    private  String content;

    @Column(length = 50, nullable = false)
    private  String writer;
}

//BaseEntity 클래스를 상속해서 작성하므로 등록시간과 수정시간을 처리해줌
