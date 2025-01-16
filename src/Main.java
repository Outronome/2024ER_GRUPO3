import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        //Carregar todos os dados para as respetivas listas
        Utente.getUtentes();
        Livro.getLivros();
        JornalRevista.getJornaisRevistas();
        Emprestimo.getEmprestimos();
        Reserva.getReservas();

        Menus menu = new Menus();
        menu.menuPrincipal();
    }
}