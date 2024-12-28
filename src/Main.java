import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Instância de Ficheiros para objetos do tipo Livro
        Ficheiros<Livro> ficheiroLivros = new Ficheiros<Livro>() {};
        // Formato que será utilizado na escrita
        String livroFormat = "%s|%s|%s|%d|%s|%s%n";  // Formato para Livro
        String categoriaFormat = "%s|%s|%s%n";  // Formato para Categoria

        // Criar livros
        Livro livro1 = new Livro("Java para Iniciantes", "Editora A", "Programação", 2022, "978-3-16-148410-0", "Autor 1");
        Livro livro2 = new Livro("Design Patterns", "Editora B", "Software", 2019, "978-1-23-456789-0", "Autor 2");
        Categoria categoria = new Categoria("Fantasia", "Editora Aventura", "Literatura");
        // Criar a lista de livros
        List<Livro> livros = List.of(livro1, livro2);

        // Nome do ficheiro
        String nomeFicheiro = "categoria.txt";
        Ficheiros.escrever(nomeFicheiro,categoria, categoriaFormat);
        nomeFicheiro = "livros.txt";
        for (Livro livro : livros) {
            ficheiroLivros.escrever(nomeFicheiro,livro, livroFormat);
        }
        List<String> linhas = Ficheiros.ler(nomeFicheiro);

        // Exibe cada linha do ficheiro
        for (int i = 0; i < linhas.size(); i++) {
            System.out.println("Linha " + i + ": " + linhas.get(i));
        }
        /*

        // Ler os livros do ficheiro
        List<Livro> livrosLidos = ficheiroLivros.ler(nomeFicheiro);
        System.out.println("Livros lidos do ficheiro:");
        for (Livro livro : livrosLidos) {
            System.out.println(livro);
        }

        // Atualizar os livros (simplesmente sobrescreve)
        livro1.setAnoEdicao(2023);
        ficheiroLivros.atualizar(livros, nomeFicheiro);

        // Ler os livros do ficheiro
        livrosLidos = ficheiroLivros.ler(nomeFicheiro);
        System.out.println("Livros lidos do ficheiro:");
        for (Livro livro : livrosLidos) {
            System.out.println(livro);
        }

        // Apagar os livros do ficheiro
        ficheiroLivros.apagar(nomeFicheiro);

         */
    }
}
