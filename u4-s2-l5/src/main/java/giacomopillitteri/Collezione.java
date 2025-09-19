package giacomopillitteri;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Collezione {
    private  List<Gioco> giochi = new ArrayList<>();

    // 1. Aggiunta di un elemento
    public void aggiungiGioco(Gioco nuovoGioco) {
        // Controllo per evitare duplicati basato sull'ID
        Optional<Gioco> giocoEsistente = giochi.stream()
                .filter(g -> g.getIdGioco().equals(nuovoGioco.getIdGioco()))
                .findFirst();

        if (giocoEsistente.isPresent()) {
            throw new IllegalArgumentException("Errore: Gioco con ID " + nuovoGioco.getIdGioco() + " già esistente.");
        }
        giochi.add(nuovoGioco);
        System.out.println("Gioco aggiunto con successo.");
    }

    // 2. Ricerca per ID
    public Optional<Gioco> ricercaPerId(String id) {
        return giochi.stream()
                .filter(g -> g.getIdGioco().equals(id))
                .findFirst();
    }

    // 3. Ricerca per prezzo
    public List<Gioco> ricercaPerPrezzoInferiore(double prezzoMax) {
        return giochi.stream()
                .filter(g -> g.getPrezzo() < prezzoMax)
                .collect(Collectors.toList());
    }

    // 4. Ricerca per numero di giocatori
    public List<GiocoDaTavolo> ricercaPerNumeroGiocatori(int numGiocatori) {
        return giochi.stream()
                .filter(g -> g instanceof GiocoDaTavolo) // Filtra solo gli oggetti di tipo GiocoDaTavolo
                .map(g -> (GiocoDaTavolo) g) // Cast per poter accedere ai metodi specifici
                .filter(g -> g.getNumeroGiocatori() == numGiocatori)
                .collect(Collectors.toList());
    }

    // 5. Rimozione di un elemento
    public void rimuoviGioco(String id) {
        boolean rimosso = giochi.removeIf(g -> g.getIdGioco().equals(id));
        if (!rimosso) {
            throw new IllegalArgumentException("Errore: Gioco con ID " + id + " non trovato.");
        }
        System.out.println("Gioco rimosso con successo.");
    }

    // 6. Aggiornamento di un elemento
    public void aggiornaGioco(String id, Gioco giocoAggiornato) {
        Optional<Gioco> giocoEsistente = ricercaPerId(id);
        if (giocoEsistente.isPresent()) {
            // Aggiorna l'elemento esistente nella lista
            int index = giochi.indexOf(giocoEsistente.get());
            giochi.set(index, giocoAggiornato);
            System.out.println("Gioco aggiornato con successo.");
        } else {
            throw new IllegalArgumentException("Errore: Gioco con ID " + id + " non trovato.");
        }
    }

    // 7. Statistiche della collezione
    public void stampaStatistiche() {
        long numeroVideogiochi = giochi.stream()
                .filter(g -> g instanceof Videogioco)
                .count();

        long numeroGiochiDaTavolo = giochi.stream()
                .filter(g -> g instanceof GiocoDaTavolo)
                .count();

        // Gioco con il prezzo più alto
        Optional<Gioco> giocoPiuCaro = giochi.stream()
                .max((g1, g2) -> Double.compare(g1.getPrezzo(), g2.getPrezzo()));

        // Media dei prezzi
        double mediaPrezzi = giochi.stream()
                .mapToDouble(Gioco::getPrezzo)
                .average()
                .orElse(0.0);

        System.out.println("\n--- Statistiche Collezione ---");
        System.out.println("Numero totale di videogiochi: " + numeroVideogiochi);
        System.out.println("Numero totale di giochi da tavolo: " + numeroGiochiDaTavolo);
        giocoPiuCaro.ifPresent(g -> System.out.println("Gioco con il prezzo più alto: " + g.getTitolo() + " (€" + g.getPrezzo() + ")"));
        System.out.println("Prezzo medio di tutti gli elementi: €" + String.format("%.2f", mediaPrezzi));
    }

    // Metodo per stampare tutta la collezione
    public void stampaTuttiIGiochi() {
        giochi.forEach(System.out::println);
    }
}
