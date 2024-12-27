import java.io.Serializable;

public class Categoria implements Serializable {
    private String titulo;
    private String editora;
    private String categoria;

    // Construtor
    public Categoria(String titulo, String editora, String categoria) {
        this.titulo = titulo;
        this.editora = editora;
        this.categoria = categoria;
    }

    // Getters e setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public Object[] getData() {
        return new Object[]{titulo, editora, categoria};
    }
}
