import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public abstract class Ficheiros<T> {

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

    // Função para ler do ficheiro e retornar uma lista com cada linha
    public static List<String> ler(String nomeFicheiro) {
        //le varias linhas e retorna de um dado ficheiro em array
        List<String> lista = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(nomeFicheiro), "UTF-8")) {
            while (scanner.hasNextLine()) {
                lista.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
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
    //escreve o conteudo enviado
    public static <T> void escrever(String nomeFicheiro, T objeto, String formato) {
        String bibliotecaAtual = Biblioteca.getBibliotecaAtual();
        nomeFicheiro = bibliotecaAtual+"\\"+nomeFicheiro;
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

    // Função para atualizar o conteúdo do ficheiro, substituindo a lista
    public static void atualizar(String nomeFicheiro, String elementoPesquisa,
                                         String palavraAntiga, String palavraNova, String novaLinha){
        String bibliotecaAtual = Biblioteca.getBibliotecaAtual();
        nomeFicheiro = bibliotecaAtual+"\\"+nomeFicheiro;
        File arquivoOriginal = new File(nomeFicheiro);
        File arquivoTemp = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoOriginal));
             BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {

            String linha;
            while ((linha = reader.readLine()) != null) {
                // Verifica se a linha contém a chave de busca (ISBN)
                if (linha.contains(elementoPesquisa)) {
                    System.out.println("Registo Anterior"+linha);
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

    public static void apagar(String nomeFicheiro, String dado) {
        String bibliotecaAtual = Biblioteca.getBibliotecaAtual();
        nomeFicheiro = bibliotecaAtual+"\\"+nomeFicheiro;
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
                }else{
                    System.out.println("Registo Eliminado"+linha);
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
}
