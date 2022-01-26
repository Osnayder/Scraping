package edu.cecar.models;

import java.util.ArrayList;

public class Hoja {
    
    private ArrayList<String>ToConfCases;
    private ArrayList<String> ToConfNewCases;
    private ArrayList<String> totalDeaths;
    private ArrayList<String> totalNewDeaths;

    public Hoja() {}
    
    public Hoja(ArrayList<String> ToConfCases, ArrayList<String> ToConfNewCases, ArrayList<String> totalDeaths, ArrayList<String> totalNewDeaths) {
        this.ToConfCases = ToConfCases;
        this.ToConfNewCases = ToConfNewCases;
        this.totalDeaths = totalDeaths;
        this.totalNewDeaths = totalNewDeaths;
    }

    public ArrayList<String> getToConfCases() {
        return ToConfCases;
    }

    public void setToConfCases(String ToConfCases) {
        if(this.ToConfCases==null){
            this.ToConfCases = new ArrayList<>();
        }
        this.ToConfCases.add(ToConfCases);
    }

    public ArrayList<String> getToConfNewCases() {
        return ToConfNewCases;
    }

    public void setToConfNewCases(String ToConfNewCases) {
        if(this.ToConfNewCases==null){
            this.ToConfNewCases = new ArrayList<>();
        }
        this.ToConfNewCases.add(ToConfNewCases);
    }

    public ArrayList<String> getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        if(this.totalDeaths==null){
            this.totalDeaths = new ArrayList<>();
        }
        this.totalDeaths.add(totalDeaths);
    }

    public ArrayList<String> getTotalNewDeaths() {
        return totalNewDeaths;
    }

    public void setTotalNewDeaths(String totalNewDeaths) {
        if(this.totalNewDeaths==null){
            this.totalNewDeaths = new ArrayList<>();
        }
        this.totalNewDeaths.add(totalNewDeaths);
    }
    
    
}
