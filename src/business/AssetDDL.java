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
    public AssetDDL(String text, double c, double s, int lf) {
        super(text, c, s, lf);
        this.rate = 2.0;
        
        if (super.IsValid()) {
            buildDep();
        }
    }

    void buildDep(double r) {
        try {
        double annualdepSL = (this.cost - this.salvage) / life;
        double rateDDL = (1.0 / this.life) * r;
        double wrkDDL = 0;
        
        begbal = new double[life];
        anndep = new double[life];
        endbal = new double[life];
        
        begbal[0] = this.cost;
        
        for (int i=0; i< this.life; i++) {
            if (i > 0) {
                begbal[i] = endbal[i-1];
            }
            
            wrkDDL = begbal[i] * rateDDL;
            if (wrkDDL < annualdepSL) {
                wrkDDL = annualdepSL;
            }
            
            anndep[i] = (begbal[i] - wrkDDL) < 
                    salvage ? begbal[i] - salvage : wrkDDL;
            
            
            endbal[i] = (begbal[i] - wrkDDL) <
                    salvage ? endbal[i] = salvage : 
                    begbal[i] - wrkDDL;
            
            
            }
        this.built = true;
        }catch(Exception e) {
            this.emsg = "Build error: " + e.getMessage();
            this.built = false;
            
        }
        
    }

    @Override
    void buildDep() {
        buildDep(rate);
    }
    
}
