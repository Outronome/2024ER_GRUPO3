import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

/**
 * Classe abstrata que fornece utilitários para manipulação de ficheiros genéricos.
 * Esta classe inclui métodos para ler ficheiros e para obter dados de objetos via reflexão.
 *
 * @param <T> O tipo de objeto manipulado por esta classe.
 */

public abstract class Ficheiros<T> {

    /**
     * Obtém os dados de um objeto invocando o método "getData" via reflexão.
     *
     * @param object O objeto do qual os dados serão extraídos.
     * @param <T>    O tipo do objeto.
     * @return Array de objetos contendo os dados retornados pelo método "getData" ou um array vazio se ocorrer um erro.
     */
    private static <T> Object[] getObjectData(T object) {
        try {

            // Usamos reflexão para buscar o método getData na classe do objeto
            Method method = object.getClass().getMethod("getData");

            // Chamamos o método "getData" e retornamos o valor
            return (Object[]) method.invoke(object);
        } catch (Exception e) {
            // Caso o método não exista ou ocorra algum erro, retornamos um array vazio
            e.printStackTrace();
            return new Object[0];
        }
    }

    /**
     * Lê o conteúdo de um ficheiro e retorna uma lista contendo cada linha como um elemento.
     * <p>
     * O caminho do ficheiro é ajustado de acordo com a biblioteca atual.
     *
     * @param nomeFicheiro O nome do ficheiro a ser lido.
     * @return Uma lista de strings contendo as linhas do ficheiro.
     * Retorna uma lista vazia se o ficheiro não for encontrado.
     */
    public static List<String> ler(String nomeFicheiro) {
        // Obtem o caminho da biblioteca atual e ajusta o caminho do ficheiro
        String bibliotecaAtual = Biblioteca.getBibliotecaAtual();
        nomeFicheiro = bibliotecaAtual + "\\" + nomeFicheiro;
        List<String> lista = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(nomeFicheiro), "UTF-8")) {
            // Lê o ficheiro linha por linha e adiciona cada linha à lista
            while (scanner.hasNextLine()) {
                lista.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            // Trata o erro caso o ficheiro não seja encontrado
            System.err.println("Erro: Ficheiro não encontrado - " + e.getMessage());
        }
        return lista;
    }

    // Função para escrever uma lista de objetos do tipo T no ficheiro
    /*public static <T> void escrever(String nomeFicheiro, T objeto, String formato) {
        try {
            File file = new File(nomeFicheiro);

            if (file.createNewFile()) {
                try (PrintWriter writer = new PrintWriter(
                        Files.newBufferedWriter(Paths.get(nomeFicheiro),
                                java.nio.file.StandardOpenOption.CREATE,
                                java.nio.file.StandardOpenOption.APPEND));
                     Formatter ficheiro = new Formatter(writer)) {
                    Object[] campos = getObjectData(objeto);
                    // Obtém os campos do objeto dinamicamente
                    /*Field[] campos = object.getClass().getDeclaredFields();

                    // Constrói a linha formatada com os valores dos campos
                    StringBuilder line = new StringBuilder();
                    for (Field field : fields) {
                        field.setAccessible(true); // Permite acesso a campos privados
                        line.append(field.get(object)).append(", ");
                    }

                    // Remove a última vírgula e espaço, e adiciona uma nova linha
                    if (line.length() > 2) {
                        line.setLength(line.length() - 2);
                    }
                    line.append(System.lineSeparator());

                    // Escreve a linha no arquivo
                    out.format("%s", line);*/
                /*} catch (FileNotFoundException e) {
                    System.err.println("Ficheiro não encontrado: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }*/

    /**
     * Escreve os dados de um objeto em um ficheiro utilizando um formato especificado.
     *
     * @param nomeFicheiro O nome do ficheiro onde os dados serão escritos.
     * @param objeto       O objeto cujos dados serão escritos no ficheiro.
     * @param formato      O formato utilizado para escrever os dados (compatível com Formatter).
     * @param <T>          O tipo do objeto.
     */

    public static <T> void escrever(String nomeFicheiro, T objeto, String formato) {
        String bibliotecaAtual = Biblioteca.getBibliotecaAtual();
        nomeFicheiro = bibliotecaAtual + "\\" + nomeFicheiro;
        try {
            File file = new File(nomeFicheiro);
            // Criar o arquivo, se não existir
            if (file.createNewFile() || file.exists()) {
                try (PrintWriter writer = new PrintWriter(
                        Files.newBufferedWriter(Paths.get(nomeFicheiro),
                                java.nio.file.StandardOpenOption.CREATE,
                                java.nio.file.StandardOpenOption.APPEND));
                     Formatter ficheiro = new Formatter(writer)) {
                    // Obter os dados do objeto usando o método getObjectData
                    Object[] campos = getObjectData(objeto);

                    // Escrever os dados no ficheiro com o formato fornecido
                    ficheiro.format(formato, campos);

                } catch (FileNotFoundException e) {
                    System.err.println("Ficheiro não encontrado: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }

    /**
     * Atualiza o conteúdo de um ficheiro, substituindo linhas ou palavras específicas.
     *
     * @param nomeFicheiro     O nome do ficheiro a ser atualizado.
     * @param elementoPesquisa O elemento utilizado para localizar a linha que deve ser atualizada.
     * @param palavraAntiga    A palavra antiga a ser substituída (pode ser null).
     * @param palavraNova      A palavra nova que substituirá a antiga (pode ser null).
     * @param novaLinha        Uma nova linha para substituir a linha inteira (opcional, pode ser null).
     *
     *
     */

    public static void atualizar(String nomeFicheiro, String elementoPesquisa,
                                 String palavraAntiga, String palavraNova, String novaLinha) {
        String bibliotecaAtual = Biblioteca.getBibliotecaAtual();
        nomeFicheiro = bibliotecaAtual + "\\" + nomeFicheiro;
        File arquivoOriginal = new File(nomeFicheiro);
        File arquivoTemp = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoOriginal));
             BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {

            String linha;
            while ((linha = reader.readLine()) != null) {
                // Verifica se a linha contém a chave de busca (ISBN)
                if (linha.contains(elementoPesquisa)) {
                    System.out.println("Registo Anterior: " + linha);
                    // Se uma nova linha for fornecida, substitui a linha inteira
                    if (novaLinha != null && !novaLinha.isEmpty()) {
                        linha = novaLinha;
                    } else {
                        // Caso contrário, substitui apenas a palavra
                        if (palavraAntiga != null && palavraNova != null && !palavraAntiga.isEmpty()) {
                            linha = linha.replaceAll("\\b" + palavraAntiga + "\\b", palavraNova);
                            System.out.println("Registo Atualizado: " + linha);
                        }
                    }
                }
                // Reescreve a linha (atualizada ou original)
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Substitui o arquivo original pelo arquivo temporário
        if (!arquivoOriginal.delete() || !arquivoTemp.renameTo(arquivoOriginal)) {
            try {
                throw new IOException("Erro ao substituir o arquivo original.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Remove linhas de um ficheiro que contenham um determinado dado.
     *
     * @param nomeFicheiro O nome do ficheiro a ser modificado.
     * @param dado         O dado utilizado para localizar e apagar as linhas correspondentes.
     */
    public static void apagar(String nomeFicheiro, String dado) {
        String bibliotecaAtual = Biblioteca.getBibliotecaAtual();
        nomeFicheiro = bibliotecaAtual + "\\" + nomeFicheiro;
        File arquivoOriginal = new File(nomeFicheiro);
        File arquivoTemp = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoOriginal));
             BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {

            String linha;
            while ((linha = reader.readLine()) != null) {
                // Verifica se a linha contém o ID que queremos apagar
                if (!linha.contains(dado)) {
                    // Se não encontrar, reescreve a linha no arquivo temporário
                    writer.write(linha);
                    writer.newLine();
                } else {
                    System.out.println("Registo Eliminado" + linha);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Substitui o arquivo original pelo arquivo temporário
        if (!arquivoOriginal.delete() || !arquivoTemp.renameTo(arquivoOriginal)) {
            try {
                throw new IOException("Erro ao substituir o arquivo original.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static int atualizarNum(String nomeFicheiro) {
        int maxNum = 0;
        Scanner scanner = null;
        String bibliotecaAtual = Biblioteca.getBibliotecaAtual();
         nomeFicheiro = bibliotecaAtual+"\\"+nomeFicheiro;
        try {
            scanner = new Scanner(new File(nomeFicheiro));

            while (scanner.hasNextLine()) {

                String linha = scanner.nextLine();

                // Divide a linha pelo delimitador '|'
                String[] campos = linha.split("\\|");

                // Extrai o valor de 'num' (assumindo que está na primeira posição)

                String numeros = campos[0].replaceAll("\\D", "");
                int numAtual = Integer.parseInt(numeros);
                // Atualiza o máximo se necessário
                if (numAtual > maxNum) {
                    maxNum = numAtual;
                }

            }}catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        scanner.close();
        return maxNum+1;
    }


}
