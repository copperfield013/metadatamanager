package cn.sowell.datacenter.admin.controller.node;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/test")
public class UserController {
	
    @ApiOperation(value="一个测试API",notes = "第一个测试api")
    @ResponseBody
    @RequestMapping(value="/hello",method = RequestMethod.POST)
    public String hello()
    {
        return "hello";
    }
}