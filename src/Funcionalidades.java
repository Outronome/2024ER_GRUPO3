import java.util.Scanner;

public class Funcionalidades {
    public static int lerInt(int range, String[] pergunta){
        Scanner ler = new Scanner(System.in);
        int val = -1  ;
        do {
            escreverString(pergunta);
            val = ler.nextInt();
        }while (val<=0 && val >= range);
        return val;
    }
    public static void escreverString(String[] lista){
        for (String elemento : lista) {
            System.out.println(elemento);
        }
    }

}
