import java.util.ArrayList;

public class Biblioteca {
    public static void main(String[] args) {
        // Criando ArrayLists
        ArrayList<Livro> livros = new ArrayList<>();
        ArrayList<Revista> revistas = new ArrayList<>();

        // Adicionando livros
        livros.add(new Livro("Livro A", "Editora A", "Ficção", 2020, "123-4567890123", "Autor A"));

        // Adicionando revistas
        revistas.add(new Revista("Revista X", "Editora X", "Ciência", "1111-2222", "01/01/2024"));

        // Mostrar livros
        System.out.println("Livros:");
        for (Livro livro : livros) {
            System.out.println("Título: " + livro.getTitulo() + ", Editora: " + livro.getEditora() + ", Categoria: " + livro.getCategoria() + ", Ano: " + livro.getAnoEdicao() + ", ISBN: " + livro.getIsbn() + ", Autor(es): " + livro.getAutores());
        }

        // Mostrar revistas
        System.out.println("\nRevistas:");
        for (Revista revista : revistas) {
            System.out.println("Título: " + revista.getTitulo() + ", Editora: " + revista.getEditora() + ", Categoria: " + revista.getCategoria() + ", ISSN: " + revista.getIssn() + ", Data de Publicação: " + revista.getDataPublicacao());
        }
    }
}
