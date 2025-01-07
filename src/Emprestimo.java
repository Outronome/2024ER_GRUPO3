import java.util.List;

public class Emprestimo {
    int num;
    String nomeObra;
    Utente utente;
    Data inicio;
    Data devolucaoPrevista;
    Data devolucaoDefinitiva;
    private static final String FORMATO = "%d|%s|%d|%d%n";//falta fazer
    private static final String NOME_FICHEIRO = "emprestimos.txt";

    public Emprestimo(int num, String nomeObra, Utente utente,
                      Data inicio, Data devolucaoPrevista, Data devolucaoDefinitiva) {
        this.num = num;
        this.utente = utente;
        this.nomeObra = nomeObra;
        this.inicio = inicio;
        this.devolucaoPrevista = devolucaoPrevista;
        this.devolucaoDefinitiva = devolucaoDefinitiva;
    }

    //ler toda a informação de um emprestimo e colocar em memória
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getObra() {
        return nomeObra;
    }

    public void setObra(Obra obra) {
        this.nomeObra = nomeObra;
    }

    public Data getInicio() {
        return inicio;
    }

    public void setInicio(Data inicio) {
        this.inicio = inicio;
    }

    public Data getDevolucaoPrevista() {
        return devolucaoPrevista;
    }

    public void setDevolucaoPrevista(Data devolucaoPrevista) {
        this.devolucaoPrevista = devolucaoPrevista;
    }

    public Data getDevolucaoDefinitiva() {
        return devolucaoDefinitiva;
    }

    public void setDevolucaoDefinitiva(Data devolucaoDefinitiva) {
        this.devolucaoDefinitiva = devolucaoDefinitiva;
    }

    private void introNum() {
        num = generateId();
    }

    private void introUtente() {
        Utente utente = new Utente(0, "", 0, 0);
        int cont = 1;
        boolean first = true;
        int nif = 0;
        do {
            if (!first) {
                cont = Funcionalidades.lerInt("Deseja sair?(0=não 1=sim)");
            }
            if (cont == 1) {
                if (first) {
                    nif = utente.introNif("Introduza o Nif do utente que deseja Associar");
                    Utente.procurar(String.valueOf(nif));
                    first = false;
                } else {
                    nif = utente.introNif("Introduza o Nif de um o utente que esteja registado e que deseje Associar");
                    Utente.procurar(String.valueOf(nif));
                }
            }
        } while (utente == null && cont == 1);
    }

    private void introObra() {

    }

    private void introDatas() {

    }

    private int generateId() {
        List<String> lista = ler();
        if (lista == null) {
            return 0; // Retorna 0 se a lista for nula
        }
        return lista.size();
    }

    private Utente PesqUtente(String pergunta) {
        Utente utente = null;
        utente.introNif(pergunta);
        return utente;
    }

    private static List<String> ler() {
        return Ficheiros.ler(NOME_FICHEIRO);
    }
/*
    public static Emprestimo registar() {
        Emprestimo tempEmprestimo = new Emprestimo(0, null, null, null, null, null);
        Emprestimo newEmprestimo = null;
        Emprestimo emprestimo = null;
        int cont = 1;
        boolean first = true;
        do {
            if (!first) {
                cont = Funcionalidades.lerInt("Deseja sair?(0=não 1=sim)");
            }
            if (cont == 1) {
                tempEmprestimo.introNum();
                tempEmprestimo.introUtente();
                tempEmprestimo.introObra();
              //  tempEmprestimo.IntroDatas();


                newEmprestimo = new Emprestimo(tempEmprestimo.num, tempEmprestimo.nomeObra, tempEmprestimo.utente, tempEmprestimo.inicio, tempEmprestimo.devolucaoPrevista, tempEmprestimo.devolucaoDefinitiva);
                List<String> emprestimos;
                emprestimos = ler();
               // emprestimo = verificarSeExiste(emprestimos, String.valueOf(newEmprestimo.num));
                if (emprestimo != null) {
                    Funcionalidades.escreverString("Não foi possivel inserir o emprestimo pois ele já existe");
                }
                first = false;
            }
        } while (emprestimo != null && cont == 1);
        Ficheiros.escrever(NOME_FICHEIRO, newEmprestimo, FORMATO);
        int sucesso = newEmprestimo.verficarEmprestimo(newEmprestimo);
        if (sucesso == 1) {
            Funcionalidades.escreverString("Emprestimo registado com sucesso.");
        } else if (sucesso == 0) {
            Funcionalidades.escreverString("Erro:Emprestimo não foi registado.");
        }
        return newEmprestimo;
    }

    public String editar() {
        //código de editar o emprestimo no ficheiro
        return "Resultado";
    }

    public Object[] getData() {
        return new Object[]{getTitulo(), getEditora(), getCategoria(), getAnoEdicao(), getIsbn(), getAutores()};
    }
    /*public List<Emprestimo> mostrar (){
        //código de enviar o range de emprestimos que estão no ficheiro caso não receba range enviar tudo
    }*/
}
