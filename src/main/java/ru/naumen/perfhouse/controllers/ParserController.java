package ru.naumen.perfhouse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.naumen.sd40.log.parser.ParserDate;


@Controller
public class ParserController {

    @RequestMapping(value = "/parser", method = RequestMethod.GET)
    public ModelAndView index()
    {
        return new ModelAndView("parser", "parserDate", new ParserDate());
    }

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public ModelAndView parserResult(@ModelAttribute("parserDate") ParserDate parserDate)
    {
        return new ModelAndView("result_parser", "parserDate", parserDate);
    }


}
