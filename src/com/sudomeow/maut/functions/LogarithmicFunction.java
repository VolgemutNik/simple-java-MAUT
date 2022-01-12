package com.sudomeow.maut.functions;

public class LogarithmicFunction implements UsefulnessFunction{

    int base;

    public LogarithmicFunction(int base){
        this.base = base;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    @Override
    public double getResult(String value) {
        double val = 0.0d;
        try{
            double foo = Double.parseDouble(value);
            if(foo < 0)
                throw new NumberFormatException("Parsed double out of bounds for value: " + value);
            val = foo;
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        //logb(n) = loge(n) / loge(b)
        return Math.log(val + 1) / Math.log(base);
    }

    @Override
    public String functionName() {
        return "Logarithmic function";
    }
}
