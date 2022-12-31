package com.example.board.scheduler;

import com.example.board.domain.Post;
import com.example.board.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Slf4j
@Component
public class PostSchedule {
    private final PostRepository repository;
    public PostSchedule(PostRepository repository) {
        this.repository = repository;
    }
    @Scheduled(cron =  "0 0/1 * * * *")
    public void postSchedule(){
        //schedule걸린건만 조회
        String scheduled = "checked";
        for (Post post : repository.findByScheduled(scheduled)) {
//            1초 차이 용인
            if(post.getScheduledTime().isBefore(LocalDateTime.now().plusSeconds(1))){
                post.setScheduled(null);
                repository.save(post);
            }
        }


    }
}
