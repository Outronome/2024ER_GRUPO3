import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menus {
    public static void MenuPrincipal() {
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
            op = Funcionalidades.lerInt(5,menu);
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
    public static void MenuUtente() {
        int op;
        String[] menu = {
                "\n==== MENU Utente ====",
                "1. Adicionar Utente",
                "2. Editar Utente",
                "3. Remover Utente",
                "0. Sair",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerInt(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Adicionar Utente");
                    Utente.registar();
                    break;
                case 2:
                    System.out.println("Escolheu: Editar Utente");
                    break;
                    MenuUtentePesquisa(0);
                case 3:
                    System.out.println("Escolheu: Remover Utente");
                    break;
                    MenuUtentePesquisa(1);
                case 0:
                    op = 0;
                    System.out.println("Voltar");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }

        } while (op != 0);
    }
    public static void MenuUtentePesquisa(int editElim) {
        int op;
        String[] menu = {
                "\n==== MENU Utente Edição ====",
                "1. Pesquisar Pelo NIF",
                "2. Pesquisar Pelo Nome",
                "3. Pesquisar Pelo Contacto",
                "0. Sair",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerInt(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: NIF");
                    if (editElim == 0){
                        Utente.registar("nif");
                    }else{
                        Utente.eliminar("nif");
                    }
                    break;
                case 2:
                    System.out.println("Escolheu: Nome");
                    if (editElim == 0){
                        Utente.registar("nome");
                    }else{
                        Utente.eliminar("nome");
                    }
                    break;
                case 3:
                    System.out.println("Escolheu: Contacto");
                    if (editElim == 0){
                        Utente.registar("contacto");
                    }else{
                        Utente.eliminar("contacto");
                    }
                    break;
                case 0:
                    op = 0;
                    System.out.println("Voltar");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }

        } while (op != 0);
    }

}
