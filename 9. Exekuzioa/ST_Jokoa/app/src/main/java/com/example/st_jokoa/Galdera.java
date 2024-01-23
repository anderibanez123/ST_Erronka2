package com.example.st_jokoa;

public class Galdera {
    private int id;  // Galderaren identifikazio zenbakia
    private String galdera;  // Galderaren testua
    private String erantzunZuzena;  // Zuzeneko erantzuna galderari
    private String erantzunOkerra1;  // Lehenengo okerraren erantzuna
    private String erantzunOkerra2;  // Bigarren okerraren erantzuna
    private String erantzunOkerra3;  // Hirugarren okerraren erantzuna
    private int correctAnswerIndex;  // Zuzeneko erantzuna zer posiziotan dagoen (0-3 artekoa)

    public Galdera() {
        // SQLite-arekin lan egiteko beharrezkoa den konstruktorea
    }

    // Konstruktorea: galderaren datu guztiak hartu eta objetua sortzen du
    public Galdera(int id, String galdera, String erantzunZuzena, String erantzunOkerra1, String erantzunOkerra2, String erantzunOkerra3) {
        this.id = id;
        this.galdera = galdera;
        this.erantzunZuzena = erantzunZuzena;
        this.erantzunOkerra1 = erantzunOkerra1;
        this.erantzunOkerra2 = erantzunOkerra2;
        this.erantzunOkerra3 = erantzunOkerra3;
    }

    // Getter: galderaren identifikazio zenbakia lortzen du
    public int getId() {
        return id;
    }

    // Setter: galderaren identifikazio zenbakia ezartzen du
    public void setId(int id) {
        this.id = id;
    }

    // Getter: galderaren testua lortzen du
    public String getGaldera() {
        return galdera;
    }

    // Setter: galderaren testua ezartzen du
    public void setGaldera(String galdera) {
        this.galdera = galdera;
    }

    // Getter: galderaren zuzeneko erantzuna lortzen du
    public String getErantzunZuzena() {
        return erantzunZuzena;
    }

    // Setter: galderaren zuzeneko erantzuna ezartzen du
    public void setErantzunZuzena(String erantzunZuzena) {
        this.erantzunZuzena = erantzunZuzena;
    }

    // Getter: lehenengo okerraren erantzuna lortzen du
    public String getErantzunOkerra1() {
        return erantzunOkerra1;
    }

    // Setter: lehenengo okerraren erantzuna ezartzen du
    public void setErantzunOkerra1(String erantzunOkerra1) {
        this.erantzunOkerra1 = erantzunOkerra1;
    }

    // Getter: bigarren okerraren erantzuna lortzen du
    public String getErantzunOkerra2() {
        return erantzunOkerra2;
    }

    // Setter: bigarren okerraren erantzuna ezartzen du
    public void setErantzunOkerra2(String erantzunOkerra2) {
        this.erantzunOkerra2 = erantzunOkerra2;
    }

    // Getter: hirugarren okerraren erantzuna lortzen du
    public String getErantzunOkerra3() {
        return erantzunOkerra3;
    }

    // Setter: hirugarren okerraren erantzuna ezartzen du
    public void setErantzunOkerra3(String erantzunOkerra3) {
        this.erantzunOkerra3 = erantzunOkerra3;
    }

    // Getter: zuzeneko erantzuna zer posiziotan dagoen lortzen du
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    // Setter: zuzeneko erantzuna zer posiziotan dagoen ezartzen du
    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }
}
