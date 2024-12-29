//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca("Minha Biblioteca");

        // Adicionando obras
        Livro livro = new Livro("Livro", "Editora", "Ficção", 2021, "1234", "Autor");
        JornalRevista revista = new JornalRevista("Revista", "Editora", "Ciência", "5678", "01/12/2023");

        biblioteca.adicionaObra(livro);
        biblioteca.adicionaObra(revista);

        // Removendo uma obra
        biblioteca.removeObra("1235");
        biblioteca.removeObra("9999");
        biblioteca.leDadosObra("1234");
    }
}