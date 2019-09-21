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
public class AssetDDL extends Asset {

    private final double rate;
    private double[] begbal, endbal, anndep;
    private boolean built;
    
    public AssetDDL(String text, double c, double s, int lf) {
        super(text, c, s, lf);
        this.rate = 2.0;
        
        if (super.IsValid()) {
            buildDep(rate);
        }
    }

    void buildDep(double r) {
        try {
        double annualdepSL = (getCost() - getSalvage()) / getLife();
        double rateDDL = (1.0 / this.getLife()) * r;
        double wrkDDL = 0;
        
        begbal = new double[getLife()];
        anndep = new double[getLife()];
        endbal = new double[getLife()];
        
        begbal[0] = getCost();
        
        for (int i=0; i< getLife(); i++) {
            if (i > 0) {
                begbal[i] = endbal[i-1];
            }
            
            wrkDDL = begbal[i] * rateDDL;
            if (wrkDDL < annualdepSL) {
                wrkDDL = annualdepSL;
            }
            
            anndep[i] = (begbal[i] - wrkDDL) < 
                    getSalvage() ? begbal[i] - getSalvage() : wrkDDL;
            
            
            endbal[i] = (begbal[i] - wrkDDL) <
                    getSalvage() ? endbal[i] = getSalvage() : 
                    begbal[i] - wrkDDL;
            
            
            }
        this.built = true;
        }catch(Exception e) {
            setErrorMsg("Build error: " + e.getMessage());
            this.built = false;
            
        }
        
    }

    public double getAnnDep(int year) {
        if (!built) {
            buildDep(rate);
            if (!built) {
                return -1;
            }
        }
        if (year < 1 || year > getLife()) {
            return -1;
        }
        
        return anndep[year - 1];
    }
    
    public double GetBegBalance(int year) {
        if (!built) {
            buildDep(rate);
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
            buildDep(rate);
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
