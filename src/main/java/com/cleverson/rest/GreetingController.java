package com.cleverson.rest;

import com.cleverson.rest.exceptions.UnsupportMathOperationException;
import com.cleverson.rest.math.FormatNumber;
import com.cleverson.rest.math.SimpleMath;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();

    private FormatNumber formatNumber;
    private SimpleMath math;


    @RequestMapping(value = "/subtracao/{numberOne}/{numberTwo}")
    public Double sub(@PathVariable(value = "numberOne") String numberOne,
                      @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        if(!formatNumber.isNumeric(numberOne) || !formatNumber.isNumeric(numberTwo)){
            throw new UnsupportMathOperationException("Please set a numeric value!");
        }


        return formatNumber.convertToDouble(numberOne) - formatNumber.convertToDouble(numberTwo);
    }




    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable(value = "numberOne") String numberOne,
                      @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        if(!formatNumber.isNumeric(numberOne) || !formatNumber.isNumeric(numberTwo)){
            throw new UnsupportMathOperationException("Please set a numeric value!");
        }


        return formatNumber.convertToDouble(numberOne) + formatNumber.convertToDouble(numberTwo);
    }


    @RequestMapping(value = "/mult/{numberOne}/{numberTwo}")
    public Double multply(@PathVariable(value = "numberOne") String numberOne,
                      @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        if(!formatNumber.isNumeric(numberOne) || !formatNumber.isNumeric(numberTwo)){
            throw new UnsupportMathOperationException("Please set a numeric value!");
        }


        return formatNumber.convertToDouble(numberOne) * formatNumber.convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/division/{numberOne}/{numberTwo}")
    public Double division(@PathVariable(value = "numberOne") String numberOne,
                          @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        if(!formatNumber.isNumeric(numberOne) || !formatNumber.isNumeric(numberTwo)){
            throw new UnsupportMathOperationException("Please set a numeric value!");
        }


        return formatNumber.convertToDouble(numberOne) /formatNumber.convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/man/{numberOne}/{numberTwo}")
    public Double men(@PathVariable(value = "numberOne") String numberOne,
                           @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        if(!formatNumber.isNumeric(numberOne) || !formatNumber.isNumeric(numberTwo)){
            throw new UnsupportMathOperationException("Please set a numeric value!");
        }


        return math.mean(formatNumber.convertToDouble(numberOne) ,formatNumber.convertToDouble(numberTwo));
    }






}
