package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;

//JpaRepository DB에 저장할 클래스 타입과 기본 키 타입을 제네릭으로 설정한다.
public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
    @Query(value = "select now()", nativeQuery = true)
    String getTime();
}

