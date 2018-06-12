package cn.sowell.datacenter.admin.controller.node.api;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sowell.datacenter.admin.controller.AdminConstants;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-11T07:53:08.778Z")


public interface BasicItemNodeApi {

    
    ResponseEntity<BasicItems> entityList();

}
