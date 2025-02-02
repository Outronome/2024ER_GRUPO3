import java.util.ArrayList;
import java.util.List;

public class Livro extends Obra{
    public static final String NOME_FICHEIRO = "livros.txt";
    private int anoEdicao;
    private String isbn;
    private String autores;
    private static String FORMATO = "%s|%s|%s|%d|%s|%s%n";
    static List<Livro> livros = new ArrayList<>();


    // Construtor
    public Livro(String titulo, String editora, String categoria, int anoEdicao, String isbn, String autores) {
        super(titulo, editora, categoria);
        this.anoEdicao = anoEdicao;
        this.isbn = isbn;
        this.autores = autores;
    }

    // Construtor sem argumentos (necessário para o fromLine)

    @Override
    public String toString() {
        return String.format("Livro {Titulo='%s', Editora='%s', Categoria='%s', Ano de Edição=%d, ISBN='%s', Autores='%s'}",
                getTitulo(), getEditora(), getCategoria(), getAnoEdicao(), getIsbn(), getAutores());
    }
    public static List<Livro> lerTodosLivros() {
        Ficheiros<Livro> reader = new Ficheiros<>(Livro.class);
        return reader.lerMemoria(NOME_FICHEIRO);
    }

    // Getters e Setters
    public int getAnoEdicao() {
        return anoEdicao;
    }

    public void setAnoEdicao(int anoEdicao) {
        this.anoEdicao = anoEdicao;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public void exibeDados() {
        super.exibeDados(); // Exibe os dados comuns
        System.out.println("Ano de Edição: " + anoEdicao);
        System.out.println("ISBN: " + isbn);
        System.out.println("Autores: " + autores);
    }

    public Object[] getData() {
        return new Object[]{getTitulo(), getEditora(), getCategoria(), getAnoEdicao(),getIsbn(), getAutores()};
    }

    private boolean verficarLivro(Livro livro){
        if (livros == null || livros.isEmpty()) {
            return false;
        }

        for (Livro livroProcurar : livros) {
            if (livroProcurar.getIsbn().equals(livro.isbn)) {
                return true;
            }
        }
        return false;

    }

    private void adicionarLivro(Livro livro){
        livros.add(livro);
    }

    public static Livro registar(){
        Livro tempLivro = new Livro("","","",0,"","");
        tempLivro.introTitulo();
        tempLivro.introEditora();
        tempLivro.introCategoria();
        tempLivro.introAnoEdicao();
        tempLivro.introIsbn();
        tempLivro.introAutores();

        Livro newLivro = new Livro(tempLivro.titulo, tempLivro.editora, tempLivro.categoria, tempLivro.anoEdicao,tempLivro.isbn, tempLivro.autores);


        newLivro.adicionarLivro(newLivro);
        boolean sucesso = newLivro.verficarLivro(newLivro);
        if (sucesso){
            Ficheiros.escrever(NOME_FICHEIRO,newLivro,FORMATO);
            Funcionalidades.escreverString("Livro guardado com sucesso.");
        } else{
            Funcionalidades.escreverString("Erro:Livro não foi guardado.");
        }
        return newLivro;
    }

    public static void eliminar(){
        String isbneliminado = Funcionalidades.lerString("Introduza o Isbn do livro que deseja apagar:");
        if (Reserva.verificarDependencias(isbneliminado)){
        Ficheiros.apagar(NOME_FICHEIRO,isbneliminado);
        }
        else {
            System.out.println("O livro tem Reservas/Emprestimos pendentes.");
        }
    }



    private static List<String> ler(){
        return Ficheiros.ler(NOME_FICHEIRO);
    }

    public static Livro procurar(String isbn){
        List<String> livros;
        livros = ler();
        for (String livro : livros){
            String[] partes = livro.split("\\|");
            String[] partesFiltradas = { partes[4] };
            for (String parte : partesFiltradas) {
                if (parte.equals(isbn)){
                    return new Livro(partes[0],partes[1],partes[2],Integer.parseInt(partes[3]),partes[4],partes[5]);
                }
            }
        }

        return null;
        //ao receber null deve pedir outra vez a leitura de um dado para ler e procurar outro livro
    }

    public static void editarCampo(String isbn, String palavraAntiga, String palavraNova, int posCampo){

        Livro livro = procurar(isbn);
        if (livro == null) {
            System.out.println("Livro não encontrado.");
            return;
        }

        switch (posCampo) {
            case 1,2,3,6 -> {
                if (palavraNova.length() <= 3 || palavraNova.length() >= 100) {
                    Funcionalidades.escreverString("Erro: Introduza um texto entre 3 e 100 caracteres");
                    return;
                }

            }

            case 4 -> {
                do {

                    if (!palavraNova.matches("\\d+"))
                    {Funcionalidades.escreverString("Introduza um ano válido");
                    return;
                    }


                    if (Integer.parseInt(palavraNova) < 0 || Integer.parseInt(palavraNova)> 2024) {
                    Funcionalidades.escreverString("Erro: Introduza um Ano de Edição válido (entre 0 e 2024)");
                }
            } while (Integer.parseInt(palavraNova)< 0 || Integer.parseInt(palavraNova) > 2024);
            }


            case 5 -> {
                Obra obra = new Obra(null,null,null);
                do {
                    if (!obra.isbnValido(palavraNova)) {
                        Funcionalidades.escreverString("Erro: ISBN inválido. Certifique-se de que seja um ISBN-10 ou ISBN-13 válido (com hífens).");
                    }
                } while (!obra.isbnValido(palavraNova));
                }
            }



        if (palavraAntiga != null) {
            Ficheiros.atualizar(NOME_FICHEIRO, isbn, palavraAntiga, palavraNova, "");
            System.out.println("Livro atualizado com sucesso.");
        } else {
            System.out.println("Posição do campo inválida.");
        }
    }

    public static void editarLinha(String isbn)
    {

        Livro livro = procurar(isbn);
        if (livro == null) {
            System.out.println("Livro não encontrado.");
            return;
        }

        System.out.println("Editar os seguintes campos (pressione Enter para manter o valor atual):");


        String novoTitulo = Funcionalidades.lerString("Novo Título (atual: " + livro.titulo + "): ");
        if (novoTitulo.isEmpty()) novoTitulo = livro.titulo;


        String novaEditora = Funcionalidades.lerString("Nova Editora (atual: " + livro.editora + "): ");
        if (novaEditora.isEmpty()) novaEditora = livro.editora;


        String novaCategoria = Funcionalidades.lerString("Nova Categoria (atual: " + livro.categoria + "): ");
        if (novaCategoria.isEmpty()) novaCategoria = livro.categoria;


        String novoAnoEdicao = Funcionalidades.lerString("Novo Ano de Edição (atual: " + livro.anoEdicao + "): ");
        if (novoAnoEdicao.isEmpty()) novoAnoEdicao = String.valueOf(livro.anoEdicao);


        String novoISBN = Funcionalidades.lerString("Novo ISBN (atual: " + livro.isbn + "): ");
        if (novoISBN.isEmpty()) novoISBN = livro.isbn;


        String novosAutores = Funcionalidades.lerString("Novos Autores (atual: " + livro.autores + "): ");
        if (novosAutores.isEmpty()) novosAutores = livro.autores;


        String novaLinha = novoTitulo + "|" + novaEditora + "|" + novaCategoria + "|" + novoAnoEdicao + "|" + novoISBN + "|" + novosAutores;

        Ficheiros.atualizar(NOME_FICHEIRO, isbn, null, null, novaLinha);

        System.out.println("Livro atualizado com sucesso.");
    }


    private void introAnoEdicao() {
        do {
            anoEdicao = Funcionalidades.lerInt("Introduza o Ano de Edição:");
            if (anoEdicao < 0 || anoEdicao > 2025) {
                Funcionalidades.escreverString("Erro: Introduza um Ano de Edição válido (entre 0 e 2025)");
            }
        } while (anoEdicao < 0 || anoEdicao > 2025);
    }


    private void introIsbn() {

        do {
            isbn = Funcionalidades.lerString("Introduza o ISBN (com hífens):");
            if (!isbnValido(isbn)) {
                Funcionalidades.escreverString("Erro: ISBN inválido. Certifique-se de que seja um ISBN-10 ou ISBN-13 válido (com hífens).");
            }
        } while (!isbnValido(isbn));

        Funcionalidades.escreverString("ISBN válido: " + isbn);
    }

    private void introAutores() {
        int numeroAutores;

        do {
            numeroAutores = Funcionalidades.lerInt("Quantos autores tem o Livro?");
            if (numeroAutores <= 0) {
                Funcionalidades.escreverString("Erro: O número de autores deve ser maior que zero.");
            }
        } while (numeroAutores <= 0);


        for (int i = 1; i <= numeroAutores; i++) {
            String autor;
            do {
                autor = Funcionalidades.lerString("Introduza o nome do autor " + i + ":");
                if (autor.length() <= 3 || autor.length() >= 100) {
                    Funcionalidades.escreverString("Erro: O nome do autor deve ter entre 3 e 100 caracteres.");
                }
            } while (autor.length() <= 3 || autor.length() >= 100);


            if (!autores.isEmpty()) {
                autores += ", ";
            }
            autores += autor;
        }

    }

    public static void mostrarLivroPorISBN(String isbn) {
        Livro livro = procurar(isbn);

        if (livro == null) {
            System.out.println("Livro não encontrado.");
            return;
        }

        // Exibir os campos do livro de forma organizada
        System.out.println("===== Detalhes do Livro =====");
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Editora: " + livro.getEditora());
        System.out.println("Categoria: " + livro.getCategoria());
        System.out.println("Ano de Edição: " + livro.getAnoEdicao());
        System.out.println("ISBN: " + livro.getIsbn());
        System.out.println("Autores: " + livro.getAutores());
        System.out.println("=============================");
    }
}
