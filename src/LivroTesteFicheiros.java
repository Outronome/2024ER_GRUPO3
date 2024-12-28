import java.io.Serializable;

public class LivroTesteFicheiros extends CategoriaTesteFicheiros implements Serializable {
    private int anoEdicao;
    private String isbn;
    private String autores;

    // Construtor
    public LivroTesteFicheiros(String titulo, String editora, String categoria, int anoEdicao, String isbn, String autores) {
        super(titulo, editora, categoria);  // Chama o construtor da classe super (Categoria)
        this.anoEdicao = anoEdicao;
        this.isbn = isbn;
        this.autores = autores;
    }

    // Getters e setters
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
    public Object[] getData() {
        return new Object[]{getTitulo(), getEditora(), getCategoria(), anoEdicao, isbn, autores};
    }

    @Override
    public String toString() {
        return "Livro{" +
               "titulo='" + getTitulo() + '\'' +
               ", editora='" + getEditora() + '\'' +
               ", categoria='" + getCategoria() + '\'' +
               ", anoEdicao=" + anoEdicao +
               ", isbn='" + isbn + '\'' +
               ", autores='" + autores + '\'' +
               '}';
    }
}
