import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Reserva implements Ficheiros.linhaConvertida{
    String num;
    int nif;
    List<String> obras;
    String inicio;
    String registo;
    String fim;
    String obra;
    private static String FORMATO = "%s|%s|%s|%s|%s|%s%n";
    private static String NOME_FICHEIRO = "reservas.txt";
    static List<Reserva> reservas = new ArrayList<>();

    public Reserva(String num, int nif, String obra, String inicio, String registo, String fim) {
        this.num = num;
        this.nif = nif;
        this.obra = obra;
        this.inicio = inicio;
        this.registo = registo;
        this.fim = fim;
    }
    public Reserva() {}

    public static List<Reserva> setReservas() {
        reservas = Reserva.lerTodosReservas();
        return reservas;
    }
    public static void guardarReservasFicheiro(){
        for(Reserva reserva : reservas) {
            Ficheiros.escrever(NOME_FICHEIRO,reserva,FORMATO);
        }
    }

    @Override
    public void fromLine(String line) {
        // Supondo que os dados estão separados por "|"
        String[] parts = line.split("\\|");

        if (parts.length == 6) {
            try {
                this.num = parts[0];
                this.nif = Integer.parseInt(parts[1]);
                this.obra = parts[2];
                this.inicio = parts[3];
                this.registo = parts[4];
                this.fim = parts[5];
            } catch (NumberFormatException e) {
                // Tratamento para garantir que os dados sejam válidos
                System.err.println("Erro de formato em linha: " + line);
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Formato da linha inválido: " + line);
        }
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "num='" + num + '\'' +
                ", nif=" + nif +
                ", obra='" + obra + '\'' +
                ", inicio='" + inicio + '\'' +
                ", registo='" + registo + '\'' +
                ", fim='" + fim + '\'' +
                '}';
    }

    // Método para ler todas as reservas
    public static List<Reserva> lerTodosReservas() {
        Ficheiros<Reserva> reader = new Ficheiros<>(Reserva.class);
        return reader.lerMemoria(NOME_FICHEIRO);
    }


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getNif() {
        return nif;
    }

    public void setUtente(Utente utente) {
        this.nif = nif;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getRegisto() {
        return registo;
    }

    public void setRegisto(String registo) {
        this.registo = registo;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }


    public Reserva registar () {
        num= "R"+Ficheiros.atualizarNum(NOME_FICHEIRO);
        Reserva tempReserva = new Reserva(num,0,null,null,null,null);
         obras = introObras();
        tempReserva.introNif();
        tempReserva.introInicio();
        tempReserva.introRegisto();
        tempReserva.introFim();
        for (String obra : obras) {
            Reserva newReserva = new Reserva(num, tempReserva.nif, obra, tempReserva.inicio, tempReserva.registo, tempReserva.fim);
            Ficheiros.escrever(NOME_FICHEIRO,newReserva,FORMATO);
        }
       return null;
    }
    public Object[] getData() {
        return new Object[]{getNum(), getNif(),getObra(), getInicio(), getRegisto(), getFim()};
    }

    private static List<String> ler(){
        return Ficheiros.ler(NOME_FICHEIRO);
    }

    public static ArrayList<Reserva> procurarListaReservas(String num){
        List<String> reservas;
        ArrayList<Reserva> listaReservas = new ArrayList<>();
        reservas = ler();
        for (String reserva : reservas) {
            String[] partes = reserva.split("\\|");
            String[] partesFiltradas = { partes[0] };
            for (String parte : partesFiltradas) {
                if (parte.equals(num)){
                    Reserva reservaEncontrada = new Reserva(partes[0], Integer.parseInt(partes[1]), partes[2], partes[3], partes[4], partes[5]);
                    listaReservas.add(reservaEncontrada);
                }
            }
        }
        if (listaReservas.isEmpty()) {
            System.out.println("Reserva não encontrada");
            return null;
        }
        return listaReservas;
        //ao receber null deve pedir outra vez a leitura de um dado para ler e procurar outro livro
    }
    public static Reserva procurarReservas(String num){
        List<String> reservas;
        reservas = ler();
        for (String reserva : reservas) {
            String[] partes = reserva.split("\\|");
            String[] partesFiltradas = { partes[0] };
            for (String parte : partesFiltradas) {
                if (parte.equals(num)){
                    return new Reserva(partes[0],Integer.parseInt(partes[1]),partes[2],partes[3],partes[4],partes[5]);
                }
            }
        }
        System.out.println("Reserva não encontrada");
        return null;
        //ao receber null deve pedir outra vez a leitura de um dado para ler e procurar outro livro
    }

    public static Boolean procurar(String codigo,String nomeFicheiroObra){
        List<String> Reservas;
        String[] partesFiltradas;
        Reservas = Ficheiros.ler(nomeFicheiroObra);
        for (String Reserva : Reservas){
            String[] partes = Reserva.split("\\|");
            if(nomeFicheiroObra.equals("JornalRevista.txt")) {
                partesFiltradas = new String[]{partes[3]};
            }
            else {
                partesFiltradas = new String[]{partes[4]};
            }
            for (String parte : partesFiltradas) {
                if (parte.equals(codigo)) {
                    return true;
                }
            }
        }
        System.out.println("Não encontrado");
        return false;
        //ao receber null deve pedir outra vez a leitura de um dado para ler e procurar outro Reserva
    }

    public List<String> introObras() {
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
                valido = procurar(codigo, "JornalRevista.txt");
            } else {
                valido=procurar(codigo, "livros.txt");
            }

            if (valido) {
                Obras.add(codigo);
            }else System.out.println("Obra não encontrada tente novamente.");

        }

    return Obras;
    }

    private void introRegisto(){
        LocalDateTime ldt = LocalDateTime.now();
        registo = ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private void introNif (){
        int cont = 1;
        boolean first = true;

        do {
            if (!first) {
                cont = Funcionalidades.lerInt("Deseja sair?(0=não 1=sim)");
            }
            if (cont == 1) {
                if (first) {
                    nif =Funcionalidades.lerInt("Introduza o Nif do utente que deseja Associar");
                    if (Utente.procurar(String.valueOf(nif)) != null) {
                    break;
                    } else continue;


                }
            }

        } while (cont == 1);
    }



    private void introInicio() {
        do {
            inicio = Funcionalidades.lerString("Introduza a Data de inicio de reserva (dd/MM/yyyy):");
            if (!Funcionalidades.validarData(inicio)){
                Funcionalidades.escreverString("Erro: Introduza uma data válida.");
                continue;
            }
            break;
        } while (true);
    }

    private void introFim() {
        do {
            fim = Funcionalidades.lerString("Introduza a Data de fim da reserva (dd/MM/yyyy):");
            if (!Funcionalidades.validarData(fim)) {
                Funcionalidades.escreverString("Erro: Introduza uma data válida no formato dd/MM/yyyy.");
                continue;
            }

            LocalDate dataInicio = LocalDate.parse(inicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate dataFim = LocalDate.parse(fim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (dataFim.isBefore(dataInicio)) {
                Funcionalidades.escreverString("Erro: A data de fim não pode ser anterior à data de início.");
            } else {
                break;
            }
        } while (true);
    }



    public void editarCampo(String num, String palavraAntiga, String palavraNova, int posCampo){

        Reserva reserva = procurarReservas(num);
        if (reserva == null) {
            System.out.println("Reserva não encontrado.");
            return;
        }

        String palavraAnterior = switch (posCampo) {
            case 0 -> reserva.getNum();
            case 1 -> String.valueOf(reserva.getNif());
            case 2 -> reserva.getObra();
            case 3 -> reserva.getInicio();
            case 4 -> reserva.getRegisto();
            case 5 -> reserva.getFim();
            default -> null;
        };

        if (palavraAntiga != null) {
            Ficheiros.atualizar(NOME_FICHEIRO, num, palavraAntiga, palavraNova, "");
            System.out.println("Reserva atualizado com sucesso.");
        } else {
            System.out.println("Posição do campo inválida.");
        }
    }
    public void eliminar (){
        String numEliminado = Funcionalidades.lerString("Introduza o numero da reserva que deseja apagar (R*):");
        Ficheiros.apagar(NOME_FICHEIRO, numEliminado);

    }

    public void pesquisarReserva(String num) {
        ArrayList<Reserva> reservas = procurarListaReservas(num);

        if (reservas.isEmpty()) {
            System.out.println("Reserva não encontrado.");
            return;
        }
        for (Reserva reserva : reservas) {
            String[] reservaEscrever =  {   "===== Detalhes do Livro =====",
                    "Numero de Reserva: " + reserva.getNum(),
                    "Obra da Reserva: " + reserva.getObra(),
                    "Data de inicio da Reserva: " + reserva.getInicio(),
                    "Data de registo da Reserva: " + reserva.getRegisto(),
                    "Data de Fim da Reserva: " + reserva.getFim(),
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
    public void mostrarReservas() {
        List<String> reservas = ler();
        if (reservas.isEmpty()) {
            Funcionalidades.escreverString("Nenhuma reserva encontrada.");
            return;
        }

        int totalReservas = reservas.size();
        int paginaAtual = 0; // Índice da página atual
        int tamanhoPagina = 5; // Mostrar até 50 registros por vez
        int opcao;

        do {
            int inicio = paginaAtual * tamanhoPagina;
            int fim = Math.min(inicio + tamanhoPagina, totalReservas);

            // Mostrar reservas da página atual
            Funcionalidades.escreverString("\n===== Exibindo reservas (" + (inicio + 1) + " a " + fim + " de " + totalReservas + ") =====");
            for (int i = inicio; i < fim; i++) {
                String reserva = reservas.get(i);
                String[] partes = reserva.split("\\|");
                Funcionalidades.escreverString("Reserva " + (i + 1) + ":");
                Funcionalidades.escreverString("Numero de Reserva: " + partes[0]);
                Funcionalidades.escreverString("NIF: " + partes[1]);
                Funcionalidades.escreverString("Obra: " + partes[2]);
                Funcionalidades.escreverString("Inicio: " + partes[3]);
                Funcionalidades.escreverString("Registo: " + partes[4]);
                Funcionalidades.escreverString("Fim: " + partes[5]);
                Funcionalidades.escreverString("-------------------------");
            }

            // Determinar opções de navegação
            if (fim < totalReservas && inicio > 0) {
                // Opções para avançar e retroceder
                opcao = Funcionalidades.lerInt("1: Próximas 5 reservas | 2: Retroceder 5 reservas | 0: Sair");
            } else if (fim < totalReservas) {
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



    public static boolean verificarDependencias(String identificador) {
        List<String> linhas = ler();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (String linha : linhas) {
            String[] partes = linha.split("\\|");
            String dataFinal = partes[5].trim();
            LocalDate hoje = LocalDate.now(); // Data atual
            LocalDate fim = LocalDate.parse(dataFinal, formatter); // Converte a dataFinal
            // Ajuste conforme o formato das linhas no arquivo
            if (linha.contains(identificador.trim())&&fim.isAfter(hoje)) {
                return false;
            }
        }

        return true;
    }

}
