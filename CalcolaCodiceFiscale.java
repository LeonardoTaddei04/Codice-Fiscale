import java.util.HashMap;
import java.util.Scanner;

public class CalcolaCodiceFiscale {

    // Mappa per convertire i numeri in caratteri
    private static final HashMap<Integer, Character> caratteriControllo = new HashMap<>();

    static {
        caratteriControllo.put(0, 'A');
        caratteriControllo.put(1, 'B');
        caratteriControllo.put(2, 'C');
        caratteriControllo.put(3, 'D');
        caratteriControllo.put(4, 'E');
        caratteriControllo.put(5, 'F');
        caratteriControllo.put(6, 'G');
        caratteriControllo.put(7, 'H');
        caratteriControllo.put(8, 'I');
        caratteriControllo.put(9, 'J');
        caratteriControllo.put(10, 'K');
        caratteriControllo.put(11, 'L');
        caratteriControllo.put(12, 'M');
        caratteriControllo.put(13, 'N');
        caratteriControllo.put(14, 'O');
        caratteriControllo.put(15, 'P');
        caratteriControllo.put(16, 'Q');
        caratteriControllo.put(17, 'R');
        caratteriControllo.put(18, 'S');
        caratteriControllo.put(19, 'T');
        caratteriControllo.put(20, 'U');
        caratteriControllo.put(21, 'V');
        caratteriControllo.put(22, 'W');
        caratteriControllo.put(23, 'X');
        caratteriControllo.put(24, 'Y');
        caratteriControllo.put(25, 'Z');
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        //
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        //
        System.out.println("Cognome: ");
        String cognome = scanner.nextLine();
        //
        System.out.println("Data di nascita: ");
        String dataNascita = scanner.nextLine();
        //
        System.out.println("Comune di nascita: ");
        String comuneNascita = scanner.nextLine();
        //
        System.out.println("Sesso M (Maschio) F (Femmina)");
        String inputSesso = scanner.nextLine();
        char sesso = inputSesso.toUpperCase().charAt(0);

        String codiceFiscale = generaCodiceFiscale(nome, cognome, sesso, dataNascita, comuneNascita);
        System.out.println("Il codice fiscale è: " + codiceFiscale);
    }

    public static String generaCodiceFiscale(String nome, String cognome, char sesso, String dataNascita, String comuneNascita) {
        // Conversione delle stringhe in uppercase
        nome = nome.toUpperCase();
        cognome = cognome.toUpperCase();
        comuneNascita = comuneNascita.toUpperCase();

        // Estrazione dei caratteri
        String cognomeCodice = estraiConsonanti(cognome);
        String nomeCodice = estraiConsonanti(nome);
        String dataCodice = estraiData(dataNascita);
        String comuneCodice = estraiComune(comuneNascita);
        String codiceSesso = (sesso == 'M') ? "0" : "1";

        // Combinazione dei codici
        String codiceParziale = cognomeCodice + nomeCodice + dataCodice + comuneCodice + codiceSesso;

        // Calcolo del carattere di controllo
        int somma = 0;
        for (int i = 0; i < codiceParziale.length(); i++) {
            char carattere = codiceParziale.charAt(i);
            if (i % 2 == 0) {
                somma += valorePari(carattere);
            } else {
                somma += valoreDispari(carattere);
            }
        }
        int resto = somma % 26;
        char carattereControllo = caratteriControllo.get(resto);

        return codiceParziale + carattereControllo;
    }

    // Metodo per estrarre le consonanti da una stringa
    private static String estraiConsonanti(String input) {
        StringBuilder risultato = new StringBuilder();
        for (char carattere : input.toCharArray()) {
            if ("BCDFGHJKLMNPQRSTVWXYZ".indexOf(carattere) != -1) {
                risultato.append(carattere);
            }
        }
        return padString(risultato.toString(), 3);
    }

    // Metodo per estrarre la data di nascita
    private static String estraiData(String data) {
        String[] parti = data.split("/");
        String anno = parti[2].substring(2);
        String mese = parti[1];
        String giorno = parti[0];
        return anno + mese + giorno;
    }

    // Metodo per estrarre il codice del comune
    private static String estraiComune(String comune) {
        String[] parti = comune.split(" ");
        StringBuilder risultato = new StringBuilder();
        for (String parte : parti) {
            if (parte.length() > 3) {
                risultato.append(parte.charAt(0));
                risultato.append(parte.charAt(2));
                risultato.append(parte.charAt(3));
            } else {
                risultato.append(parte);
                while (risultato.length() < 3) {
                    risultato.append('X');
                }
            }
        }
        return padString(risultato.toString(), 3);
    }

    // Metodo per calcolare il valore per i caratteri pari
    private static int valorePari(char carattere) {
        int valore = Character.getNumericValue(carattere);
        if (valore < 0) {
            valore = (valore + 10) * 2;
        } else {
            valore *= 2;
        }
        return valore > 9 ? valore - 9 : valore;
    }

    // Metodo per calcolare il valore per i caratteri dispari
    private static int valoreDispari(char carattere) {
        int valore = Character.getNumericValue(carattere);
        return valore < 0 ? valore + 10 : valore;
    }

    // Metodo per aggiungere zeri a sinistra se la stringa è più corta di una certa lunghezza
    private static String padString(String input, int lunghezza) {
        return String.format("%-" + lunghezza + "s", input).replace(' ', 'X');
    }
}
