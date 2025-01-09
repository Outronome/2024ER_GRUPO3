import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static java.time.LocalDate.parse;

public class Emprestimo {
    int num;
    String nomeObra;
    int nif;
    LocalDate inicio;
    LocalDate devolucaoPrevista;
    LocalDate devolucaoDefinitiva;
    private static final String FORMATO = "%d|%s|%d|%d%n";//falta fazer
    private static final String NOME_FICHEIRO = "emprestimos.txt";

    public Emprestimo(int num, String nomeObra, int nif,
                      LocalDate inicio, LocalDate devolucaoPrevista, LocalDate devolucaoDefinitiva) {
        this.num = num;
        this.nif = nif;
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

    public int getUtente() {
        return nif;
    }

    public void setUtente(int nif) {
        this.nif = nif;
    }

    public String getObra() {
        return nomeObra;
    }

    public void setObra(Obra obra) {
        this.nomeObra = nomeObra;
    }


    private void introNum() {
        num = generateId();
    }

    public String getNomeObra() {
        return nomeObra;
    }

    public void setNomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getDevolucaoPrevista() {
        return devolucaoPrevista;
    }

    public void setDevolucaoPrevista(LocalDate devolucaoPrevista) {
        this.devolucaoPrevista = devolucaoPrevista;
    }

    public LocalDate getDevolucaoDefinitiva() {
        return devolucaoDefinitiva;
    }

    public void setDevolucaoDefinitiva(LocalDate devolucaoDefinitiva) {
        this.devolucaoDefinitiva = devolucaoDefinitiva;
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
                    utente = Utente.procurar(String.valueOf(nif));
                    first = false;
                } else {
                    nif = utente.introNif("Introduza o Nif de um o utente que esteja registado e que deseje Associar");
                    utente = Utente.procurar(String.valueOf(nif));
                }
            }
        } while (utente == null && cont == 1);
        this.nif = utente.getNif();
    }
    private void introObra() {
        Obra obra = new Obra("","","","");
        int cont = 1;
        boolean first = true;
        int nif = 0;
        String codigo;
        do {
            if (!first) {
                cont = Funcionalidades.lerInt("Deseja sair?(0=não 1=sim)");
            }
            if (cont == 1) {

                if (first) {
                    obra.introCodigo("Introduza o ISBN/ISNN (com hífens) que quer pedir Emprestimo:");
                    Obra obraTemp = new Obra(obra.codigo,"","","");
                    obra = obraTemp.pesquisar();
                    first = false;
                } else {
                    obra.introCodigo("Introduza o ISBN/ISNN (com hífens) correto que quer pedir Emprestimo:");
                    Obra obraTemp = new Obra(obra.codigo,"","","");
                    obra = obraTemp.pesquisar();
                }
            }
        } while (obra == null && cont == 1);
        nomeObra = obra.titulo;
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
    private static boolean datasConflito(LocalDate inicio1, LocalDate fim1, LocalDate inicio2, LocalDate fim2) {
        return !(fim1.isBefore(inicio2) || inicio1.isAfter(fim2));
    }
    public static boolean verificarSeExiste(List<String> emprestimos, List<String> reservas, Emprestimo novoEmprestimo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Verificar sobreposição nos empréstimos
        if (emprestimos != null && !emprestimos.isEmpty()) {
            for (String emprestimo : emprestimos) {
                try {
                    String[] partes = emprestimo.split("\\|");

                    if (partes.length >= 5) {
                        String nomeObra = partes[2].trim();
                        LocalDate inicio = LocalDate.parse(partes[3].trim(), formatter);
                        LocalDate devolucaoPrevista = LocalDate.parse(partes[4].trim(), formatter);

                        System.out.println("Comparando empréstimo: " + nomeObra + " com novo empréstimo: " + novoEmprestimo.nomeObra);
                        System.out.println("Comparando datas: " + inicio + " - " + devolucaoPrevista + " com " + novoEmprestimo.inicio + " - " + novoEmprestimo.devolucaoPrevista);

                        if (nomeObra.equals(novoEmprestimo.nomeObra) &&
                                !novoEmprestimo.inicio.isAfter(devolucaoPrevista) &&
                                !novoEmprestimo.devolucaoPrevista.isBefore(inicio)) {
                            System.out.println("Sobreposição detectada em empréstimo.");
                            return true; // Existe sobreposição com um empréstimo
                        }
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Erro ao processar data em empréstimo: " + emprestimo + " - " + e.getMessage());
                }
            }
        }

        // Verificar sobreposição nas reservas
        if (reservas != null && !reservas.isEmpty()) {
            for (String reserva : reservas) {
                try {
                    String[] partes = reserva.split("\\|");

                    if (partes.length >= 6) {
                        String nomeObra = partes[2].trim();
                        LocalDate dataInicio = LocalDate.parse(partes[3].trim(), formatter);
                        LocalDate dataFim = LocalDate.parse(partes[4].trim(), formatter);

                        System.out.println("Comparando reserva: " + nomeObra + " com novo empréstimo: " + novoEmprestimo.nomeObra);
                        System.out.println("Comparando datas: " + dataInicio + " - " + dataFim + " com " + novoEmprestimo.inicio + " - " + novoEmprestimo.devolucaoPrevista);

                        if (nomeObra.equals(novoEmprestimo.nomeObra) &&
                                !novoEmprestimo.inicio.isAfter(dataFim) &&
                                !novoEmprestimo.devolucaoPrevista.isBefore(dataInicio)) {
                            System.out.println("Sobreposição detectada em reserva.");
                            return true; // Existe sobreposição com uma reserva
                        }
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Erro ao processar data em reserva: " + reserva + " - " + e.getMessage());
                }
            }
        }

        return false; // Não existe sobreposição
    }






    /*private static Emprestimo verificarSeExiste(List<String> emprestimos,List<String> reservas, String dado){
        //o dado tem que ser o nif o nome ou o contacto
        for (String emprestimo : emprestimos){
            String[] partes = emprestimo.split("\\|");
            String[] partesFiltradas = { partes[0], partes[1], partes[3] };
            for (String parte : partesFiltradas) {
                if (parte.equals(dado)){
                    return new Emprestimo(Integer.parseInt(partes[0]),partes[1],Integer.parseInt(partes[2]),Integer.parseInt(partes[3]));
                }
            }
        }
        System.out.println("Utilizador não encontrado");
        return null;
    }*/
    /*private int verficarEmprestimo(Emprestimo newEmprestimo){
        int sucesso = 0;
        //verifica se existe no ficheiro
        List<String> emprestimos = Ficheiros.ler(NOME_FICHEIRO);
        for ( String emprestimo : emprestimos ) {
            String[] partes = emprestimo.split("\\|");  // Usa expressão regular para dividir por "|"

            if (!newEmprestimo.nomeObra.equals(partes[1].trim()) && !String.valueOf(newEmprestimo.nif).equals(partes[2].trim()) && !String.valueOf(newEmprestimo.inicio).equals(partes[3].trim()) && !String.valueOf(newEmprestimo.devolucaoPrevista).equals(partes[4].trim()) && !String.valueOf(newEmprestimo.devolucaoDefinitiva).equals(partes[5].trim())) {
                sucesso = 1;
                break;
            }
        }
        return sucesso;
    }*/

    public static Emprestimo registar() {
        Emprestimo tempEmprestimo = new Emprestimo(0, null, 0, null, null, null);
        Emprestimo newEmprestimo = null;
        Emprestimo emprestimo = null;
        boolean podeCriar = false;
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
                tempEmprestimo.inicio = LocalDate.from(Data.dataNow());
                do{
                    Funcionalidades.escreverString("Escreva Data Prevista de Entrega");
                    tempEmprestimo.devolucaoPrevista = Data.introData();
                }while (tempEmprestimo.devolucaoPrevista.isBefore(tempEmprestimo.inicio) );
                /*do{
                    tempEmprestimo.devolucaoDefinitiva = Data.introData();
                }while (tempEmprestimo.devolucaoDefinitiva.isBefore(tempEmprestimo.inicio));*/



                newEmprestimo = new Emprestimo(tempEmprestimo.num, tempEmprestimo.nomeObra, tempEmprestimo.nif, tempEmprestimo.inicio, tempEmprestimo.devolucaoPrevista, tempEmprestimo.inicio);
                List<String> emprestimos;
                emprestimos = ler();
                List<String> reservas = Ficheiros.ler("reservas.txt");
                podeCriar = verificarSeExiste(emprestimos, reservas, newEmprestimo);
                if (!podeCriar) {
                    Funcionalidades.escreverString("Não foi possivel inserir o emprestimo pois ele já existe");
                }
                first = false;
            }
        } while (!podeCriar && cont == 1);
        Ficheiros.escrever(NOME_FICHEIRO, newEmprestimo, FORMATO);
        /*int sucesso = newEmprestimo.verficarEmprestimo(newEmprestimo);
        if (sucesso == 1) {*/
        Funcionalidades.escreverString("Emprestimo registado com sucesso.");
        /*} else if (sucesso == 0) {
            Funcionalidades.escreverString("Erro:Emprestimo não foi registado.");
        }*/
        return newEmprestimo;
    }

    public String editar() {
        //código de editar o emprestimo no ficheiro
        return "Resultado";
    }

    public Object[] getData() {
        return new Object[]{getNif(), getInicio(), getDevolucaoPrevista(), getDevolucaoDefinitiva()};
    }
    /*public List<Emprestimo> mostrar (){
        //código de enviar o range de emprestimos que estão no ficheiro caso não receba range enviar tudo
    }*/
}
