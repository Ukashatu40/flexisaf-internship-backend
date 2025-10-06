package dev.ukasha.spring_tok.controller;

import dev.ukasha.spring_tok.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {

    @Autowired
    private VideoRepository repository;

//    @Autowired
//    public VideoController(VideoRepository repository) {
//        this.repository = repository;
//    }

    @Autowired
    public void setVideoRepository(VideoRepository repository){
        this.repository = repository;
    }

    public String next(){
        return "Next Video";
    }
}
