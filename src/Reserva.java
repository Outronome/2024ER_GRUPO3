import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.io.File;

public class Reserva {
    String num;
    int nif;
    List<String> obras;
    String inicio;
    String registo;
    String fim;
    String obra;
    private static String FORMATO = "%s|%s|%s|%s|%s|%s%n";
    private static String NOME_FICHEIRO = "reservas.txt";

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

    public Reserva(String num, int nif, String obra, String inicio, String registo, String fim) {
        this.num = num;
        this.nif = nif;
        this.obra = obra;
        this.inicio = inicio;
        this.registo = registo;
        this.fim = fim;
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

    private void introInicio() {
        do {
            inicio = Funcionalidades.lerString("Introduza a Data de inicio de reserva (dd/MM/yyyy):");
            if (!validarData(inicio)){
                Funcionalidades.escreverString("Erro: Introduza uma data válida.");
                continue;
            }
            break;
        } while (true);
    }

    private void introFim() {
        do {
            fim = Funcionalidades.lerString("Introduza a Data de fim da reserva (dd/MM/yyyy):");
            if (!validarData(fim)) {
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

    public  void mostrarReserva(String num) {
        Reserva reserva = procurarReservas(num);

        if (reserva == null) {
            System.out.println("Livro não encontrado.");
            return;
        }

        // Exibir os campos do livro de forma organizada
        System.out.println("===== Detalhes do Livro =====");
        System.out.println("Numero de Reserva: " + reserva.getNum());
        System.out.println("Obra da Reserva: " + reserva.getObra());
        System.out.println("Data de inicio da Reserva: " + reserva.getInicio());
        System.out.println("Data de registo da Reserva: " + reserva.getRegisto());
        System.out.println("Data de Fim da Reserva: " + reserva.getFim());
        System.out.println("=============================");
    }


    public static boolean verificarDependencias(String identificador) {
        List<String> linhas = Ficheiros.ler("reservas.txt");
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
