package org.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService{

    //반드시 final로 선언
    private final GuestbookRepository repository;

    //파라미터를 DTO 타입으로 받기 때문에 이를 JPA로 처리하기 위해서
    // entity타입의 객체로 변환해야하는 작업이 반드시 필요
    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO------------------------------");
        log.info(dto);
        Guestbook entity = dtoToEntity(dto);
        log.info(entity);

        repository.save(entity);
        System.out.println(entity.getGno());
        return entity.getGno();
    }

    @Override
    public GuestbookDTO read(Long gno) {
        // GuestbookRespository에서 findById()를 통해서
        // 만일 엔티티 객체를 가져왔다면
        // entityToDto()를 이용해서 엔티티 객체를 DTO를 변환해서 반환한다
        Optional<Guestbook> result = repository.findById(gno);
        return result.isPresent()? entityToDto(result.get()):null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        // 업데이트 하는 항목은 '제목','내용'
        Optional<Guestbook> result = repository.findById(dto.getGno());
        if(result.isPresent()){
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }


    //     PageResultDTO에는 JPA의 처리 결과인 Page<Entity>와 Function을 전달해서 엔티티객체들을
//    DTO의 리스트로 변환하고 화면에 페이지 처리와 필요한 값 생성
    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색 조건 처리

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result,fn);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){ // querydsl 처리
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qGuestbook.gno.gt(0L); // gno > 0 조건만 생성
        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0){ // 검색 조건이 없는 경우
            return booleanBuilder;
        }
        // 검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        // 모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
