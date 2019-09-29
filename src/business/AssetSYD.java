/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import business.Asset;

/**
 *
 * @author oSlash
 */
public class AssetSYD extends Asset {
    private double[] begbal, endbal, anndep, annrate;
    private boolean built;
    private int SYD;

    public AssetSYD(String text, double c, double s, int lf) {
        super(text, c, s, lf);
        if (super.IsValid()) {
            this.buildDep();
        }
    }

    @Override
    public double getAnnDep(int year) {
        if(!this.built) {
            buildDep();
            if (!this.built) {
                return -1;
            }
        }
        return anndep[year - 1];
    }

    @Override
    public double GetBegBalance(int year) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double GetEndBalance(int year) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void buildDep() {
        try {
        begbal = new double[getLife()];
        anndep = new double[getLife()];
        endbal = new double[getLife()];
        annrate = new double[getLife()];
        begbal[0] = getCost() - getSalvage();
        
        SYD = this.getLife() * (this.getLife() + 1) / 2;
                
        for (int i=0; i< getLife(); i++) {
            if (i > 0) {
                begbal[i] = endbal[i-1];
            }
            annrate[i] = (this.getLife() - i) / (double)getSYD();
            anndep[i] = begbal[0] * annrate[i];
            endbal[i] = begbal[i] - anndep[i];
        }
        this.built = true;
        }catch(Exception e) {
            setErrorMsg("Build error: " + e.getMessage());
            this.built = false;
            
        }
    }

    /**
     * @return the SYD
     */
    public int getSYD() {
        return SYD;
    }

    public double getAnnDepRate(int year) {
         if(!this.built) {
            buildDep();
            if (!this.built) {
                return -1;
            }
        }
        return annrate[year - 1];
    }
    
}
