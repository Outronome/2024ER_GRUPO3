import java.util.Scanner;

public class Menus {
    private static int ler(int range, String[] pergunta){
        int val = -1  ;
        do {
            Scanner ler = new Scanner(System.in);
            escrever(pergunta);
            val = ler.nextInt();
            System.out.println(val);
        }while (val<=0 && val >= range);
        return val;
    }
    private static void escrever(String[] perguntas){
        for (String pergunta : perguntas) {
            System.out.println(pergunta);
        }
    }
    public static void Menus() {
        int op;
        String[] menu = {
                         "\n==== MENU PRINCIPAL ====",
                         "1. Menu Livros",
                         "2. Menu Jornais/Revistas",
                         "3. Menu Utente",
                         "4. Menu Empréstimo",
                         "5. Menu Reserva",
                         "0. Sair",
                         "Escolha uma opção: "
                        };
        do {
            op = ler(5,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Menu Livros");
                    break;
                case 2:
                    System.out.println("Escolheu: Menu Jornais/Revistas");
                    break;
                case 3:
                    System.out.println("Escolheu: Menu Utente");
                    break;
                case 4:
                    System.out.println("Escolheu: Menu Empréstimo");
                    break;
                case 5:
                    System.out.println("Escolheu: Menu Reserva");
                    break;
                case 0:
                    System.out.println("Sair");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }

        } while (op != 0);
    }
}
