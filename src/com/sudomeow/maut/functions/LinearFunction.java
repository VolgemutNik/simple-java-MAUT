package com.sudomeow.maut.functions;

import java.io.DataOutputStream;

public class LinearFunction implements UsefulnessFunction {

    double min;
    double max;

    public LinearFunction(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }


    @Override
    public double getResult(String value) {
        double val = 0.0d;
        try{
            double foo = Double.parseDouble(value);
            if(foo < min || foo > max)
                throw new NumberFormatException("Double out of bounds for value: " );
            val = foo;
        }catch(NumberFormatException e){
            e.printStackTrace();
        }

        double angle = (1) / (max - min);

        return angle * (val - min);
    }

    @Override
    public String functionName() {
        return "Linear Function";
    }
}
