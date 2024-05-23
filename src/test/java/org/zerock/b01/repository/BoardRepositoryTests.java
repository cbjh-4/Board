package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardListReplyCountDTO;
import org.zerock.b01.repository.search.BoardSearchImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1,100).forEach(i->{
            Board board = Board.builder()
                    .title("title...."+i)
                    .content("content..."+i)
                    .writer("writer"+(i%10))
                    .build();
            Board result = boardRepository.save(board);        //boardRepository를 통해 board 객체를 DB에 insert하겠다. 데이터가 이미 있으면 update한다.
            log.info("BNO: " + result.getBno());
        });
    }

    @Test
    public void testSelect() {
        Long bno = 210L; //몇번 할까요?
        Optional<Board> result = boardRepository.findById(bno); //Optional 컬렉션은 알아서 Exception 처리를 해줌. Board가 있으면 저장해서 반환함.
//        Board board = result.orElseThrow(); //있으면 주고 없으면 말고
        if (result.isPresent()) {
            Board board = result.get();
            log.info("--------------------------------------------------------");
            log.info(board);
        } else {
            log.info("--------------------------------------------------------");
            log.info("No Board found with bno: " + bno);
        }
    }

    @Test
    public void testUpdate() {
        Long bno = 10L;
        Optional<Board> result = boardRepository.findById(bno);
        if (result.isPresent()) {
            Board board = result.orElseThrow();
            board.change("updateed... title", "updateed... content");
            boardRepository.save(board);
            log.info("---------------------------------------------------------");
            log.info(board);
        } else {
            log.info("No Board found with bno: " + bno);
        }
    }

    @Test
    public void testDelete() {
        Long bno = 10L;
        Optional<Board> result = boardRepository.findById(bno);
        if (result.isPresent()) {
            Board board = result.get();
            boardRepository.delete(board);
            log.info("---------------------------------------------------------");
            log.info(board);
        }else{
            log.info("No Board found with bno: " + bno);
        }
    }
    @Test
    public void testPaging(){
        //1 page. order by bno desc
        Pageable pageable
                = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);
        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("page size: " + result.getSize());

        for (Board board : result.getContent()) {
            log.info(board);
        }

        List<Board> list = result.getContent();
        list.forEach(board -> log.info(board));
    }

    @Test
    public void testSearch1(){
        Pageable pageable
                = PageRequest.of(1, 10, Sort.by("bno").descending());
        boardRepository.search1(pageable);
    }

    @Test
    public void testSearchAll(){
        String[] types = {"t", "c", "w"};
        String keyword = "3";
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);
        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("page size: " + result.getSize());
        log.info("result.hasPrevious()+\"__\"+result.hasNext(): "+ result.hasPrevious()+"__"+result.hasNext());//이전 페이지가 있냐?__다음 페이지가 있냐?

        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testSearchWithCount(){
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());
        String types[] = {"t","c","w"};
        String keyword = "1";
        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);
        result.getContent().forEach(resultElement -> log.info(resultElement));
    }
}
