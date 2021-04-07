/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appthoitiet;

/**
 *
 * @author USER
 */
public class User {
    public String ID;
    public String PW;
    public String NAME;
    public boolean Ad;
    public boolean Guest;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public User() {
        this.ID = "";
        this.PW = "";
        this.Ad = false;
        this.Guest=false;
    }

    public boolean isGuest() {
        return Guest;
    }

    public void setGuest(boolean Guest) {
        this.Guest = Guest;
    }

    public User(String ID, String PW, boolean Ad) {
        this.ID = ID;
        this.PW = PW;
        this.Ad = Ad;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }

    public void setAd(boolean Ad) {
        this.Ad = Ad;
    }

    public String getID() {
        return ID;
    }

    public String getPW() {
        return PW;
    }

    public boolean isAd() {
        return Ad;
    }
    
    
}
