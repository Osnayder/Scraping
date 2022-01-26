package edu.cecar.models;

import java.util.ArrayList;

public class Data {
    
    private ArrayList<String>ToConfCases;
    private ArrayList<String> ToConfNewCases;
    private ArrayList<String> totalDeaths;
    private ArrayList<String> totalNewDeaths;

    public Data() {}

    public ArrayList<String> getToConfCases() {
        return ToConfCases;
    }

    public void setToConfCases(ArrayList<String> ToConfCases) {
        this.ToConfCases = ToConfCases;
    }

    public ArrayList<String> getToConfNewCases() {
        return ToConfNewCases;
    }

    public void setToConfNewCases(ArrayList<String> ToConfNewCases) {
        this.ToConfNewCases = ToConfNewCases;
    }

    public ArrayList<String> getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(ArrayList<String> totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public ArrayList<String> getTotalNewDeaths() {
        return totalNewDeaths;
    }

    public void setTotalNewDeaths(ArrayList<String> totalNewDeaths) {
        this.totalNewDeaths = totalNewDeaths;
    }
    
    
    
}
