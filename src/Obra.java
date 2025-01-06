import java.util.List;

// Classe base Obra
public class Obra {
    protected String titulo;
    protected String editora;
    protected String categoria;
    protected static String NOME_FICHEIRO;

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

    protected void introTitulo() {
        do {
            titulo = Funcionalidades.lerString("Introduza o Título:");
            if (titulo.length() <= 3 || titulo.length() >= 100) {
                Funcionalidades.escreverString("Erro: Introduza um Título válido (entre 3 e 100 caracteres)");
            }
        } while (titulo.length() <= 3 || titulo.length() >= 100);
    }
    protected void introEditora() {
        do {
            editora = Funcionalidades.lerString("Introduza os Editores:");
            if (editora.length() <= 3 || editora.length() >= 100) {
                Funcionalidades.escreverString("Erro: Introduza um nome de Editores válido (entre 3 e 100 caracteres)");
            }
        } while (editora.length() <= 3 || editora.length() >= 100);
    }

    protected void introCategoria() {do {
        categoria = Funcionalidades.lerString("Introduza a Categoria:");
        if (categoria.length() <= 3 || categoria.length() >= 100) {
            Funcionalidades.escreverString("Erro: Introduza uma Categoria válida (entre 3 e 100 caracteres)");
        }
    } while (categoria.length() <= 3 || categoria.length() >= 100);}


}


