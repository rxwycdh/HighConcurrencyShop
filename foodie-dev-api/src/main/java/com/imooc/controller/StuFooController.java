package com.imooc.controller;


import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StuFooController {

    @Autowired
    private StuService stuService;

    @GetMapping("/getStu")
    public Object getStu(Integer id) {
        return stuService.getStuInfo(id);
    }

    @PostMapping("/saveStu")
    public Object saveStu() {
        stuService.saveStu();
        return "OK";
    }

    @PutMapping("/updateStu")
    public Object updateStu(Integer id) {
        stuService.updateStu(id);
        return "Ok";
    }

    @DeleteMapping("/deleteStu")
    public Object deleteStu(Integer id) {
        stuService.deleteStu(id);
        return "OK";
    }

}
