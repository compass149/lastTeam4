package com.projectdemo1.repository;

import com.projectdemo1.domain.Board;
import com.projectdemo1.domain.User;
import com.projectdemo1.dto.BoardListReplyCountDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testInsert() {
        // UserRepository가 필요하다고 가정합니다.
        IntStream.rangeClosed(1, 100).forEach(i -> {
            // userId 또는 user 객체를 데이터베이스에서 가져온다고 가정
            Long userId = (long) (i % 10 + 1); // userId를 1~10 사이로 설정
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                Board board = Board.builder()
                        .title("title..." + i)
                        .content("content..." + i)
                        .user(user) // User 객체를 설정
                        .build();

                Board result = boardRepository.save(board);
                log.info("BNO: " + result.getBno());
            } else {
                log.warn("User with ID " + userId + " not found.");
            }
        });
    }


    @Test
    public void testSelect() {
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info(board);

    }

    @Test
    public void testUpdate() {

        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        board.change("update..title 100", "update content 100");

        boardRepository.save(board);

    }

    @Test
    public void testDelete() {
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging() {

        //1 page order by bno desc
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);


        log.info("total count: "+result.getTotalElements());
        log.info( "total pages:" +result.getTotalPages());
        log.info("page number: "+result.getNumber());
        log.info("page size: "+result.getSize());

        List<Board> todoList = result.getContent();

        todoList.forEach(board -> log.info(board));


    }

    @Test
    public void testSearch1() {

        //2 page order by bno desc
        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());

        boardRepository.search1(pageable);

    }

    @Test
    public void testSearchAll() {

        String[] types = {"t","c","w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());



    }

    @Test
    public void testSearchAll2() {

        String[] types = {"t","c","w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

    }

    @Test
    public void testBoardReplyCount() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<BoardListReplyCountDTO> result =  boardRepository.searchWithReplyCount(types, keyword, pageable);

        //total pages
        log.info(result.getTotalPages());
        //page size
        log.info(result.getSize());
        //page number
        log.info(result.getNumber());
        //prev next
        log.info(result.hasPrevious() +": " +result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

}
