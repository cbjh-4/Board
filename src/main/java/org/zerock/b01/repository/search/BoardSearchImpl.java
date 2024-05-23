package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;
import org.zerock.b01.domain.QReply;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{
    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable){
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);
        query.where(board.title.contains("3"));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();
        long count = query.fetchCount();

        //페이징을 위한 page객체
        return new PageImpl<>(list, pageable, count);
        //return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable){
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        if((types != null && types.length>0) && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type: types){
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(board.bno.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);
        List<Board> list = query.fetch();
        Long count = query.fetchCount();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount
            (String[] types, String keyword, Pageable pageable){
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));    //Spring에선 join으로 테이블을 연결하는 것을 지원한다.
        query.groupBy(board.bno);

        if((types != null && types.length>0) && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type: types){
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(board.bno.gt(0L));

        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections
                .bean(BoardListReplyCountDTO.class,                             //Projections.bean은 select할 때, BoardListReplyCountDTO 모양을 해주겠다는 설정이다.

                        board.bno,
                        board.title,
                        board.writer,
                        board.regDate,
                        reply.count().as("replyCount")));

        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();
        Long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }
}

