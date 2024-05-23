package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.repository.BoardRepository;

@SpringBootTest
@Log4j2
public class BoardServiceTests{
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testRegister() {
        BoardDTO boardDTO = BoardDTO.builder()
                .title("boardDTO test...")
                .content("boardDTO test...")
                .writer("user00")
                .build();
        Long bno = boardService.register(boardDTO);
        log.info("bno: "+bno);
    }

    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(99L)
                .title("updated good!.................")
                .content("updated good!.................")
                .build();
        boardService.modify(boardDTO);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("3")
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        for(BoardDTO element : responseDTO.getDtoList()){
            log.info(element.toString());
        }
    }
}
