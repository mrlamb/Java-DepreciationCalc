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
public abstract class Asset {
    
    protected String name, emsg;
    protected double cost, salvage;
    protected int life;
    protected boolean built;
    protected double[]begbal, anndep, endbal;
    protected static final int SL = 0, DDL = 1;
    
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
    
    

    protected boolean IsValid() {
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

    abstract void buildDep();
    
    public String getErrorMsg() {
        return this.emsg;
    }
    
    public double getAnnDep(int year) {
        if (built) {
            return anndep[year -1];
        }
        else
        {
            return -1;
        }
    }
    
    public double GetBegBalance(int year) {
        if (built) {
            return begbal[year -1];
        }
        else {
            return -1;
        }
    }
    public double GetEndBalance(int year) {
        if (built) {
            return endbal[year -1];
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
