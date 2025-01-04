import java.util.List;

public class Livro extends Obra {
    private int anoEdicao;
    private String isbn;
    private String autores;
    private static String FORMATO = "%s|%s|%s|%d|%s|%s%n";
    private static String NOME_FICHEIRO = "livros.txt";
    // Construtor
    public Livro(String titulo, String editora, String categoria, int anoEdicao, String isbn, String autores) {
        super(titulo, editora, categoria);
        this.anoEdicao = anoEdicao;
        this.isbn = isbn;
        this.autores = autores;
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

    private int verficarLivro(Livro newLivro){

        int sucesso = 0;
        List<String> livros = Ficheiros.ler(Biblioteca.bibliotecaAtual+"\\"+NOME_FICHEIRO);
        for (String livro : livros) {
            String[] partes = livro.split("\\|");
            if (partes.length >= 5) {
                String isbnComparar = partes[4].trim();
                if (newLivro.isbn.equals(isbnComparar)) {
                    sucesso = 1;
                    break;
                }
            }
        }
        return sucesso;
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
        Ficheiros.escrever(NOME_FICHEIRO,newLivro,FORMATO);
        int sucesso = newLivro.verficarLivro(newLivro);
        if (sucesso == 1){
            Funcionalidades.escreverString("Livro registado com sucesso.");
        } else if (sucesso == 0) {
            Funcionalidades.escreverString("Livro não foi registado.");
        }
        return newLivro;
    }

    public static void eliminar(){
        String isbneliminado = Funcionalidades.lerString("Introduza o Isbn do livro que deseja apagar:");
        Ficheiros.apagar(NOME_FICHEIRO,isbneliminado);
    }

    private static List<String> ler(){
        return Ficheiros.ler(NOME_FICHEIRO);
    }

    public static Livro procurar(String isbn){
        List<String> livros;
        livros = ler();
        for (String livro : livros){
            String[] partes = livro.split("\\|");
            String[] partesFiltradas = { partes[3] };
            for (String parte : partesFiltradas) {
                if (parte.equals(isbn)){
                    return new Livro(partes[0],partes[1],partes[2],Integer.parseInt(partes[3]),partes[4],partes[5]);
                }
            }
        }
        System.out.println("Utilizador não encontrado");
        return null;
        //ao receber null deve pedir outra vez a leitura de um dado para ler e procurar outro livro
    }

    public static void editarCampo(String isbn, String palavraAntiga, String palavraNova, int posCampo){

        Livro livro = procurar(isbn);
        if (livro == null) {
            System.out.println("Livro não encontrado.");
            return;
        }

        String palavraAnterior = switch (posCampo) {
            case 0 -> livro.getTitulo();
            case 1 -> livro.getEditora();
            case 2 -> livro.getCategoria();
            case 3 -> String.valueOf(livro.getAnoEdicao());
            case 4 -> livro.getIsbn();
            case 5 -> livro.getAutores();
            default -> null;
        };

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
            if (anoEdicao < 0 || anoEdicao > 2024) {
                Funcionalidades.escreverString("Erro: Introduza um Ano de Edição válido (entre 1450 e 2024)");
            }
        } while (anoEdicao < 0 || anoEdicao > 2024);
    }

    private boolean validarIsbn10(String isbn) {
        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += (isbn.charAt(i) - '0') * (10 - i);
        }

        char ultimo = isbn.charAt(9);
        if (ultimo == 'X') {
            soma += 10; //
        } else if (Character.isDigit(ultimo)) {
            soma += (ultimo - '0');
        } else {
            return false;
        }

        return soma % 11 == 0;
    }
    private boolean validarIsbn13(String isbn){
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            int digito = isbn.charAt(i) - '0';
            soma += (i % 2 == 0) ? digito : digito * 3;
        }

        int checkDigitEsperado = (10 - (soma % 10)) % 10;

        int checkDigitReal = isbn.charAt(12) - '0';
        return checkDigitEsperado == checkDigitReal;
    }

    private boolean isbnValido(String isbn) {
        if (isbn == null) return false;

        // Regex para validar formatos
        String regexISBN10 = "^[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9X]$";
        String regexISBN13 = "^[0-9]{3}-[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9]$";

        if (isbn.matches(regexISBN10)) {
            return validarIsbn10(isbn.replace("-", ""));
        }
        if (isbn.matches(regexISBN13)) {
            return validarIsbn13(isbn.replace("-", ""));
        }
        return false;
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
}
