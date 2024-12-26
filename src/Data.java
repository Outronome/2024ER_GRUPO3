import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Scanner;

public class Data {

    public static LocalDateTime dataNow() {
        return LocalDateTime.now();  // Retorna a data e hora atuais
    }

    // Função para buscar a data fornecida pelo usuário (mês, dia e ano)
    public static LocalDate dataUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o mês (1-12): ");
        int mes = scanner.nextInt();
        System.out.print("Digite o dia (1-31): ");
        int dia = scanner.nextInt();
        System.out.print("Digite o ano (ex: 2024): ");
        int ano = scanner.nextInt();
        return LocalDate.of(ano, mes, dia);
    }


}
