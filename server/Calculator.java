package server;

import java.util.DoubleSummaryStatistics;

import static java.lang.Integer.parseInt;

/**
 * Created by Morthimer on 3/19/2016.
 */
public class Calculator {

    public Calculator() {
    }

    private String calculate(String a, String b, String operator){

        if(!validateNum(a) || !validateNum(b)){
            return "Incorrect number!";
        }
        String result;
        switch (operator) {
            case "add":
                result = (Double.parseDouble(a) + Double.parseDouble(b)) + "";
                break;
            case "sub":
                result = (Double.parseDouble(a) - Double.parseDouble(b)) + "";
                break;
            case "mul":
                result = (Double.parseDouble(a) * Double.parseDouble(b)) + "";
                break;
            case "div":
                result = (Double.parseDouble(a) / Double.parseDouble(b)) + "";
                break;
            default:
                return "incorrect operation";
        }
        return result;
        }

    public String parseRequestt(String request){
       // calculate?operation=add&operand1=5&operand2=10
        String operation = request.substring(request.indexOf("?")).split("&")[0].split("=")[1];
        String a = request.substring(request.indexOf("?")).split("&")[1].split("=")[1];
        String b = request.substring(request.indexOf("?")).split("&")[2].split("=")[1];
        return calculate(a, b, operation);
    }

    private boolean validateNum(String num){
        try {
            Double.parseDouble(num);
        }catch(NumberFormatException ex){
            return false;
        }
        return true;
    }
}
