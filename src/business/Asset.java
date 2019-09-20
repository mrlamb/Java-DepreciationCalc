/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author oSlash
 */
public class Asset {
    
    private String name, emsg;
    private double cost, salvage;
    private int life;
    private boolean built;
    private double[][] begbal, anndep, endbal;
    private static final int SL = 0, DDL = 1;
    
    public Asset() {
        this.name = "";
        this.cost = 0;
        this.salvage = 0;
        this.life = 0;
        this.built = false;
    }
    
    
    public Asset(String name, double cost, double salvage, int life) {
        this.name = name;
        this.cost = cost;
        this.salvage = salvage;
        this.life = life;
        
        if (IsValid()) {
            buildDep();
        }
        
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getSalvage() {
        return salvage;
    }
    
    public void setSalvage(double salvage) {
        this.salvage = salvage;
    }

    public int getLife() {
        return life;
    }
    
    public void setLife(int life) {
        this.life = life;
    }
    
    

    private boolean IsValid() {
        this.emsg = "";
        if (this.name.trim().isEmpty()) {
            this.emsg += "Asset name is missing. ";
        }
        if (this.cost <= 0) {
            this.emsg += "Cost must be > 0. ";
        }
        if (this.salvage < 0) {
            this.emsg += "Salvage must be at least 0. ";
        }
        if (this.life <=0) {
            this.emsg += "Life must be > 0. ";
        }
        if (this.salvage >= this.cost) {
            this.emsg += "Salvage must be < cost. ";
        }
        
        return this.emsg.isEmpty();
        
        
    }

    private void buildDep() {
        try {
        double annualdepSL = (this.cost - this.salvage) / life;
        double rateDDL = (1.0 / this.life) * 2.0;
        double wrkDDL = 0;
        
        begbal = new double[this.life][2];
        anndep = new double[this.life][2];
        endbal = new double[this.life][2];
        
        begbal[0][SL] = this.cost;
        begbal[0][DDL] = this.cost;
        
        for (int i=0; i< this.life; i++) {
            if (i > 0) {
                begbal[i][SL] = endbal[i-1][SL];
                begbal[i][DDL] = endbal[i-1][DDL];
            }
            
            wrkDDL = begbal[i][DDL] * rateDDL;
            if (wrkDDL < annualdepSL) {
                wrkDDL = annualdepSL;
            }
            
            //I like ternary, do you?
            anndep[i][SL] = annualdepSL;
            anndep[i][DDL] = (begbal[i][DDL] - wrkDDL) < 
                    salvage ? begbal[i][DDL] - salvage : wrkDDL;
            
            
            endbal[i][SL] = begbal[i][SL] - anndep[i][SL];
            endbal[i][DDL] = (begbal[i][DDL] - wrkDDL) <
                    salvage ? endbal[i][DDL] = salvage : 
                    begbal[i][DDL] - wrkDDL;
            
            
            }
        
        
        
        this.built = true;
        }catch(Exception e) {
            this.emsg = "Build error: " + e.getMessage();
            this.built = false;
            
        }
        
    }
    
    public String getErrorMsg() {
        return this.emsg;
    }
    
    public double AnnualDepreciation() {
        if (!this.built) {
            buildDep();
            if (!this.built) {
                return -1;
            }
        }
        return anndep[0][SL];
    }
    
    
    public double AnnualDepreciation(int year) {
        if (!this.built) { 
            buildDep();
            if (!this.built) {
                return -1;
            }
        }
        return anndep[year-1][DDL];
    }
    
    public double GetBegBalance(int year, String type) {
        if (!this.built) {
            buildDep();
            if (!this.built) {
                return -1;
            }
        }
        if (year < 1 || year > this.life) {
            return -1;
        }
        if (type.equalsIgnoreCase("S")) {
            return begbal[year-1][SL];
        }
        else if (type.equalsIgnoreCase(("D"))){
            return begbal[year-1][DDL];
        }
        else {
            return -1;
        }
    }
    public double GetEndBalance(int year, String type) {
        if (!this.built) {
            buildDep();
            if (!this.built) {
                return -1;
            }
        }
        if (year < 1 || year > this.life) {
            return -1;
        }
        if (type.equalsIgnoreCase("S")) {
            return endbal[year-1][SL];
        }
        else if (type.equalsIgnoreCase(("D"))){
            return endbal[year-1][DDL];
        }
        else {
            return -1;
        }
    }    

    public void setSave(File selectedFile) {
        try {
            if (!selectedFile.exists()) {
            selectedFile.createNewFile();
        }
        } catch(IOException e) {
            e.printStackTrace();
        }
        try (PrintWriter out = new PrintWriter(selectedFile.getCanonicalPath())) {
            out.println(this.name);
            out.println(this.cost);
            out.println(this.salvage);
            out.println(this.life);
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
