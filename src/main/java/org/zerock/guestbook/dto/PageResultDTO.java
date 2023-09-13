package org.zerock.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
// 페이지 결과 처리DTO
// - Page<Entity>의 엔티티 객체들을 DTO객체로 변환해서 자료구조로 담아 주어야함
// - 화면 출력에 필요한 페이지 정보들을 구성해 주어야함
public class PageResultDTO<DTO,EN> {
    private List<DTO> dtoList;
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
    }
}
