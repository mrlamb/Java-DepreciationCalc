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
public class AssetDL15 extends AssetDL
{
    private final double rate;

    public AssetDL15(String text, double c, double s, int lf) {
        super(text, c, s, lf);
        this.rate = 1.5;
        if (super.IsValid()) {
            super.buildDep(rate);
        }
    }    
}
