package giacomopillitteri;

import java.util.Objects;

public abstract class Gioco {
    protected String idGioco;
    protected String titolo;
    protected int annoPubblicazione;
    protected  double prezzo;

    public Gioco(String idGioco, String titolo, int annoPubblicazione, double prezzo) {
        this.idGioco = idGioco;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.prezzo = prezzo;
    }

    public String getIdGioco() {
        return idGioco;
    }

    public String getTitolo() {
        return titolo;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setIdGioco(String idGioco) {
        this.idGioco = idGioco;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Gioco gioco = (Gioco) o;
        return Objects.equals(idGioco, gioco.idGioco);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idGioco);
    }

    @Override
    public String toString() {
        return "Gioco{" +
                "idGioco='" + idGioco + '\'' +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", prezzo=" + prezzo +
                '}';
    }
}
