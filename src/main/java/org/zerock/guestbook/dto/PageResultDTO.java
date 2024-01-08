package org.zerock.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//화면까지 전달되는 데이터
@Data
// 페이지 결과 처리DTO
// - Page<Entity>의 엔티티 객체들을 DTO객체로 변환해서 자료구조로 담아 주어야함
// - 화면 출력에 필요한 페이지 정보들을 구성해 주어야함
// - 다양한 종류의 엔티티를 다루기 때문에 제네릭방식으로 적용해두면 나중에 추가적인 클래스를 작성하지 않고도 목록데이터를 처리할수 있음
public class PageResultDTO<DTO, EN> {
    //DTO 리스트
    private List<DTO> dtoList;
    //총 페이지 번호
    private int totalPage;

    //현재 페이지 번호
    private int page;

    //목록사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;
    // 이전 다음
    private boolean prev, next;
    //페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        //마지막 페이지가 33이라면 Math.ceil계산으로 인해 40이 되기 때문에 아래와 같이 반영
        totalPage = result.getTotalPages(); // result는 Page<Guestbook>
        makePageList(result.getPageable());

    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;//0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();
        //temp and page
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }
}
// 끝번호를 먼저 계산해두는것이 수월
// 끝 번호를 구하는 공식(페이지가 10개씩 보인다고 가정)
// tempEnd = (int)(Math.ceil(페이지번호 / 10.0)) * 10;

/*
 * Math.ceil은 소수점을 올림으로 처리하기 때문에
 * 1페이지의 경우 : Math.ceil(0.1)*10 = 10
 * 10페이지의 경우 : Math.ceil(1)*10 = 10
 * 11페이지의 경우 : Math.ceil(11)*10 = 20
 */