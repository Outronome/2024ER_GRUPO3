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

    public static boolean validarData(String data) {
        // Verifica se a data está no formato correto "dd/MM/yyyy"
        if (data == null || data.length() != 10 || data.charAt(2) != '/' || data.charAt(5) != '/') {
            return false;
        }

        try {
            // Extrai o dia, mês e ano da string
            int dia = Integer.parseInt(data.substring(0, 2));
            int mes = Integer.parseInt(data.substring(3, 5));
            int ano = Integer.parseInt(data.substring(6, 10));

            // Valida o mês
            if (mes < 1 || mes > 12) {
                return false;
            }

            // Valida os dias de acordo com o mês
            int diasNoMes = diasNoMes(mes, ano);
            if (dia < 1 || dia > diasNoMes) {
                return false;
            }

            return true; // A data é válida
        } catch (NumberFormatException e) {
            return false; // Não conseguiu converter para número
        }
    }

    private static int diasNoMes(int mes, int ano) {
        switch (mes) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31; // Meses com 31 dias
            case 4: case 6: case 9: case 11:
                return 30; // Meses com 30 dias
            case 2:
                // Verifica se é ano bissexto para fevereiro
                return (anoBissexto(ano)) ? 29 : 28;
            default:
                return 0; // Mês inválido (não deveria chegar aqui)
        }
    }

    private static boolean anoBissexto(int ano) {
        // Regras para anos bissextos:
        // - Divisível por 4 e (não divisível por 100 ou divisível por 400)
        return (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0));
    }

    private void introDataPublicacao() {
        do {
            dataPublicacao = Funcionalidades.lerString("Introduza a Data de Publicação (dd/MM/yyyy):");
            if (!validarData(dataPublicacao)){
                Funcionalidades.escreverString("Erro: Introduza uma data válida.");
                continue;
            }
            break;
        } while (true);
    }

    public static JornalRevista procurar(String isbn){
        List<String> jornalRevistas;
        jornalRevistas = ler();
        for (String jornalRevista : jornalRevistas){
            String[] partes = jornalRevista.split("\\|");
            String[] partesFiltradas = { partes[3] };
            for (String parte : partesFiltradas) {
                if (parte.equals(isbn)){
                    return new JornalRevista(partes[0],partes[1],partes[2],partes[3],partes[4]);
                }
            }
        }
        System.out.println("Utilizador não encontrado");
        return null;
        //ao receber null deve pedir outra vez a leitura de um dado para ler e procurar outro livro
    }

    private static List<String> ler(){
        return Ficheiros.ler(NOME_FICHEIRO);
    }

    public static void editarCampo(String issn, String palavraAntiga, String palavraNova, int posCampo){

        JornalRevista jornalRevista = procurar(issn);
        if (jornalRevista == null) {
            System.out.println("Livro não encontrado.");
            return;
        }

        String palavraAnterior = switch (posCampo) {
            case 0 -> jornalRevista.getTitulo();
            case 1 -> jornalRevista.getEditora();
            case 2 -> jornalRevista.getCategoria();
            case 3 -> jornalRevista.getIssn();
            case 4 -> jornalRevista.getDataPublicacao();
            default -> null;
        };

        if (palavraAntiga != null) {
            Ficheiros.atualizar(NOME_FICHEIRO, issn, palavraAntiga, palavraNova, "");
            System.out.println("Atualizado com sucesso.");
        } else {
            System.out.println("Posição do campo inválida.");
        }
    }


}
