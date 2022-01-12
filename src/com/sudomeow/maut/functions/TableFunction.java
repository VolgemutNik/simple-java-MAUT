package com.sudomeow.maut.functions;


import java.util.HashMap;

/**
 * TODO: dokoncaj implementacijo Tabular funkcije
 *
 * STRING | VREDNOST
 * -----------------
 * String1 | Vrednost1
 * String2 | Vrednost2
 *        ...
 * StringN | VrednostN
 */
public class TableFunction implements UsefulnessFunction {

    private HashMap<String, Double> valueMapping = new HashMap<>();

    @Override
    public double getResult(String value) {
        Double val = valueMapping.get(value);
        return val == null ? 0.0d : val;
    }

    public void map(String key, Double value){
        if(value > 1.0d)
            throw new NumberFormatException();
        valueMapping.put(key, value);
    }

    @Override
    public String functionName() {
        return "Tabular Function";
    }

}
