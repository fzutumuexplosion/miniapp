package com.itwu.controller;

import com.itwu.entity.R;
import com.itwu.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService  scheduleService;

    @GetMapping("{current}/{size}")
    public R getAll(@PathVariable Integer current,@PathVariable Integer size){
        return new R(true,scheduleService.getPage(current,size));
    }
}
