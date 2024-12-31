//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {



       // Utente.registar();

        Biblioteca biblioteca = new Biblioteca("BibPorto");

       /* // Adicionando obras
        Livro livro = new Livro("Livro", "Editora", "Ficção", 2021, "1234", "Autor");
        JornalRevista revista = new JornalRevista("Revista", "Editora", "Ciência", "5678", "01/12/2023");

        biblioteca.adicionaObra(livro);
        biblioteca.adicionaObra(revista);

        // Removendo uma obra
        biblioteca.removeObra("1235");
        biblioteca.removeObra("9999");
        biblioteca.leDadosObra("1234");
*/
        biblioteca.setBibliotecaAtual("BibPorto2");
        Livro.registar();

        // Instância de Ficheiros para objetos do tipo Livro
        //Ficheiros<LivroTesteFicheiros> ficheiroLivros = new Ficheiros<LivroTesteFicheiros>() {};
        // Formato que será utilizado na escrita
        //String livroFormat = "%s|%s|%s|%d|%s|%s%n";  // Formato para Livro
        //String categoriaFormat = "%s|%s|%s%n";  // Formato para Categoria

        // Criar livros
        //LivroTesteFicheiros livro1 = new LivroTesteFicheiros("Java para Iniciantes", "Editora A", "Programação", 2022, "978-3-16-148410-0", "Autor 1");
        //LivroTesteFicheiros livro2 = new LivroTesteFicheiros("Design Patterns", "Editora B", "Software", 2019, "978-1-23-456789-0", "Autor 2");
        //CategoriaTesteFicheiros categoria = new CategoriaTesteFicheiros("Fantasia", "Editora Aventura", "Literatura");
        // Criar a lista de livros
        //List<LivroTesteFicheiros> livros = List.of(livro1, livro2);

        // Nome do ficheiro
        //String nomeFicheiro = "categoria.txt";
        //Ficheiros.escrever(nomeFicheiro,categoria, categoriaFormat);
        //nomeFicheiro = "livros.txt";
        //for (LivroTesteFicheiros livro : livros) {
        //    ficheiroLivros.escrever(nomeFicheiro,livro, livroFormat);
        //}
        //List<String> linhas = Ficheiros.ler(nomeFicheiro);

        // Exibe cada linha do ficheiro
        //for (int i = 0; i < linhas.size(); i++) {
        //    System.out.println("Linha " + i + ": " + linhas.get(i));
        //}
        //try {
        //    // Substituir linha inteira com ISBN
        //    String chaveBusca = "978-3-16-148410-0";  // ISBN do livro a ser atualizado
        //    String palavraAntiga = null;  // Não vamos usar isso aqui, pois substituiremos a linha inteira
        //    String palavraNova = null;  // Também não usaremos
        //    String novaLinha = "Java para Iniciantes|Editora A|Programação|2023|978-3-16-148410-0|Autor 1 Atualizado"; // Nova linha para substituir a antiga

            // Atualiza conteúdo substituindo a linha inteira
            //Ficheiros.atualizar(nomeFicheiro, chaveBusca, palavraAntiga, palavraNova, novaLinha);
            //System.out.println("Livro atualizado por linha com sucesso!");

            // Agora vamos fazer a substituição de palavra dentro de uma linha
            //String palavraAntigaSubstituicao = "Java";  // Palavra a ser substituída
            //String palavraNovaSubstituicao = "Java 17"; // Nova palavra
            //chaveBusca = "978-3-16-148410-0"; // Usando o ISBN como chave de busca

            // Atualiza conteúdo substituindo a palavra dentro da linha
            //Ficheiros.atualizar(nomeFicheiro, chaveBusca, palavraAntigaSubstituicao, palavraNovaSubstituicao, null);
            //System.out.println("Palavra substituída com sucesso!");

        //} catch (IOException e) {
        //    System.err.println("Erro ao atualizar o conteúdo: " + e.getMessage());
        //}
        //try {
            // ISBN do livro a ser apagado
            //String isbnParaApagar = "978-3-16-148410-0";  // ISBN a ser apagado

            // Apagar livro com ISBN específico
            //Ficheiros.apagar(nomeFicheiro, isbnParaApagar);
            //System.out.println("Livro com ISBN " + isbnParaApagar + " apagado com sucesso!");

        //} catch (IOException e) {
            //System.err.println("Erro ao apagar o livro: " + e.getMessage());
        //}

        /*

        // Ler os livros do ficheiro
        List<Livro> livrosLidos = ficheiroLivros.ler(nomeFicheiro);
        System.out.println("Livros lidos do ficheiro:");
        for (Livro livro : livrosLidos) {
            System.out.println(livro);
        }



        // Ler os livros do ficheiro
        livrosLidos = ficheiroLivros.ler(nomeFicheiro);
        System.out.println("Livros lidos do ficheiro:");
        for (Livro livro : livrosLidos) {
            System.out.println(livro);
        }

        // Apagar os livros do ficheiro
        ficheiroLivros.apagar(nomeFicheiro);

         */
        //main comentado por este codigo é so para servir de exemplo
    }
}