package com.example.mreview.repository;

import com.example.mreview.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(" select m , mi ,avg(coalesce(r.grade,0) ) , count(distinct r) from Movie m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m group by m " )
//    @Query("select m , i , count(r) from Movie m left join MovieImage i on i.movie = m  and i.inum = (select max(i2.inum) from MovieImage i2 where i2.movie = m) left outer join Review r on r.movie = m group by m ")
    // 위의 쿼리문은 N+1 이슈를 해결하긴 했지만 성능을 최대로 좋게 고친 건 아니다.
    Page<Object[]> getListPage(Pageable pageable); // 페이지 처리

    @Query(
            "select m , mi, avg(coalesce(r.grade, 0)) , count(r)  " +
                    "from Movie m left outer join MovieImage mi on mi.movie = m left outer join Review r on r.movie = m where m.mno = :mno group by mi")
//    @Query("select m , mi, avg(coalesce(r.grade, 0)) , count(r)  from Movie m inner join MovieImage mi on mi.movie = m inner join Review r on r.movie = m where m.mno = :mno group by mi")
    List<Object[]> getMovieWithAll(Long mno); // 특정 영화 조회




    /*

        동적으로 처리해야하는 JPQL Query 적용 방법
        QMovie movie = QMovie.movie;
        QReview review = QReview.review;
        QMovieImage movieImage = QMovieImage.movieImage;

        JPQLQuery<Movie> jpqlQuery = from(movie);
        JPQLQuery.leftJoin(movieImage).on(movieImage.movie.eq(movie));
        JPQLQuery.leftJoin(review).on(review.movie.eq(movie));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(movie , movieImage, review.grade.avg(), review.countDistinct());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = movie.mno.gt(0L);

     */


//            [Movie(mno=100, title=Movie......100), MovieImage(inum=281, uuid=de2be734-a548-4ea1-8711-e4a46c7dda8c, imgName=test1.jpg, path=null), 1.6667, 3]
//            [Movie(mno=99, title=Movie......99), MovieImage(inum=279, uuid=f93a7697-675e-4e09-b81f-16287b266e34, imgName=test2.jpg, path=null), 5.0, 2]
//            [Movie(mno=98, title=Movie......98), MovieImage(inum=276, uuid=91c29a64-3237-4dfa-b426-305a9dfac750, imgName=test1.jpg, path=null), 2.0, 1]
//            [Movie(mno=97, title=Movie......97), MovieImage(inum=274, uuid=96f6619c-1d1e-4ea2-ab20-d60907695f84, imgName=test4.jpg, path=null), 2.3333, 3]
//            [Movie(mno=96, title=Movie......96), MovieImage(inum=269, uuid=04680e65-e4fe-416a-b60d-3f37987e1232, imgName=test0.jpg, path=null), 2.2, 5]
//            [Movie(mno=95, title=Movie......95), MovieImage(inum=268, uuid=57c5eb98-037a-431c-b722-40cac62871fb, imgName=test2.jpg, path=null), 4.25, 4]
//            [Movie(mno=94, title=Movie......94), MovieImage(inum=265, uuid=e69c92ae-5e17-4e23-ad86-7272035cad5e, imgName=test2.jpg, path=null), 3.5, 2]
//            [Movie(mno=93, title=Movie......93), MovieImage(inum=262, uuid=306d671e-61ec-451c-97e9-5d57a713c1e8, imgName=test0.jpg, path=null), 2.0, 3]
//            [Movie(mno=92, title=Movie......92), MovieImage(inum=261, uuid=6a11b6c6-2454-4923-a4e6-15734dddca41, imgName=test4.jpg, path=null), 3.0, 1]
//            [Movie(mno=91, title=Movie......91), MovieImage(inum=256, uuid=97438b08-db85-4973-9c37-5680cd571f7e, imgName=test3.jpg, path=null), 0.0, 0]

}
