package Projekt_Quiz;

public class Frage {
    private String frageText;
    private String[] antworten;
    private int richtigeAntwort;
    private String bildPfad;

    public Frage(String frageText, String[] antworten, int richtigeAntwort) {
        this.frageText = frageText;
        this.antworten = antworten;
        this.richtigeAntwort = richtigeAntwort;
        this.bildPfad = null;
    }

    public String getFrageText() {
        return frageText;
    }

    public String[] getAntworten() {
        return antworten;
    }

    public boolean istRichtig(int antwortIndex) {
        return antwortIndex == richtigeAntwort;
    }

    public String getBildPfad() {
        return bildPfad;
    }
}
