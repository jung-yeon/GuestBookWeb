package org.zerock.guestbook.entity;


import lombok.*;

import javax.persistence.*;


// 실제 테이블을 생성할 Guestbook 클래스 생성
/* Entity클래스는 가능하면 setter 관련 기능을 만들지 않는 것이 권장사항
 * 왜?
 * 1. 게시글클래스라고 예를 들때 게시글의 값을 생성하는 것인지 변경하는 것인지 정확한 의도를 파악하기 힘들다
 * 2. public으로 작성된 setter 메소드를 통해 어디서든 접근이 가능하기에 의도치 않게 post의 값을 변경하는 경우가 발생할 수 있다
 *          => 일관성 유지가 어렵다
 *
 * BUT!!!
 * 필요에 따라  수정기능을 만들기도 함
 * (엔티티 객체가 어플리케이션 내부에서 변경되면 JPA를 관리하는 쪽이 복잡해질 우려가 있기 때문에
 * 가능하면 최소한의 수정이 가능하도록 하는것을 권장)

*/
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

    // BaseEntity의 modDate는
    // 최종 수정 시간이 반영되기 때문에 특정한 엔티티를 수정한 후에 save()했을 경우에 동작
    // 제목을 수정할 메서드
    public void changeTitle(String title){
        this.title = title;
    }
    // 내용을 수정할 메서드
    public void changeContent(String content){
        this.content = content;
    }
}

//BaseEntity 클래스를 상속해서 작성하므로 등록시간과 수정시간을 처리해줌

