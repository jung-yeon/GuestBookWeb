package org.zerock.guestbook.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    // 목록 페이지를 요청할 때 사용하는 데이터를 재사용하기 쉽게 만드는 클래스
    // 페이지 번호, 페이지 내 목록의 개수, 검색 조건 등의 파라미터를 DTO로 선언하고
    // 나중에 재사용하는 용도로 사용


    // 목적 - JPA쪽에서 사용하는 Pageable타입의 객체를 생성


    private int page;
    private int size;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    //JPA를 이용하는 경우에는 페이지 번호가 0부터 시작한다는 점은 감안해
    // 1페이지의 경우 0이 될 수 있도록 page - 1을 하는 형태로 작성
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }


}
