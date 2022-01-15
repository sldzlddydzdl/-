package com.example.mreview.controller;

import com.example.mreview.dto.ReviewDTO;
import com.example.mreview.entity.Review;
import com.example.mreview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno){
        log.info("======================= list ======================");
        log.info("MNO : " + mno);

        List<ReviewDTO> reviewDTOList = reviewService.getListOfMovie(mno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO movieReviewDTO){
        log.info("=================== add MovieReview ==================");
        log.info("reviewDTO : " + movieReviewDTO);

//        movieReviewDTO.setNickname("동현");
//        movieReviewDTO.setReviewnum(220L);
//        movieReviewDTO.setEmail("rlaehdgu1@naver.com");
//        movieReviewDTO.setRegDate(LocalDateTime.now());
//        movieReviewDTO.setModDate(LocalDateTime.now());

        Long reviewnum = reviewService.register(movieReviewDTO);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modfiyReview(@PathVariable Long reviewnum,
                                             @RequestBody ReviewDTO movieReviewDTO){
        log.info("------------------ modify MovieReview ------------------");
        log.info("reviewDTO : " + movieReviewDTO);

        reviewService.modify(movieReviewDTO);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);

    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewnum){
        log.info("------------------------ modify removeReview ------------------------");
        log.info("reviewnum : " + reviewnum);

        reviewService.remove(reviewnum);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }



}
