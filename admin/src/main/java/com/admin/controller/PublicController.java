package com.admin.controller;

import com.admin.service.PublicService;
import com.common.pojo.Result;
import com.controller.controller.admin.PublicControllerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController implements PublicControllerInterface {
    @Autowired
    PublicService publicService;
    @Override
    public Result selHomeRotation(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(name = "pageSize", defaultValue = "1") Integer pageSize) {
        return publicService.selHomeRotation(pageNum, pageSize);
    }

    @Override
    public Result selAnnouncement(Integer pageNum, Integer pageSize) {
        return publicService.selAnnouncement(pageNum, pageSize);
    }
}
