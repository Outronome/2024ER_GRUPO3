import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final List<Utente> utentes = new ArrayList<>();
    private static final List<Livro> livros = new ArrayList<>();
    private static final List<JornalRevista> jornaisRevistas = new ArrayList<>();
    private static final List<Emprestimo> emprestimos = new ArrayList<>();
    private static final List<Reserva> reservas = new ArrayList<>();

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca("BibPorto2");
        List<Utente> utentes = Utente.lerTodosUtentes();
        List<Livro> livros = Livro.lerTodosLivros();
        List<JornalRevista> jornaisRevistas = JornalRevista.lerTodosJornaisRevistas();
        List<Emprestimo> emprestimos = Emprestimo.lerTodosEmprestimos();
        List<Reserva> reservas = Reserva.lerTodosReservas();


        Menus menu = new Menus();
        menu.menuPrincipal();
    }
}