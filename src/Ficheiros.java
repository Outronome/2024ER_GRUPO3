import java.io.*;
import java.util.Scanner;

public class Ficheiros {
    public ler(){
        return;
    }
    public escrever(){
        return;
    }
    public atualizar(){
        return;
    }
    public apagar(){
        return;
    }
    static void lerFicheiroComBufferedReader(String nomeFicheiro) {
        System.out.println("###################################");
        System.out.println("## Leitura usando BufferedReader ##");
        System.out.println("###################################");
        System.out.println(""); // Dar um espaçamento de uma linha

        // Outra forma para a instrução try é especificar a instrução específica
        // que pode gerar o erro de runtime
        try (BufferedReader ler = new BufferedReader( new FileReader("c:\\teste.txt")))
        {
            String linha;
            String[] campos;

            // Neste caso, temos de tentar ler a próxima linha
            // e verificar se a operação foi bem sucedida (não retornou null)
            while ((linha = ler.readLine()) != null) {
                // Separa o valor de linha em várias strings,
                // separando pelo caracter "|". Como o caracter "|" é especial,
                // é necessário especificar que o caracter não é um RegEx.
                campos = linha.split("\\|");

                // Imprime a linha
                System.out.println(linha);

                // Ciclo "for each". Neste caso, a variável c assume o valor
                // de cada elemento do array campos.
                for (String c:campos) {
                    // Imprime cada campo separadamente
                    System.out.println(c);
                }
            }
            ler.close();

        }
        catch (IOException ex) {
            // A classe BufferedReader lança uma exceção IOException
            // caso não consiga abrir o ficheiro
        }
    }

    /**
     * Este método usa as classes BufferedWriter e FileWriter para escrever
     * texto num ficheiro.
     * @param nomeFicheiro Caminho completo do ficheiro a ser escrito
     */
    static void gravarFicheiroComBufferedWriter(String nomeFicheiro) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFicheiro))) {
            // Cria (sobrepõe) sempre o ficheiro.
            // Para acrescentar ao ficheiro, seria necessário alterar a linha anterior,
            // acrescentando o parâmetro "append" ao construtor do FileWriter:
            // (...) new FileWriter(nomeFicheiro, true)


            // O ciclo seguinte pede ao utilizador para introduzir texto
            // a gravar no ficheiro.
            // Para sair, deve carregar em Enter sem escrever nada.
            String texto;
            Scanner ler = new Scanner(System.in);
            System.out.println("Introduza texto a gravar no ficheiro");
            System.out.println("Para sair, carregue em Enter sem introduzir mais nada");
            do {
                texto = ler.nextLine();

                // Escreve o texto que leu no ficheiro
                writer.write(texto);

                // Escreve uma quebra de linha
                writer.write(System.lineSeparator());

            } while (!texto.equals(""));

            writer.close();
        }
        catch (IOException ex) {

        }

    }
}

