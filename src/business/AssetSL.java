/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author oSlash
 */
public class AssetSL extends Asset {
    
    private boolean built;
    private double[] begbal, endbal;
    private double anndep;
    
    
    public AssetSL(String name, double cost, double salvage, int life) {
        super(name, cost, salvage, life);
        
        
        if (IsValid()) {
        } else {
            buildDep();
        }
    }
    void buildDep() {
        anndep = (getCost() - getSalvage()) / getLife();
        try {
            begbal = new double[getLife()];
            endbal = new double[getLife()];
            
            
            begbal[0] = this.getCost();
            
            for (int i=0; i < getLife(); i++) {
            if (i > 0) {
                begbal[i] = endbal[i-1];
            }
            endbal[i] = begbal[i] - anndep;
            }
            built = true;
            
        }
        catch(Exception e) {
            setErrorMsg("Build error: " + e.getMessage());
            built = false;
            
        }
    }
    public double getAnnDep() {
        if (!built) {
            buildDep();
            if (!built) {
                return -1;
            }
        }
        return anndep;
    }
    
    public double GetBegBalance(int year) {
        if (!built) {
            buildDep();
            if (!built) {
                return -1;
            }
        }
        if (year < 1 || year > getLife()) {
            return -1;
        }
        return begbal[year -1];
    }
    public double GetEndBalance(int year) {
        if (!built) {
            buildDep();
            if (!built) {
                return -1;
            }
        }
        if (year < 1 || year > getLife()) {
            return -1;
        }
        return endbal[year-1];
    }
}
