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
public class AssetDDL extends AssetDL
{
    private final double rate;

    public AssetDDL(String text, double c, double s, int lf) {
        super(text, c, s, lf);
        this.rate = 2.0;
        if (super.IsValid()) {
            super.buildDep(rate);
        }
    }    
}
