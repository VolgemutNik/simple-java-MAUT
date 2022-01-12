package com.sudomeow.maut.functions;

public class FallingExponentialFunction extends ExponentialFunction{

    public FallingExponentialFunction(int power, double max){
        super(power, max);
    }

    @Override
    public double getResult(String value) {
        double val = 0.0d;
        try{
            double foo = Double.parseDouble(value);
            if(foo < 0 || foo > max){
                throw new NumberFormatException("Parsed double out of bounds for value: " + value);
            }
            val = foo;
        }catch(NumberFormatException e){
            e.printStackTrace();
        }

        double k = 1 / Math.pow(max, power);

        return 1 - (k * Math.pow(val, power));
    }

    @Override
    public String functionName() {
        return "Falling Exponential Function";
    }
}
