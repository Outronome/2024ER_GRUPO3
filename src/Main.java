
public class Main {
    public static void main(String[] args) {
        //Menus menu = new Menus();
        //menu.menuPrincipal();
        Biblioteca biblioteca = new Biblioteca("");
        biblioteca.setBibliotecaAtual("BibPorto2");

        Reserva reserva = new Reserva(null,0,null,null,null,null);


        Livro.eliminar();
        }
}