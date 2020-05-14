package com.codeup.mockprep.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AjaxController {

    @ResponseBody
    @RequestMapping(value = "/addsomedata", method = RequestMethod.POST)
    public void getSearchResultViaAjax(
            @RequestParam int question_id,
            @RequestParam int timeInSecs) {

        System.out.println("QuestionID " + question_id);
        System.out.println("TimeInSeconds " + timeInSecs);

    }

}