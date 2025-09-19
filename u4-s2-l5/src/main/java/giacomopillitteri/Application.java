package giacomopillitteri;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Collezione collezione = new Collezione();
        Scanner scanner = new Scanner(System.in);
        int scelta = -1;

        collezione.aggiungiGioco(new Videogioco("V1", "The Witcher 3", 2025, 29.99, "PC", 150, Genere.RPG));
        collezione.aggiungiGioco(new GiocoDaTavolo("T1", "Catan", 1995, 35.50, 4, 90));
        collezione.aggiungiGioco(new Videogioco("V2", "Elden Ring", 2022, 59.99, "PS5", 80, Genere.AZIONE));

        do {
            try {
                System.out.println("\n--- Menu Collezione Giochi ---");
                System.out.println("1. Aggiungi un gioco");
                System.out.println("2. Cerca un gioco per ID");
                System.out.println("3. Cerca giochi con prezzo inferiore a un valore");
                System.out.println("4. Cerca giochi da tavolo per numero di giocatori");
                System.out.println("5. Rimuovi un gioco per ID");
                System.out.println("6. Aggiorna un gioco per ID");
                System.out.println("7. Stampa statistiche");
                System.out.println("8. Stampa tutta la collezione");
                System.out.println("0. Esci");
                System.out.print("Scegli un'opzione: ");

                scelta = scanner.nextInt();
                scanner.nextLine(); // Consuma il newline

                switch (scelta) {
                    case 1:
                        aggiungiGiocoUtente(scanner, collezione);
                        break;
                    case 2:
                        ricercaIdUtente(scanner, collezione);
                        break;
                    case 3:
                        ricercaPrezzoUtente(scanner, collezione);
                        break;
                    case 4:
                        ricercaNumeroGiocatoriUtente(scanner, collezione);
                        break;
                    case 5:
                        rimuoviGiocoUtente(scanner, collezione);
                        break;
                    case 6:
                        aggiornaGiocoUtente(scanner, collezione);
                        break;
                    case 7:
                        collezione.stampaStatistiche();
                        break;
                    case 8:
                        collezione.stampaTuttiIGiochi();
                        break;
                    case 0:
                        System.out.println("Uscita in corso...");
                        break;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Errore: Inserisci un numero valido.");
                scanner.nextLine(); // Consuma l'input non valido
                scelta = -1; // Per continuare il ciclo
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: " + e.getMessage());
            }

        } while (scelta != 0);

        scanner.close();
    }


    private static void aggiungiGiocoUtente(Scanner scanner, Collezione collezione) {
        System.out.println("Inserisci ID del gioco: ");
        String id = scanner.nextLine();
        System.out.println("Inserisci titolo: ");
        String titolo = scanner.nextLine();
        System.out.println("Inserisci anno di pubblicazioni: ");
        int anno = scanner.nextInt();
        System.out.println("Inserisci prezzo: ");
        double prezzo = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("E' un videogioco (V) o un gioco da tavolo (T)? ");
        String tipo = scanner.nextLine().toUpperCase();

        try {
            if (tipo.equals("V")) {
                System.out.print("Inserisci piattaforma: ");
                String piattaforma = scanner.nextLine();
                System.out.print("Inserisci durata in ore: ");
                int durata = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Inserisci genere (es. AZIONE, RPG): ");
                String genereStr = scanner.nextLine().toUpperCase();
                Genere genere = Genere.valueOf(genereStr);
                collezione.aggiungiGioco(new Videogioco(id, titolo, anno, prezzo, piattaforma, durata, genere));
            } else if (tipo.equals("T")) {
                System.out.print("Inserisci numero di giocatori: ");
                int numGiocatori = scanner.nextInt();
                System.out.print("Inserisci durata media partita (in minuti): ");
                int durataPartita = scanner.nextInt();
                collezione.aggiungiGioco(new GiocoDaTavolo(id, titolo, anno, prezzo, numGiocatori, durataPartita));
            } else {
                System.out.println("Tipo di gioco non valido.");
            }
        } catch (IllegalArgumentException e){
            System.out.println("Errore: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Errore durante l'inserimento dei dati: " + e.getMessage());
        }
    }

    private static void ricercaIdUtente(Scanner scanner, Collezione collezione) {
        System.out.print("Inserisci l'ID del gioco da cercare: ");
        String id = scanner.nextLine();
        Optional<Gioco> giocoTrovato = collezione.ricercaPerId(id);
        giocoTrovato.ifPresentOrElse(
                g -> System.out.println("Gioco trovato: " + g),
                () -> System.out.println("Gioco con ID " + id + " non trovato.")
        );
    }

    private static void ricercaPrezzoUtente(Scanner scanner, Collezione collezione) {
        System.out.print("Inserisci il prezzo massimo: ");
        double prezzoMax = scanner.nextDouble();
        List<Gioco> giochiTrovati = collezione.ricercaPerPrezzoInferiore(prezzoMax);
        if (giochiTrovati.isEmpty()) {
            System.out.println("Nessun gioco trovato con prezzo inferiore a " + prezzoMax);
        } else {
            System.out.println("Giochi con prezzo inferiore a " + prezzoMax + ":");
            giochiTrovati.forEach(System.out::println);
        }
    }

    private static void ricercaNumeroGiocatoriUtente(Scanner scanner, Collezione collezione) {
        System.out.print("Inserisci il numero di giocatori: ");
        int numGiocatori = scanner.nextInt();
        List<GiocoDaTavolo> giochiTrovati = collezione.ricercaPerNumeroGiocatori(numGiocatori);
        if (giochiTrovati.isEmpty()) {
            System.out.println("Nessun gioco da tavolo trovato con " + numGiocatori + " giocatori.");
        } else {
            System.out.println("Giochi da tavolo trovati con " + numGiocatori + " giocatori:");
            giochiTrovati.forEach(System.out::println);
        }
    }

    private static void rimuoviGiocoUtente(Scanner scanner, Collezione collezione) {
        System.out.print("Inserisci l'ID del gioco da rimuovere: ");
        String id = scanner.nextLine();
        collezione.rimuoviGioco(id);
    }

    private static void aggiornaGiocoUtente(Scanner scanner, Collezione collezione) {
        System.out.print("Inserisci l'ID del gioco da aggiornare: ");
        String id = scanner.nextLine();

        System.out.print("Inserisci il nuovo titolo del gioco: ");
        String nuovoTitolo = scanner.nextLine();
        System.out.print("Inserisci il nuovo anno di pubblicazione: ");
        int nuovoAnno = scanner.nextInt();
        System.out.print("Inserisci il nuovo prezzo: ");
        double nuovoPrezzo = scanner.nextDouble();
        scanner.nextLine();

        Optional<Gioco> giocoOriginale = collezione.ricercaPerId(id);
        if(giocoOriginale.isPresent()) {
            Gioco giocoAggiornato;
            if(giocoOriginale.get() instanceof Videogioco) {
                Videogioco vg = (Videogioco) giocoOriginale.get();
                giocoAggiornato = new Videogioco(id, nuovoTitolo, nuovoAnno, nuovoPrezzo, vg.getPiattaforma(), vg.getDurataGioco(), vg.getGenere());
            } else {
                GiocoDaTavolo gt = (GiocoDaTavolo) giocoOriginale.get();
                giocoAggiornato = new GiocoDaTavolo(id, nuovoTitolo, nuovoAnno, nuovoPrezzo, gt.getNumeroGiocatori(), gt.getDurataMediaPartita());
            }
            collezione.aggiornaGioco(id, giocoAggiornato);
        } else {
            System.out.println("Errore: Gioco con ID " + id + " non trovato.");
        }
    }
    }



