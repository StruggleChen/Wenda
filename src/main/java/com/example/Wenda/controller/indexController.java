package com.example.Wenda.controller;

import com.example.Wenda.aspect.LogAspect;
import com.example.Wenda.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.util.*;

@Controller
public class indexController {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @RequestMapping(path = {"/Wenda/","/Wenda/index"}, method = {RequestMethod.POST})
    @ResponseBody
    public String index(){
        return "hello world";
    }

    @RequestMapping(path = {"/Wenda/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "zz") String key)
    {
        return String.format(("Profile page of user %s / %d, type : %d key : %s"),groupId, userId,type, key);
    }



    @RequestMapping(path = {"/Wenda/request"}, method = {RequestMethod.GET})
    @ResponseBody
    public String hello(Model model, HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession session
                        ){
        logger.info("visiti request");

        StringBuffer sb = new StringBuffer();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name + " : " + request.getHeader(name) + "<br>");
        }
        sb.append(request.getMethod() + "<br>");
        sb.append("hello" + session.getAttribute("msg"));
        return sb.toString();
    }

    @RequestMapping(path = {"/Wenda/redirect/{code}"}, method = {RequestMethod.GET})
    public RedirectView redirect(Model model, HttpServletRequest request,
                                 HttpServletResponse response,
                                 @PathVariable("code") int code,
                                 HttpSession session){
        logger.info("visiti redirect");
        RedirectView red = new RedirectView("/Wenda/request");
        if(code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        session.setAttribute("msg", "this is from redirect");
        return red;
    }

    @RequestMapping(path = {"/Wenda/admin"})
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if(key.equals("admin")){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error:" + e.getMessage();
    }

}
