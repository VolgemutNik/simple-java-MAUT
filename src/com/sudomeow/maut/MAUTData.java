package com.sudomeow.maut;

import com.sudomeow.maut.functions.UsefulnessFunction;

import java.util.HashMap;

public class MAUTData {

    /* <Ime alternative, Vrednost dolocena s strani uporabnika> */
    private HashMap<String, String> mappedValues = new HashMap<>();

    private String nodeName;
    private Double weight;
    private UsefulnessFunction usefulnessFunction;

    public void setNodeName(String nodeName){
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public double getWeight(){
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public boolean isWeightSet(){
        return weight != null;
    }

    public UsefulnessFunction getUsefulnessFunction() {
        return usefulnessFunction;
    }

    public void setUsefulnessFunction(UsefulnessFunction usefulnessFunction) {
        this.usefulnessFunction = usefulnessFunction;
    }

    public HashMap<String, String> getMappedValues() {
        return mappedValues;
    }

    @Override
    public String toString() {
        return this.getNodeName();
    }
}
