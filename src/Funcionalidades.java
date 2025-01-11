import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Funcionalidades {
    public static int lerInteiros(int range, String[] pergunta){
        Scanner ler = new Scanner(System.in);
        int val = -1  ;
        do {
            escreverStrings(pergunta);
            val = ler.nextInt();
        }while (val<=0 && val >= range);
        return val;
    }
    public static int lerOpcoesMenus(int range, String[] pergunta){
        Scanner ler = new Scanner(System.in);
        int val = -1  ;
        do {
            escreverStrings(pergunta);
            val = ler.nextInt();
        }while (val<=0 && val >= range);
        return val;
    }
    public static String lerString(String pergunta){
        Scanner ler = new Scanner(System.in);
        escreverString(pergunta);
        return ler.nextLine();
    }
    public static int lerInt(String pergunta){
        Scanner ler = new Scanner(System.in);
        escreverString(pergunta);
        return ler.nextInt();
    }
    public static void escreverString(String texto){
        System.out.println(texto);
    }
    public static void escreverStrings(String[] lista){
        for (String elemento : lista) {
            System.out.println(elemento);
        }
    }
    public static boolean verificarDependencias(String identificador) {
        List<String> linhas = Ficheiros.ler("reservas.txt");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (String linha : linhas) {
            String[] partes = linha.split("\\|");
            String dataFinal = partes[5].trim();
            LocalDate hoje = LocalDate.now(); // Data atual
            LocalDate fim = LocalDate.parse(dataFinal, formatter); // Converte a dataFinal
            // Ajuste conforme o formato das linhas no arquivo
            if (linha.contains(identificador.trim())&&fim.isAfter(hoje)) {
                return false;
            }
        }

        return true;
    }
}
