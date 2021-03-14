package com.srv.web.main.Controller;

import com.srv.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class MainController {
    @RequestMapping(value="/")
    public String goMain(HttpServletRequest request, Model model){

        String sDate = StringUtils.isEmpty(request.getParameter("sDate"))?DateUtils.getAddMonth(DateUtils.getDate(), -1):request.getParameter("sDate");
        String eDate = StringUtils.isEmpty(request.getParameter("eDate"))?DateUtils.getDate():request.getParameter("eDate");


        log.info("param : " + sDate +"::" + eDate);

        model.addAttribute("sDate", sDate);
        model.addAttribute("eDate", eDate);



        return "index";
    }


    @RequestMapping(value="/chart.html")
    public String goChart(HttpServletRequest request, Model model){

        String param = request.getParameter("param");
        String type = request.getParameter("type");

        String sDate = request.getParameter("sDate");
        String eDate = request.getParameter("eDate");

        log.info("param : " + param +"::" + type);

        model.addAttribute("sDate", sDate);
        model.addAttribute("eDate", eDate);
        model.addAttribute("serverType", type);
        model.addAttribute("serverParam", param);
        return "chart";
    }
}
