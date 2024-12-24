import java.io.*;


public abstract class Ficheiros {

    private String nomeFicheiro;

    public Ficheiros(String nomeFicheiro) {
        this.nomeFicheiro = nomeFicheiro;
    }

    // Função para ler do ficheiro e retornar uma lista de objetos do tipo T
    public List<T> ler() {
        List<T> lista = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFicheiro))) {
            lista = (List<T>) ois.readObject();  // Faz o cast para o tipo correto
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler o ficheiro: " + e.getMessage());
        }
        return lista;
    }

    // Função para escrever uma lista de objetos do tipo T no ficheiro
    public void escrever(List<T> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFicheiro))) {
            oos.writeObject(lista);  // Escreve a lista no ficheiro
        } catch (IOException e) {
            System.err.println("Erro ao escrever no ficheiro: " + e.getMessage());
        }
    }

    // Função para atualizar o conteúdo do ficheiro, substituindo a lista
    public void atualizar(List<T> lista) {
        escrever(lista);  // Como a atualização é basicamente sobrescrever, podemos chamar a função escrever
    }

    // Função para apagar os dados do ficheiro
    public void apagar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFicheiro))) {
            oos.writeObject(new ArrayList<T>());  // Escreve uma lista vazia no ficheiro, apagando o conteúdo anterior
        } catch (IOException e) {
            System.err.println("Erro ao apagar o ficheiro: " + e.getMessage());
        }
    }






}


