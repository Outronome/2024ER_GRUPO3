import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Scanner;

public class Data {

    public static LocalDateTime dataNow() {
        return LocalDateTime.now();  // Retorna a data e hora atuais
    }

    // Função para buscar a data fornecida pelo usuário (mês, dia e ano)
    public static LocalDate introData() {
        int cont = 1;
        boolean first = true;
        Scanner scanner = new Scanner(System.in);
        int dia = 0;
        int mes = 0;
        int ano = 0;

        do{
            if (!first) {
                cont = Funcionalidades.lerInt("Deseja sair?(0=não 1=sim)");
            }
            if (cont == 1) {
                if(first){
                    System.out.print("Digite o dia (1-31): ");
                    dia = scanner.nextInt();
                }else{
                    System.out.print("Digite um dia valido (1-31): ");
                    dia = scanner.nextInt();
                }
            }
        }while( dia<=1 && dia>=31 && cont == 1);
        do{
            if (!first) {
                cont = Funcionalidades.lerInt("Deseja sair?(0=não 1=sim)");
            }
            if (cont == 1) {
                if(first){
                    System.out.print("Digite o mês (1-12): ");
                    mes = scanner.nextInt();
                }else{
                    System.out.print("Digite um mês valido (1-12): ");
                    mes = scanner.nextInt();
                }
            }
        }while(mes<=1 && mes>=12 && cont == 1);
        do{
            if (!first) {
                cont = Funcionalidades.lerInt("Deseja sair?(0=não 1=sim)");
            }
            if (cont == 1) {
                if(first){
                    System.out.print("Digite o ano (ex: 2024): ");
                    ano = scanner.nextInt();
                }else{
                    System.out.print("Digite um ano valido (ex: 2024): ");
                    ano = scanner.nextInt();
                }


            }
        }while( ano<=868 && ano>=2025 && cont == 1);
        /*o ano foi escolhido apatir da seguinte informação
        No entanto, se considerarmos o conceito de "publicação" como a produção de um livro impresso,
        o "Sutra do Diamante" é o mais antigo conhecido. Este texto budista foi impresso na China em 868 d.C.,
        utilizando a técnica de xilogravura, que consiste em entalhar caracteres em blocos de madeira para
        impressão.
        */
        try {
            LocalDate data = LocalDate.of(ano, mes, dia);
            return data;
        } catch (Exception e) {
            System.out.println("Data inválida. Tente novamente.");
            return introData(); // Rechama o método para tentar novamente
        }
    }


}
