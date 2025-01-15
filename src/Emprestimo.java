import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Objects;

import static java.time.LocalDate.parse;

public class Emprestimo {
    int num;
    String isbn;
    int nif;
    String inicio;
    String devolucaoPrevista;
    String devolucaoDefinitiva;
    private static final String FORMATO = "%d|%s|%d|%s|%s|%s%n";//falta fazer
    private static final String NOME_FICHEIRO = "emprestimos.txt";
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Emprestimo(int num, String isbn, int nif,
                      String inicio, String devolucaoPrevista, String devolucaoDefinitiva) {
        this.num = num;
        this.isbn = isbn;
        this.nif = nif;
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
        return isbn;
    }

    public void setObra(Obra obra) {
        this.isbn = isbn;
    }


    private void introNum() {
        num = generateId();
    }

    public String getNomeObra() {
        return isbn;
    }

    public void setNomeObra(String isbn) {
        this.isbn = isbn;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getDevolucaoPrevista() {
        return devolucaoPrevista;
    }

    public void setDevolucaoPrevista(String devolucaoPrevista) {
        this.devolucaoPrevista = devolucaoPrevista;
    }

    public String getDevolucaoDefinitiva() {
        return devolucaoDefinitiva;
    }

    public void setDevolucaoDefinitiva(String devolucaoDefinitiva) {
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
        this.nif = nif;
    }
    private void introObra() {
        Obra obra = new Obra("","","","");
        int cont = 1;
        boolean first = true;
        String codigo;
        do {
            if (!first) {
                cont = Funcionalidades.lerInt("Deseja sair?(1=não 0=sim)");
            }
            if (cont == 1) {

                if (first) {
                    obra.introCodigo("Introduza o ISBN/ISNN (com hífens) que quer pedir Emprestimo:");
                    obra = obra.verificarExiste();
                    System.out.println(obra.codigo);
                    first = false;
                } else {
                    obra = new Obra("","","","");
                    obra.introCodigo("Introduza o ISBN/ISNN (com hífens) correto que quer pedir Emprestimo:");
                    System.out.println(obra.codigo);
                    obra = obra.verificarExiste();
                }
            }
        } while (obra == null && cont == 1);
        isbn = obra.codigo;

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

    public static boolean verificarSeExiste(List<String> emprestimos, List<String> reservas, Emprestimo novoEmprestimo) {

            // Verificar sobreposição nos empréstimos
            if (emprestimos != null && !emprestimos.isEmpty()) {
                for (String emprestimo : emprestimos) {
                    String[] partes = emprestimo.split("\\|");
                    if (partes.length >= 5) {
                        String isbnEmprestimo = partes[1].trim();

                        if (isbnEmprestimo.equals(novoEmprestimo.isbn) &&
                                Objects.equals(partes[3], partes[5])) {
                                return true;
                        }
                    }
                }
            }
            //chamar função reserva para validar se existe alguma reserva com aquele isbn e datas

        if (reservas != null && !reservas.isEmpty()) {
            for (String reserva : reservas) {
                String[] partes2 = reserva.split("\\|");
                if (partes2.length >= 5) {
                    String isbnReserva = partes2[2].trim();

                    if (isbnReserva.equals(novoEmprestimo.isbn) && (LocalDate.parse(partes2[5],FORMATTER).isAfter(Data.dataNow()))){
                        return true;
                    }
                }
            }
        }

        return false; // Não existe sobreposição
    }


    public static Emprestimo registar() {
        Emprestimo tempEmprestimo = new Emprestimo(0, "", 0, "", "", "");
        Emprestimo newEmprestimo = null;
        Emprestimo emprestimo = null;
        boolean podeCriar = false;
        int cont = 1;
        boolean first = true;
        String devolucaoPrevista;
        String inicio;
        do {
            if (!first) {
                cont = Funcionalidades.lerInt("Deseja Continuar?(0=não 1=sim)");
            }
            if (cont == 1) {
                tempEmprestimo.introNum();
                tempEmprestimo.introUtente();
                tempEmprestimo.introObra();
                tempEmprestimo.inicio = String.valueOf(Data.dataNow());
                System.out.println(tempEmprestimo.inicio);

                do {
                    String fim = Funcionalidades.lerString("Introduza a Data prevista de entrega (dd/MM/yyyy):");


                        // Converter a data de inicio de "yyyy-MM-dd" para "dd/MM/yyyy"
                        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate parsedDate = LocalDate.parse(tempEmprestimo.inicio, originalFormatter);
                        tempEmprestimo.inicio = parsedDate.format(FORMATTER); // "dd/MM/yyyy"

                        // Validar e analisar a data de devolução
                        LocalDate dataInicio = LocalDate.parse(tempEmprestimo.inicio, FORMATTER);
                        LocalDate dataFim = LocalDate.parse(fim, FORMATTER);

                        // Verificar se a data final é anterior à inicial
                        if (dataFim.isBefore(dataInicio)) {
                            Funcionalidades.escreverString("Erro: A data prevista não pode ser anterior à data de início.");
                        } else {
                            tempEmprestimo.devolucaoPrevista = fim;
                            break; // Sai do loop se tudo estiver correto
                        }



                } while (true);



                newEmprestimo = new Emprestimo(tempEmprestimo.num, tempEmprestimo.isbn, tempEmprestimo.nif, tempEmprestimo.inicio, tempEmprestimo.devolucaoPrevista, tempEmprestimo.inicio);
                List<String> emprestimos;
                emprestimos = ler();
                List<String> reservas = Ficheiros.ler("reservas.txt");
                podeCriar = verificarSeExiste(emprestimos, reservas, newEmprestimo);
                System.out.println(podeCriar);

                if (podeCriar) {
                    Funcionalidades.escreverString("Não foi possivel inserir o emprestimo pois ele já existe");
                } else {
                    System.out.printf("Campos: num=%d, isbn=%s, nif=%d, inicio=%s, devolucaoPrevista=%s, devolucaoDefinitiva=%s%n",
                            newEmprestimo.getNum(), newEmprestimo.getObra(), newEmprestimo.getUtente(),
                            newEmprestimo.getInicio(), newEmprestimo.getDevolucaoPrevista(), newEmprestimo.getDevolucaoDefinitiva());
                    Ficheiros.escrever(NOME_FICHEIRO, newEmprestimo, FORMATO);
                }
                first = false;
            }
        } while (!podeCriar || cont == 1);
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
        return new Object[]{getNum(),getObra(),getNif(), getInicio(), getDevolucaoPrevista(), getDevolucaoDefinitiva()};
    }
    /*public List<Emprestimo> mostrar (){
        //código de enviar o range de emprestimos que estão no ficheiro caso não receba range enviar tudo
    }*/

    public void converterReserva(String codigo){
        Reserva existe = Reserva.procurarReservas(codigo);
        int num = generateId();
        if (existe == null) {
            System.out.println("Reserva não existe");
            return;
        }
        List<String> reservas = Ficheiros.ler("reservas.txt");
        for (String reserva : reservas)
        {
            if(reserva.contains(codigo)){
                Reserva converter = Reserva.procurarReservas(codigo);
                String[] partes = reserva.split("\\|");
                Emprestimo novoEmp = new Emprestimo(num,partes[2], converter.getNif(), converter.getInicio(), converter.getFim(), converter.getInicio());
                Ficheiros.escrever(NOME_FICHEIRO,novoEmp,FORMATO);
            }
        }


    }
}
