package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.MemberDTO;
import org.zerock.b01.dto.SampleDTO01;
import org.zerock.b01.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
public class SampleController {

    class SampleDTO{
        private String p1, p2, p3;
        public String getP1(){return p1;}
        public String getP2(){return p2;}
        public String getP3(){return p3;}
    }

    @GetMapping("/ex/ex2")
    public void ex2(Model model){
        List<String> strList = IntStream.range(1,10).mapToObj(i->"STR"+i).collect(Collectors.toList());
        model.addAttribute("strList", strList);

        Map<String, String> map = new HashMap<>();
        map.put("A", "AAAAAA");
        map.put("B", "BBBBBB");
        model.addAttribute("map", map);

        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.p1 = "p1p1p1p1p1p1";
        sampleDTO.p2 = "p2p2p2p2p2p2p2p2p22";
        sampleDTO.p3 = "p3p3p3p3p3p3p3";
        model.addAttribute("sampleDTO", sampleDTO);
    }

    @GetMapping("/hello")
    public void hello(Model model){
        log.info("hello...........");
        model.addAttribute("msg", "Hello World");
    }

    @GetMapping("/ex/ex1")
    public void ex1(Model model){
        List<String> list = Arrays.asList("111","CCC", "dd");
        model.addAttribute("list", list);
    }

    @GetMapping("/ex/ex01")
    public void ex01(Model model){
        List<SampleDTO01> DTOList = IntStream.rangeClosed(1,40).asLongStream().mapToObj(i->{
            SampleDTO01 dto = SampleDTO01.builder()
                    .id(i)
                    .name("사용자"+i)
                    .contents("Content......" + i)
                    .regTime(LocalDateTime.now())
                    .build();
            return dto;
        }).collect(Collectors.toList());
        System.out.println(DTOList);
        model.addAttribute("DTOList", DTOList);
    }

    @GetMapping("/layout/layout1")
    public void layout1(Model model){}

    @GetMapping("/ex/ex3")
    public void ex3(Model model){
        model.addAttribute("arr", new String[]{"aaa","bbb","ccc"});
    }

    @GetMapping("/memInsert")
    public void memInsert(Model model){}

    @PostMapping("/memInsert")
    public String memInsert(@ModelAttribute MemberDTO memberDTO, Model model){
        String info = memberDTO.getName() +", " + memberDTO.getEmail() + ", " + memberDTO.getAge();
        model.addAttribute("info", info);
        return "result";
    }

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/ex/ex02")
    public String list(@RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "10") int size,
                       Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("bno").descending());
        Page<Board> boardPage = boardRepository.findAll(pageable);

        model.addAttribute("boards", boardPage.getContent());
        model.addAttribute("totalPages", boardPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "ex/ex02";
    }
}
