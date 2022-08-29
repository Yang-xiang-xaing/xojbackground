package indi.yuluo.xojbackgroundmanagmentsystem.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  17:30
 * @Description: TODO
 */

@Slf4j
@Transactional
@RestController
@RequestMapping(value = "/xoj/problem")
@Api(value = "题目信息控制器")
public class ProblemController {
}
