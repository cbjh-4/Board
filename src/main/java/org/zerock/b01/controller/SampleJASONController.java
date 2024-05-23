package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SampleJASONController {
    @GetMapping("helloJSON")
    public String[] helloJSON1234(){
        return new String[]{"aaa", "bbb", "ccc"};
    }
}
