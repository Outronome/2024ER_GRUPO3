import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Funcionalidades <T> {

    public void exibirPaginado(List<T> lista, int tamanhoPagina) {
        if (lista.isEmpty()) {
            Funcionalidades.escreverString("Nenhum registro encontrado.");
            return;
        }

        int totalRegistros = lista.size();
        int paginaAtual = 0; // Índice da página atual
        int opcao;

        do {
            int inicio = paginaAtual * tamanhoPagina;
            int fim = Math.min(inicio + tamanhoPagina, totalRegistros);

            // Mostrar registros da página atual
            Funcionalidades.escreverString("\n===== Exibindo registros (" + (inicio + 1) + " a " + fim + " de " + totalRegistros + ") =====");
            for (int i = inicio; i < fim; i++) {
                T objeto = lista.get(i);
                Funcionalidades.escreverString("Índice " + (i + 1) + ":");
                Funcionalidades.escreverString(objeto.toString()); // Chama `toString` do objeto genérico
                Funcionalidades.escreverString("-------------------------");
            }

            // Determinar opções de navegação
            if (fim < totalRegistros && inicio > 0) {
                // Opções para avançar e retroceder
                opcao = Funcionalidades.lerInt("1: Próximos registros | 2: Retroceder registros | 0: Sair");
            } else if (fim < totalRegistros) {
                // Somente opção para avançar
                opcao = Funcionalidades.lerInt("1: Próximos registros | 0: Sair");
            } else if (inicio > 0) {
                // Somente opção para retroceder
                opcao = Funcionalidades.lerInt("2: Retroceder registros | 0: Sair");
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
    public static int lerInteiros(int range, String[] pergunta){
        Scanner ler = new Scanner(System.in);
        int val = -1  ;
        do {
            escreverStrings(pergunta);
            val = ler.nextInt();
        }while (val<=0 && val >= range);
        return val;
    }
    public static int lerOpcoesMenus(int range, String[] pergunta){
        Scanner ler = new Scanner(System.in);
        int val = -1  ;
        do {
            escreverStrings(pergunta);
            val = ler.nextInt();
        }while (val<=0 && val >= range);
        return val;
    }
    public static String lerString(String pergunta){
        Scanner ler = new Scanner(System.in);
        escreverString(pergunta);
        return ler.nextLine();
    }
    public static int lerInt(String pergunta){
        Scanner ler = new Scanner(System.in);

            escreverString(pergunta);
            if (ler.hasNextInt()){
                int val = ler.nextInt();
                return val;
            }else
            {System.out.println("Introduza um inteiro");
                return -1;
            }



    }
    public static void escreverString(String texto){
        System.out.println(texto);
    }
    public static void escreverStrings(String[] lista){
        for (String elemento : lista) {
            System.out.println(elemento);
        }
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

}
