package ru.naumen.perfhouse.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import ru.naumen.sd40.log.parser.Parser;
import ru.naumen.sd40.log.parser.ParserDate;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;


@Controller
public class ParserController {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return new CommonsMultipartResolver();
    }

    @RequestMapping(value = "/parser", method = RequestMethod.GET)
    public ModelAndView index()
    {
        return new ModelAndView("parser", "parserDate", new ParserDate());
    }

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public ModelAndView parserResult(@ModelAttribute("parserDate") ParserDate parserDate) throws IOException, ParseException {
        Parser.parse(parserDate.getNameForBD(),parserDate.getParserConf(),uploadFile(parserDate.getFilePath()),parserDate.getTimeZone());
        return new ModelAndView("result_parser", "parserDate", parserDate);
    }

    private File uploadFile(MultipartFile file) {
        String name = null;

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                name = file.getOriginalFilename();

                File uploadedFile = File.createTempFile(name,"temp");

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.flush();
                stream.close();
                return uploadedFile;
            } catch (Exception e) {
            }
        }
        return null;
    }



}
