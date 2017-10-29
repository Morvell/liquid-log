package ru.naumen.perfhouse.controllers;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import ru.naumen.sd40.log.parser.*;

import javax.inject.Inject;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;


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
        HashMap<Long, DataSet> data = Parser.parse(parserDate.getNameForBD(),parserDate.getParserConf(),parserDate.getFilePath(),parserDate.getTimeZone());
        if (parserDate.getTraceResult()) {
            ArrayList<ActionDoneParser> date = new ArrayList<>();
            data.forEach((aLong, set) -> {
                ActionDoneParser dones = set.getActionsDone();
                dones.calculate();
                ErrorParser erros = set.getErrors();
                dones.setError(erros.getErrorCount());
                dones.setK(aLong);

                date.add(dones);
            });
            return new ModelAndView("result_parser", "date", date);
        }
        else {
            return new ModelAndView("result_parser_without_log");
        }
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
