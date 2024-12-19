import java.util.Scanner;

public class Menus {

    public static void Menus() {
        Scanner scanner = new Scanner(System.in);
        int ler_menu_opcao;




        do {
            System.out.println("\n==== MENU PRINCIPAL ====");
            System.out.println("1. Menu Livros");
            System.out.println("2. Menu Jornais/Revistas");
            System.out.println("3. Menu Utente");
            System.out.println("4. Menu Empréstimo");
            System.out.println("5. Menu Reserva");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            ler_menu_opcao = scanner.nextInt();
            scanner.nextLine();
            switch (ler_menu_opcao) {
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

        } while (ler_menu_opcao != 0);
    }
}
