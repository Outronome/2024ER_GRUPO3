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
}
