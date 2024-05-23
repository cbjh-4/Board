package org.zerock.b01.repository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;

import java.util.stream.LongStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void 이거입력임(){
        Long bno = 11L;
        Board board = Board.builder().bno(bno).build();
        Reply reply = Reply.builder()
                .board(board)
                .replyText("댓글...1?")
                .replyer("Replyer1")
                .build();
        replyRepository.save(reply);
    }

    @Test
    public void testInsert2(){
        LongStream.rangeClosed(1L, 10L).forEach(i->{
            Long bno = i;
            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .board(board)
                    .replyText("댓글..."+i)
                    .replyer("replyer"+i)
                    .build();
            replyRepository.save(reply);
        });
    }

    @Transactional
    @Test
    public void testBoardReplies(){
        Long bno = 10L;
        Pageable pageable = PageRequest.of(0, 10,
                Sort.by("rno").descending());
        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);
        result.getContent().forEach(reply->{
            log.info("---------------------------------------------------------");
            log.info(reply);
        });
    }

    @Test
    public void testDelete(){
        replyRepository.deleteAll();
    }
}
