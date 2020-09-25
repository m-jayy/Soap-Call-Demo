package com.mohsin.soapcallnew;

public class MyModel {
    String NumberToDollarsResult;

    public String getNumberToDollarsResult() {
        return NumberToDollarsResult;
    }

    public void setNumberToDollarsResult(String numberToDollarsResult) {
        NumberToDollarsResult = numberToDollarsResult;
    }

    public String getNumberToRupeesResult() {
        return NumberToRupeesResult;
    }

    public void setNumberToRupeesResult(String numberToRupeesResult) {
        NumberToRupeesResult = numberToRupeesResult;
    }

    public String getNumberToRiyalResult() {
        return NumberToRiyalResult;
    }

    public void setNumberToRiyalResult(String numberToRiyalResult) {
        NumberToRiyalResult = numberToRiyalResult;
    }

    String NumberToRupeesResult;

    public MyModel(String numberToDollarsResult, String numberToRupeesResult, String numberToRiyalResult) {
        NumberToDollarsResult = numberToDollarsResult;
        NumberToRupeesResult = numberToRupeesResult;
        NumberToRiyalResult = numberToRiyalResult;
    }

    String NumberToRiyalResult;
}
