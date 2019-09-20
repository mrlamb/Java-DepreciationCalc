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
    
    public AssetSL(String name, double cost, double salvage, int life) {
        super(name, cost, salvage, life);
        
        
        if (super.IsValid()) {
            buildDep();
        }
    }


    @Override
    void buildDep() {
        double annualdepSL = (cost - salvage) / life;
        try {
            begbal = new double[life];
            endbal = new double[life];
            anndep = new double[life];
            
            begbal[0] = this.getCost();
            
            for (int i=0; i < life; i++) {
            if (i > 0) {
                begbal[i] = endbal[i-1];
            }
            anndep[i] = annualdepSL;
            endbal[i] = begbal[i] - anndep[i];
            }
            built = true;
            
        }
        catch(Exception e) {
            emsg = "Build error: " + e.getMessage();
            built = false;
            
        }
    }
}
