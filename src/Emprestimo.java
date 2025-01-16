import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.time.LocalDate.parse;

public class Emprestimo implements Ficheiros.linhaConvertida{
    int num;
    String isbn;
    int nif;
    String inicio;
    String devolucaoPrevista;
    String devolucaoDefinitiva;
    private static final String FORMATO = "%d|%s|%d|%s|%s|%s%n";//falta fazer
    private static final String NOME_FICHEIRO = "emprestimos.txt";
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static List<Emprestimo> emprestimos = new ArrayList<>();

    public Emprestimo(int num, String isbn, int nif,
                      String inicio, String devolucaoPrevista, String devolucaoDefinitiva) {
        this.num = num;
        this.isbn = isbn;
        this.nif = nif;
        this.inicio = inicio;
        this.devolucaoPrevista = devolucaoPrevista;
        this.devolucaoDefinitiva = devolucaoDefinitiva;
    }
    // Construtor sem argumentos
    public Emprestimo() {}

    public static List<Emprestimo> setEmprestimos() {
        emprestimos = Emprestimo.lerTodosEmprestimos();
        return emprestimos;
    }
    public static void guardarEmprestimosFicheiro(){
        for(Emprestimo emprestimo : emprestimos) {
            Ficheiros.escrever(NOME_FICHEIRO,emprestimo,FORMATO);
        }
    }

    // Método para ler uma linha do ficheiro e preencher os dados do empréstimo
    @Override
    public void fromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 6) {
            this.num = Integer.parseInt(parts[0]);
            this.isbn = parts[1];
            this.nif = Integer.parseInt(parts[2]);
            this.inicio = parts[3];
            this.devolucaoPrevista = parts[4];
            this.devolucaoDefinitiva = parts[5];
        } else {
            throw new IllegalArgumentException("Formato da linha inválido: " + line);
        }
    }

    // Método para gerar uma string de representação do empréstimo
    @Override
    public String toString() {
        return String.format("Emprestimo {Num=%d, ISBN='%s', NIF=%d, Início='%s', Devolução Prevista='%s', Devolução Definitiva='%s'}",
                num, isbn, nif, inicio, devolucaoPrevista, devolucaoDefinitiva);
    }
    public static List<Emprestimo> lerTodosEmprestimos() {
        Ficheiros<Emprestimo> reader = new Ficheiros<>(Emprestimo.class);
        return reader.lerMemoria(NOME_FICHEIRO);
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
        if(Objects.equals(devolucaoPrevista, inicio)) {
            return "Ainda não foi entregue";
        }
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
    public static List<String> introObras() {
        Scanner scanner = new Scanner(System.in);
        List<String> Obras = new ArrayList<>();
        boolean valido=false;

        System.out.println("Introduza os códigos das obras (digite 'FIM' para terminar):");

        while (true) {
            System.out.print("Código da obra: ");
            String codigo = scanner.nextLine().trim();

            // Terminar o loop se o utilizador digitar 'FIM'
            if (codigo.equalsIgnoreCase("FIM")) {
                break;
            }
            if (codigo.matches("^\\d{4}-\\d{3}[\\dX]$")) {
                valido = Reserva.procurar(codigo, "JornalRevista.txt");
            } else {
                valido= Reserva.procurar(codigo, "livros.txt");
            }

            if (valido) {
                Obras.add(codigo);
            }else System.out.println("Obra não encontrada tente novamente.");

        }

        return Obras;
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
        boolean podeCriar = false;
        int cont = 1;
        boolean first = true;

        do {
            if (!first) {
                cont = Funcionalidades.lerInt("Deseja Continuar?(0=não 1=sim)");
            }
            if (cont == 1) {
                tempEmprestimo.introNum();
                tempEmprestimo.introUtente();
                List<String> obras = introObras();
                tempEmprestimo.inicio = String.valueOf(Data.dataNow());


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

                List<String> emprestimos;
                emprestimos = ler();
                List<String> reservas = Ficheiros.ler("reservas.txt");


                    for (String obra : obras)
                    {
                        newEmprestimo = new Emprestimo(tempEmprestimo.num, obra, tempEmprestimo.nif, tempEmprestimo.inicio, tempEmprestimo.devolucaoPrevista, tempEmprestimo.inicio);
                        podeCriar = verificarSeExiste(emprestimos, reservas, newEmprestimo);
                        if (podeCriar)
                            Ficheiros.escrever(NOME_FICHEIRO, newEmprestimo, FORMATO);
                        else {Funcionalidades.escreverString("Não foi possivel inserir o emprestimo da obra " +obra+" pois ele já existe");}
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
    public static ArrayList<Emprestimo> procurarListaEmprestimos(String num){
        List<String> emprestimos;
        ArrayList<Emprestimo> listaEmprestimos = new ArrayList<>();
        emprestimos = ler();
        for (String emprestimo : emprestimos) {
            String[] partes = emprestimo.split("\\|");
            String[] partesFiltradas = { partes[0] };
            for (String parte : partesFiltradas) {
                if (parte.equals(num)){
                    Emprestimo emprestimoEncontrado = new Emprestimo(Integer.parseInt(partes[0]), partes[1], Integer.parseInt(partes[2]), partes[3], partes[4], partes[5]);
                    listaEmprestimos.add(emprestimoEncontrado);
                }
            }
        }
        if (listaEmprestimos.isEmpty()) {
            System.out.println("Reserva não encontrada");
            return null;
        }
        return listaEmprestimos;
        //ao receber null deve pedir outra vez a leitura de um dado para ler e procurar outro livro
    }
    public void pesquisarEmprestimo(String num) {
        ArrayList<Emprestimo> emprestimos = procurarListaEmprestimos(num);

        if (emprestimos.isEmpty()) {
            System.out.println("Reserva não encontrado.");
            return;
        }
        for (Emprestimo emprestimo : emprestimos) {
            String[] reservaEscrever =  {   "===== Detalhes do Livro =====",
                    "Numero de Emprestimo: " + emprestimo.getNum(),
                    "Obra do Emprestimo: " + emprestimo.getObra(),
                    "Data de inicio do Emprestimo: " + emprestimo.getInicio(),
                    "Data de registo da Emprestimo: " + emprestimo.getDevolucaoPrevista(),
                    "Data de Fim da Emprestimo: " + emprestimo.getDevolucaoDefinitiva(),
                    "============================="
            };
            Funcionalidades.escreverStrings(reservaEscrever);
        }
    }

    /*public void mostrarReservas(){
        List<String> revervas = ler();
        for (String reverva : revervas) {
            Funcionalidades.lerInt("Reserva " + reverva + ":");
        }
    }*/
    public void mostrarEmprestimos() {
        List<String> emprestimos = ler();
        if (emprestimos.isEmpty()) {
            Funcionalidades.escreverString("Nenhuma reserva encontrada.");
            return;
        }

        int totalEmprestimos = emprestimos.size();
        int paginaAtual = 0; // Índice da página atual
        int tamanhoPagina = 5; // Mostrar até 50 registros por vez
        int opcao;

        do {
            int inicio = paginaAtual * tamanhoPagina;
            int fim = Math.min(inicio + tamanhoPagina, totalEmprestimos);

            // Mostrar reservas da página atual
            Funcionalidades.escreverString("\n===== Exibindo reservas (" + (inicio + 1) + " a " + fim + " de " + totalEmprestimos + ") =====");
            for (int i = inicio; i < fim; i++) {
                String reserva = emprestimos.get(i);
                String[] partes = reserva.split("\\|");
                Funcionalidades.escreverString("Indice " + (i + 1) + ":");
                Funcionalidades.escreverString("Numero do Emprestimo: " + partes[0]);
                Funcionalidades.escreverString("ISBN: " + partes[1]);
                Funcionalidades.escreverString("NIF: " + partes[2]);
                Funcionalidades.escreverString("Inicio: " + partes[3]);
                Funcionalidades.escreverString("Devolução Prevista: " + partes[4]);
                Funcionalidades.escreverString("Devolução Definitiva: " + partes[5]);
                Funcionalidades.escreverString("-------------------------");
            }

            // Determinar opções de navegação
            if (fim < totalEmprestimos && inicio > 0) {
                // Opções para avançar e retroceder
                opcao = Funcionalidades.lerInt("1: Próximas 5 reservas | 2: Retroceder 5 reservas | 0: Sair");
            } else if (fim < totalEmprestimos) {
                // Somente opção para avançar
                opcao = Funcionalidades.lerInt("1: Próximas 5 reservas | 0: Sair");
            } else if (inicio > 0) {
                // Somente opção para retroceder
                opcao = Funcionalidades.lerInt("2: Retroceder 5 reservas | 0: Sair");
            } else {
                // Nenhuma navegação necessária
                opcao = Funcionalidades.lerInt("0: Sair");
            }

            // Atualizar página com base na opção escolhida
            if (opcao == 1) {
                paginaAtual++;
            } else if (opcao == 2) {
                paginaAtual--;
            }

        } while (opcao != 0);
    }

    public static void listarUtentesComAtraso(int diasAtraso) {
        List<String> emprestimos = Ficheiros.ler(NOME_FICHEIRO);
        LocalDate hoje = LocalDate.now();

        if (emprestimos == null || emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo encontrado.");
            return;
        }

        System.out.println("Utentes com atraso superior a " + diasAtraso + " dias:");

        for (String emprestimo : emprestimos) {
            String[] partes = emprestimo.split("\\|");
            if (partes.length >= 5) {
                String nif = partes[2];
                String devolucaoPrevistaStr = partes[4];
                String devolucaoDefinitivaStr = partes[5];

                LocalDate devolucaoPrevista = LocalDate.parse(devolucaoPrevistaStr, FORMATTER);
                LocalDate devolucaoDefinitiva = devolucaoDefinitivaStr.equals("Ainda não foi entregue")
                        ? hoje
                        : LocalDate.parse(devolucaoDefinitivaStr, FORMATTER);

                long atraso = devolucaoDefinitiva.toEpochDay() - devolucaoPrevista.toEpochDay();

                if (atraso > diasAtraso) {
                    System.out.println("NIF: " + nif + " | Atraso: " + atraso + " dias");
                }
            }
        }
    }



    public static void apresentarItemMaisRequisitado() {
            Scanner scanner = new Scanner(System.in);
            LocalDate inicio;
            LocalDate fim;

            try {
                System.out.print("Digite a data de início (dd/MM/yyyy): ");
                String dataInicioStr = scanner.nextLine();
                inicio = LocalDate.parse(dataInicioStr, FORMATTER);

                System.out.print("Digite a data de fim (dd/MM/yyyy): ");
                String dataFimStr = scanner.nextLine();
                fim = LocalDate.parse(dataFimStr, FORMATTER);
            } catch (Exception e) {
                System.out.println("Erro: Formato de data inválido. Use o formato dd/MM/yyyy.");
                return;
            }

            List<String> emprestimos = Ficheiros.ler(NOME_FICHEIRO);
            List<String> reservas = Ficheiros.ler("reservas.txt");

            if ((emprestimos == null || emprestimos.isEmpty()) && (reservas == null || reservas.isEmpty())) {
                System.out.println("Nenhum empréstimo ou reserva encontrada.");
                return;
            }

            ArrayList<String> itens = new ArrayList<>();
            ArrayList<Integer> contagens = new ArrayList<>();

            // Contar itens em empréstimos dentro do intervalo
            if (emprestimos != null) {
                for (String emprestimo : emprestimos) {
                    String[] partes = emprestimo.split("\\|");
                    if (partes.length >= 4) {
                        LocalDate dataInicio = LocalDate.parse(partes[3], FORMATTER);
                        if ((dataInicio.isEqual(inicio) || dataInicio.isAfter(inicio)) &&
                                (dataInicio.isEqual(fim) || dataInicio.isBefore(fim))) {
                            String isbn = partes[1];
                            int index = itens.indexOf(isbn);
                            if (index >= 0) {
                                contagens.set(index, contagens.get(index) + 1);
                            } else {
                                itens.add(isbn);
                                contagens.add(1);
                            }
                        }
                    }
                }
            }

            // Contar itens em reservas dentro do intervalo
            if (reservas != null) {
                for (String reserva : reservas) {
                    String[] partes = reserva.split("\\|");
                    if (partes.length >= 4) {
                        LocalDate dataInicio = LocalDate.parse(partes[3], FORMATTER);
                        if ((dataInicio.isEqual(inicio) || dataInicio.isAfter(inicio)) &&
                                (dataInicio.isEqual(fim) || dataInicio.isBefore(fim))) {
                            String isbn = partes[2];
                            int index = itens.indexOf(isbn);
                            if (index >= 0) {
                                contagens.set(index, contagens.get(index) + 1);
                            } else {
                                itens.add(isbn);
                                contagens.add(1);
                            }
                        }
                    }
                }
            }
            // Determinar o item mais requisitado
            String itemMaisRequisitado = null;
            int maxRequisicoes = 0;
            for (int i = 0; i < itens.size(); i++) {
                if (contagens.get(i) > maxRequisicoes) {
                    maxRequisicoes = contagens.get(i);
                    itemMaisRequisitado = itens.get(i);
                }
            }
            if (itemMaisRequisitado != null) {
                System.out.println("Item mais requisitado: " + itemMaisRequisitado + " | Total de requisições: " + maxRequisicoes);
            } else {
                System.out.println("Nenhum item foi requisitado no intervalo especificado.");
            }
        }

    public static void apresentarTempoMedioEmprestimos() {
        Scanner scanner = new Scanner(System.in);
        LocalDate inicio;
        LocalDate fim;

        try {
            System.out.print("Digite a data de início (dd/MM/yyyy): ");
            String dataInicioStr = scanner.nextLine();
            inicio = LocalDate.parse(dataInicioStr, FORMATTER);

            System.out.print("Digite a data de fim (dd/MM/yyyy): ");
            String dataFimStr = scanner.nextLine();
            fim = LocalDate.parse(dataFimStr, FORMATTER);
        } catch (Exception e) {
            System.out.println("Erro: Formato de data inválido. Use o formato dd/MM/yyyy.");
            return;
        }

        List<String> emprestimos = Ficheiros.ler(NOME_FICHEIRO);

        if (emprestimos == null || emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo encontrado.");
            return;
        }

        ArrayList<Long> tempos = new ArrayList<>();

        // Calcular tempos de empréstimos dentro do intervalo
        for (String emprestimo : emprestimos) {
            String[] partes = emprestimo.split("\\|");
            if (partes.length >= 5) {
                LocalDate dataInicio = LocalDate.parse(partes[3], FORMATTER);
                LocalDate dataDevolucao = LocalDate.parse(partes[5], FORMATTER);

                if ((dataInicio.isEqual(inicio) || dataInicio.isAfter(inicio)) &&
                        (dataInicio.isEqual(fim) || dataInicio.isBefore(fim))) {

                    if (dataInicio.equals(dataDevolucao)) {
                        continue; // Ignorar empréstimos ainda não devolvidos
                    }

                    long dias = dataDevolucao.toEpochDay() - dataInicio.toEpochDay();
                    tempos.add(dias);
                }
            }
        }

        // Calcular média dos tempos de empréstimos
        if (tempos.isEmpty()) {
            System.out.println("Nenhum empréstimo encontrado no intervalo especificado ou ainda não devolvido.");
            return;
        }

        long soma = 0;
        for (long tempo : tempos) {
            soma += tempo;
        }

        double media = (double) soma / tempos.size();
        System.out.printf("Tempo médio dos empréstimos: %.2f dias.%n", media);
    }

    public static void apresentarTotalEmprestimos() {
        Scanner scanner = new Scanner(System.in);
        LocalDate inicio;
        LocalDate fim;

        try {
            System.out.print("Digite a data de início (dd/MM/yyyy): ");
            String dataInicioStr = scanner.nextLine();
            inicio = LocalDate.parse(dataInicioStr, FORMATTER);

            System.out.print("Digite a data de fim (dd/MM/yyyy): ");
            String dataFimStr = scanner.nextLine();
            fim = LocalDate.parse(dataFimStr, FORMATTER);
        } catch (Exception e) {
            System.out.println("Erro: Formato de data inválido. Use o formato dd/MM/yyyy.");
            return;
        }

        List<String> emprestimos = Ficheiros.ler(NOME_FICHEIRO);

        if (emprestimos == null || emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo encontrado.");
            return;
        }

        ArrayList<Integer> numerosUnicos = new ArrayList<>();

        // Contar empréstimos únicos dentro do intervalo
        for (String emprestimo : emprestimos) {
            String[] partes = emprestimo.split("\\|");
            if (partes.length >= 5) {
                LocalDate dataInicio = LocalDate.parse(partes[3], FORMATTER);

                if ((dataInicio.isEqual(inicio) || dataInicio.isAfter(inicio)) &&
                        (dataInicio.isEqual(fim) || dataInicio.isBefore(fim))) {
                    int numEmprestimo = Integer.parseInt(partes[0]);

                    // Verificar se o número já está na lista
                    if (!numerosUnicos.contains(numEmprestimo)) {
                        numerosUnicos.add(numEmprestimo);
                    }
                }
            }
        }

        System.out.println("Total de empréstimos únicos no intervalo especificado: " + numerosUnicos.size());
    }

    public static Emprestimo procurarEmprestimos(String num) {
        List<String> emprestimos = Ficheiros.ler(NOME_FICHEIRO);
        if (emprestimos == null || emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo encontrado.");
            return null;
        }

        for (String emprestimo : emprestimos) {
            String[] partes = emprestimo.split("\\|");
            if (partes.length >= 6 && partes[0].equals(num)) {
                return new Emprestimo(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        Integer.parseInt(partes[2]),
                        partes[3],
                        partes[4],
                        partes[5]
                );
            }
        }

        System.out.println("Empréstimo não encontrado.");
        return null;
    }

    public static boolean validarExistencia(String valor, String tipo) {
        List<String> itens;
        String nomeFicheiro;

        switch (tipo) {
            case "ISBN" -> nomeFicheiro = "livros.txt";
            case "ISSN" -> nomeFicheiro = "JornalRevista.txt";
            case "NIF" -> nomeFicheiro = "utentes.txt";
            default -> {
                System.out.println("Tipo inválido.");
                return false;
            }
        }
        itens = Ficheiros.ler(nomeFicheiro);
        if (itens == null || itens.isEmpty()) {
            System.out.println("Nenhum dado encontrado no arquivo " + nomeFicheiro);
            return false;
        }
        for (String item : itens) {
            if (item.contains(valor)) {
                return true;
            }
        }
        System.out.println(tipo + " não encontrado: " + valor);
        return false;
    }


    public static void editarCampo(String num, String palavraAntiga, String palavraNova, int posCampo){

        Emprestimo emprestimo = procurarEmprestimos(num);
        if (emprestimo == null) {
            System.out.println("Empréstimo não encontrado.");
            return;
        }

        switch (posCampo) {
            case 1 -> {
                if (!palavraNova.matches("\\d{4}-\\d{3}[\\dX]")&& !palavraNova.matches("^[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9X]$")&&!palavraNova.matches("^[0-9]{3}-[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9]$")) {}

                if (!validarExistencia(palavraNova, "ISBN")) {
                    System.out.println("Erro: ISBN não encontrado no sistema ou já está emprestado.");
                    return;
                }

                Emprestimo novoEmprestimo = new Emprestimo(
                        emprestimo.getNum(),
                        palavraNova,
                        emprestimo.getNif(),
                        emprestimo.getInicio(),
                        emprestimo.getDevolucaoPrevista(),
                        emprestimo.getDevolucaoDefinitiva()
                );
                List<String> emprestimos;
                emprestimos = ler();
                List<String> reservas = Ficheiros.ler("reservas.txt");

                if (verificarSeExiste(emprestimos, reservas, novoEmprestimo)) {
                    System.out.println("Erro: Já existe um empréstimo ou reserva para o ISBN fornecido no mesmo intervalo.");
                    return;
                }
            }
            case 2 -> {
                if (!palavraNova.matches("\\d{9}")) {
                    System.out.println("Erro: NIF inválido.");
                    return;
                }

                if (!validarExistencia(palavraNova, "NIF")) {
                    System.out.println("Erro: NIF não encontrado no sistema.");
                    return;
                }
            }
            case 3, 4 -> {
                try {
                    LocalDate.parse(palavraNova, FORMATTER);
                } catch (Exception e) {
                    System.out.println("Erro: A data deve estar no formato dd/MM/yyyy.");
                    return;
                }
            }
            case 5 -> {
                if (!palavraNova.isBlank() && !palavraNova.equals(emprestimo.getInicio())) {
                    try {
                        LocalDate devolucao = LocalDate.parse(palavraNova, FORMATTER);
                        LocalDate inicio = LocalDate.parse(emprestimo.getInicio(), FORMATTER);
                        if (!devolucao.isAfter(inicio)) {
                            System.out.println("Erro: A data de devolução deve ser posterior à data de início.");
                            return;
                        }
                    } catch (Exception e) {
                        System.out.println("Erro: A data deve estar no formato dd/MM/yyyy.");
                        return;
                    }
                }
            }
            default -> {
                System.out.println("Erro: Campo inválido especificado.");
                return;
            }
        }


        if (palavraAntiga != null) {
            Ficheiros.atualizar(NOME_FICHEIRO, num, palavraAntiga, palavraNova, "");
            System.out.println("Empréstimo atualizado com sucesso.");
        } else {
            System.out.println("Posição do campo inválida.");
        }
    }
}







