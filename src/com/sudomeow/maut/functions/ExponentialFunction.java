package com.sudomeow.maut.functions;

public class ExponentialFunction implements UsefulnessFunction {

    int power;
    double max;

    public ExponentialFunction(int power, double max) {
        this.power = power;
        this.max = max;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
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

        return k * Math.pow(val, power);
    }

    @Override
    public String functionName() {
        return "Exponential Function";
    }
}
