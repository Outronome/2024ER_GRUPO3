import java.util.List;

// Classe JornalRevista, que herda de Obra
public class JornalRevista extends Obra {
    private String issn;
    private String dataPublicacao;
    private static String NOME_FICHEIRO = "JornalRevista.txt";
    private static String FORMATO = "%s|%s|%s|%s|%s%n";
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

    public void exibeDados() {
        super.exibeDados(); // Exibe os dados comuns
        System.out.println("ISSN: " + issn);
        System.out.println("Data de Publicação: " + dataPublicacao);
    }

    public static void eliminar(){
        String isnneliminado = Funcionalidades.lerString("Introduza o Isnn do jornal ou reista que deseja apagar:");
        Ficheiros.apagar(NOME_FICHEIRO,isnneliminado);
    }

    public Object[] getData() {
        return new Object[]{getTitulo(), getEditora(), getCategoria(), getIssn(),getDataPublicacao()};
    }

    private int verficarJornalRevista(JornalRevista newJornalRevista){

        int sucesso = 0;
        List<String> JornalRevistas = Ficheiros.ler(Biblioteca.bibliotecaAtual+"\\"+NOME_FICHEIRO);
        for (String JornalRevista : JornalRevistas) {
            String[] partes = JornalRevista.split("\\|");
            if (partes.length >= 5) {
                String issnComparar = partes[3].trim();
                if (newJornalRevista.issn.equals(issnComparar)) {
                    sucesso = 1;
                    break;
                }
            }
        }
        return sucesso;
    }

    public static JornalRevista registar(){
        JornalRevista tempJornalRevista = new JornalRevista("","","","","");
        tempJornalRevista.introTitulo();
        tempJornalRevista.introEditora();
        tempJornalRevista.introCategoria();
        tempJornalRevista.introIssn();
        tempJornalRevista.introDataPublicacao();

        JornalRevista newJornalRevista = new JornalRevista(tempJornalRevista.titulo, tempJornalRevista.editora, tempJornalRevista.categoria,tempJornalRevista.issn, tempJornalRevista.dataPublicacao);
        Ficheiros.escrever(NOME_FICHEIRO,newJornalRevista,FORMATO);
        int sucesso = newJornalRevista.verficarJornalRevista(newJornalRevista);
        if (sucesso == 1){
            Funcionalidades.escreverString("Registado com sucesso.");
        } else if (sucesso == 0) {
            Funcionalidades.escreverString("Não foi registado.");
        }
        return newJornalRevista;
    }

    private void introIssn() {
        do {
            issn = Funcionalidades.lerString("Introduza o ISSN do Livro (formato 1234-567X):");


            if (!issn.matches("^\\d{4}-\\d{3}[\\dX]$")) {
                Funcionalidades.escreverString("Erro: O ISSN deve estar no formato 1234-567X.");
                continue;
            }

            if (!validarIssn(issn)) {
                Funcionalidades.escreverString("Erro: O ISSN introduzido é inválido.");
                continue;
            }

            break;
        } while (true);
    }

    private boolean validarIssn(String issn) {
        String issnSemHifen = issn.replace("-", "");
        String primeirosSete = issnSemHifen.substring(0, 7);
        char ultimoDigito = issnSemHifen.charAt(7);
        // Calcular a soma ponderada dos primeiros 7 dígitos
        int soma = 0;
        for (int i = 0; i < 7; i++) {
            int digito = Character.getNumericValue(primeirosSete.charAt(i));
            soma += digito * (8 - i); // Peso decrescente de 8 a 2
        }
        // Calcular o dígito de verificação
        int resto = soma % 11;
        int digitoVerificador = (11 - resto) % 11;
        char digitoEsperado = (digitoVerificador == 10) ? 'X' : Character.forDigit(digitoVerificador, 10);
        return ultimoDigito == digitoEsperado;
    }

    private void introDataPublicacao() {

        dataPublicacao = Funcionalidades.lerString("Data;");

    }


}
