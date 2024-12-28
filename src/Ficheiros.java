import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public abstract class Ficheiros<T> {
    private File ficheiro;
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
    // Função para verificar a existência do arquivo e criá-lo, se não existir
    private File verificarCriarFicheiro(String nomeFicheiro,int lerEscrever) {
        try {
            File file = new File(nomeFicheiro);

            if (file.createNewFile()) {
                if (lerEscrever == 0){
                    try (Scanner ficheiro = new Scanner(new File(nomeFicheiro), "UTF-8")) {
                        while (ficheiro.hasNextLine()) {
                            String linha = ficheiro.nextLine();
                            System.out.println(linha);
                        }
                    } catch (FileNotFoundException e) {
                        System.err.println("Ficheiro não encontrado: " + e.getMessage());
                    }
                } else if (lerEscrever == 1) {
                    try (PrintWriter writer = new PrintWriter(
                            Files.newBufferedWriter(Paths.get(nomeFicheiro),
                                    java.nio.file.StandardOpenOption.CREATE,
                                    java.nio.file.StandardOpenOption.APPEND));
                         Formatter ficheiro = new Formatter(writer)) {
                        //code sair
                    } catch (FileNotFoundException e) {
                        System.err.println("Ficheiro não encontrado: " + e.getMessage());
                    }
                }else{
                    //sai, pois o arquivo foi criado e não tem nem nada para editar, nem para apagar
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo: " + e.getMessage());
        }
        return ficheiro;
    }

    // Função para ler do ficheiro e retornar uma lista de objetos do tipo T
    public List<T> ler(String nomeFicheiro) {
        //le varias linhas e retorna de um dado ficheiro em array
        verificarCriarFicheiro(nomeFicheiro,0);  // Verifica e cria o arquivo, se necessário
        List<T> lista = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFicheiro))) {
            lista = (List<T>) ois.readObject();  // Faz o cast para o tipo correto
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler o ficheiro: " + e.getMessage());
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
    public static <T> void escrever(String nomeFicheiro, T objeto, String formato) {
        //falta colocar identificador
        try {
            File file = new File(nomeFicheiro);
            boolean fileNotEmpty = file.length() > 0;
            // Criar o arquivo, se não existir
            if (file.createNewFile() || file.exists()) {
                try (PrintWriter writer = new PrintWriter(
                        Files.newBufferedWriter(Paths.get(nomeFicheiro),
                                java.nio.file.StandardOpenOption.CREATE,
                                java.nio.file.StandardOpenOption.APPEND));
                     Formatter ficheiro = new Formatter(writer)) {
                    if (fileNotEmpty) {
                        ficheiro.format("%n"); // Adiciona uma nova linha antes de escrever
                    }
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
    /*public void atualizar(List<T> lista, String nomeFicheiro) {
        escrever(lista, nomeFicheiro);  // Como a atualização é basicamente sobrescrever, podemos chamar a função escrever
    }

    // Função para apagar os dados do ficheiro
    public void apagar(String nomeFicheiro) {
        verificarCriarFicheiro(nomeFicheiro);  // Verifica e cria o arquivo, se necessário
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFicheiro))) {
            oos.writeObject(new ArrayList<T>());  // Escreve uma lista vazia no ficheiro, apagando o conteúdo anterior
        } catch (IOException e) {
            System.err.println("Erro ao apagar o ficheiro: " + e.getMessage());
        }
    }*/
}
