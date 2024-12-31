// Classe base Obra
public class Obra {
    protected String titulo;
    protected String editora;
    protected String categoria;

    // Construtor
    public Obra(String titulo, String editora, String categoria) {
        this.titulo = titulo;
        this.editora = editora;
        this.categoria = categoria;
    }

    // Getters e Setters
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

    public String getCodigo() {
        return null;
    }

    public void exibeDados() {
        System.out.println("Título: " + titulo);
        System.out.println("Editora: " + editora);
        System.out.println("Categoria: " + categoria);
        System.out.println("Código: " + getCodigo());
    }

}


