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
    public String getCodigo() {
        return issn;
    }

    public void setCodigo(String issn) {
        this.issn = issn;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public void exibeDados() {
        super.exibeDados(); // Exibe os dados comuns
        System.out.println("ISSN: " + issn);
        System.out.println("Data de Publicação: " + dataPublicacao);
    }
}
