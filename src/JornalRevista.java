// Classe JornalRevista, que herda de Obra
public class JornalRevista extends Obra {
    private String issn;
    private String dataPublicacao;

    // Construtor
    public JornalRevista(String titulo, String editora, String categoria, String issn, String dataPublicacao) {
        super(titulo, editora, categoria);
        this.issn = issn;
        this.dataPublicacao = dataPublicacao;
    }

    // Getters e Setters
    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
}
