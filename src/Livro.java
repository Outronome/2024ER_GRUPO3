public class Livro extends Obra {
    private int anoEdicao;
    private String isbn;
    private String autores;

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

    public String getCodigo() {
        return isbn;
    }

    public void setCodigo(String isbn) {
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
}
