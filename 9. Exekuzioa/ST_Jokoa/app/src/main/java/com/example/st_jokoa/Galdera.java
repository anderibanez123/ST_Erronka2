package com.example.st_jokoa;

public class Galdera {
    private int id;
    private String galdera;
    private static String erantzunZuzena;
    private String erantzunOkerra1;
    private String erantzunOkerra2;
    private String erantzunOkerra3;

    public Galdera() {
        // Constructor vacío necesario para trabajar con SQLite
    }

    public Galdera(int id, String galdera, String erantzunZuzena, String erantzunOkerra1, String erantzunOkerra2, String erantzunOkerra3) {
        this.id = id;
        this.galdera = galdera;
        this.erantzunZuzena = erantzunZuzena;
        this.erantzunOkerra1 = erantzunOkerra1;
        this.erantzunOkerra2 = erantzunOkerra2;
        this.erantzunOkerra3 = erantzunOkerra3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGaldera() {
        return galdera;
    }

    public void setGaldera(String galdera) {
        this.galdera = galdera;
    }

    public static String getErantzunZuzena() {
        return erantzunZuzena;
    }

    public void setErantzunZuzena(String erantzunZuzena) {
        this.erantzunZuzena = erantzunZuzena;
    }

    public String getErantzunOkerra1() {
        return erantzunOkerra1;
    }

    public void setErantzunOkerra1(String erantzunOkerra1) {
        this.erantzunOkerra1 = erantzunOkerra1;
    }

    public String getErantzunOkerra2() {
        return erantzunOkerra2;
    }

    public void setErantzunOkerra2(String erantzunOkerra2) {
        this.erantzunOkerra2 = erantzunOkerra2;
    }

    public String getErantzunOkerra3() {
        return erantzunOkerra3;
    }

    public void setErantzunOkerra3(String erantzunOkerra3) {
        this.erantzunOkerra3 = erantzunOkerra3;
    }
}
