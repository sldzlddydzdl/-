package com.example.mreview.repository;

import com.example.mreview.entity.Member;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 다른 해결방법은 @Query 로 직접 join 을 해준다.
//    @EntityGraph(attributePaths = {"member"} , type = EntityGraph.EntityGraphType.LOAD)
    @EntityGraph(attributePaths = {"member"} , type = EntityGraph.EntityGraphType.FETCH) // FETCH => attributePaths 에 명시한 속성은 EAGER 로 처리하고 ,나머지는 LAZY   // LOAD => attributePaths 에 명시한 속성은 EAGER 로 처리하고, 나머지는 엔티티 클래승 ㅔ명시되거나 기본 방식으로 처리한다.
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("delete from Review mr where mr.member = :member")
    void deleteByMember(Member member);
}
