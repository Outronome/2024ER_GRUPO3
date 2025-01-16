import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um Utente (usuário), armazenando informações como NIF, nome, gênero e contato.
 * A classe também fornece métodos para manipulação e persistência de dados de utentes em um ficheiro.
 */

public class Utente implements Ficheiros.linhaConvertida {
    private int nif;
    private String nome;
    private int genero;
    private int contacto;
    private static final String FORMATO = "%d|%s|%d|%d%n";
    private static final String NOME_FICHEIRO = "utentes.txt";
    static List<Utente> utentes = new ArrayList<>();
    static Utente utenteAtual = new Utente();

    public static List<Utente> setUtentes() {
        utentes = Utente.lerTodosUtentes();
        return utentes;
    }
    public static void guardarUtentesFicheiro(){
        for(Utente utente : utentes) {
            Ficheiros.escrever(NOME_FICHEIRO,utente,FORMATO);
        }
    }

    // Construtor completo para instância
    public Utente(int nif, String nome, int genero, int contacto) {
        this.nif = nif;
        this.nome = nome;
        this.genero = genero;
        this.contacto = contacto;
    }

    // Construtor sem argumentos (necessário para reflexão)
    public Utente() {}

    @Override
    public void fromLine(String line) {
        // Supondo que os dados estão separados por "|"
        String[] parts = line.split("\\|");

        if (parts.length == 4) {
            try {
                this.nif = Integer.parseInt(parts[0]);
                this.nome = parts[1];
                this.genero = Integer.parseInt(parts[2]);
                this.contacto = Integer.parseInt(parts[3]);
            } catch (NumberFormatException e) {
                // Tratamento para garantir que os dados sejam válidos
                System.err.println("Erro de formato em linha: " + line);
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Formato da linha inválido: " + line);
        }
    }

    @Override
    public String toString() {
        return "Utente{" +
                "nif=" + nif +
                ", nome='" + nome + '\'' +
                ", genero=" + genero +
                ", contacto=" + contacto +
                '}';
    }

    public static List<Utente> lerTodosUtentes() {
        Ficheiros<Utente> reader = new Ficheiros<>(Utente.class);
        return reader.lerMemoria(NOME_FICHEIRO);
    }

    /**
     * Construtor da classe Utente.
     *
     * @param nif      Número de Identificação Fiscal (NIF) do utente.
     * @param nome     Nome do utente.
     * @param genero   Gênero do utente (0 para masculino, 1 para feminino).
     * @param contacto Contato telefônico do utente.
     */


    /**
     * Verifica se um novo utente já existe no ficheiro de utentes.
     *
     * @param newUtente O utente a ser verificado.
     * @return 0 se o utente já existir, 1 caso contrário.
     */

    private boolean verficarUtente(Utente newUtente) {
        if (utentes == null || utentes.isEmpty()) {
            return false;
        }

        for (Utente utenteProcurar : utentes) {
            if (utenteProcurar.nif == newUtente.nif) {
                return true;
            }
        }
        return false;
        /*int sucesso = 0;
        //verifica se existe no ficheiro
        List<String> utentes = Ficheiros.ler(NOME_FICHEIRO);
        for (String utente : utentes) {
            String[] partes = utente.split("\\|");  // Usa expressão regular para dividir por "|"
            int nifCompararLista = Integer.parseInt(partes[0].trim());
            if (newUtente.nif != nifCompararLista) {
                sucesso = 1;
                break;
            }
        }
        return sucesso;*/
    }

    /**
     * Solicita ao usuário a introdução de um NIF válido.
     *
     * @param pergunta A mensagem exibida ao usuário solicitando o NIF.
     * @return O NIF introduzido.
     */

    public int introNif(String pergunta) {
        do {
            nif = 0;
            String val = Funcionalidades.lerString(pergunta);
            if (val.length() != 9) {
                Funcionalidades.escreverString("O NIF tem que conter 9 numeros");
            } else {
                try {
                    nif = Integer.parseInt((val));
                } catch (NumberFormatException e) {
                    Funcionalidades.escreverString("Erro:Introduz um numero");
                    nif = 0;
                }
            }
        } while (nif < 100000000 || nif > 999999999);
        return nif;
    }

    /**
     * Solicita ao usuário a introdução de um nome válido.
     *
     * @param pergunta A mensagem exibida ao usuário solicitando o nome.
     * @return O nome introduzido.
     */

    public String introNome(String pergunta) {
        //Neste caso está a supor-se que não existem nomes com mais de 100 caracteres
        do {
            nome = Funcionalidades.lerString(pergunta);
            if (nome.length() <= 3 || nome.length() >= 100) {
                Funcionalidades.escreverString("Erro:Introduza um Nome");
            }
        } while (nome.length() <= 3 || nome.length() >= 100);//supomos que todos os nomes tenham mais de 3 caracteres
        return nome;
    }

    /**
     * Solicita ao usuário a introdução de um gênero válido (0 para masculino, 1 para feminino).
     */

    private void introGenero() {
        do {
            genero = Funcionalidades.lerInt(
                    "Se o genero for masculino insira 0 se o genero for feminino insira 1");
        } while (genero != 0 && genero != 1);
    }

    /**
     * Solicita ao usuário a introdução de um contato telefônico válido.
     *
     * @param pergunta A mensagem exibida ao usuário solicitando o contato.
     * @return O contato introduzido.
     */

    private int introContacto(String pergunta) {
        do {
            contacto = 0;
            String val = Funcionalidades.lerString(pergunta);
            if (val.length() != 9) {
                Funcionalidades.escreverString("O Contacto Telefonico tem que conter 9 numeros");
            } else if (val.startsWith("2")||val.startsWith("91") || val.startsWith("92") || val.startsWith("93")
                    || val.startsWith("95") || val.startsWith("96")) {
                try {
                    contacto = Integer.parseInt(val);
                } catch (NumberFormatException e) {
                    Funcionalidades.escreverString("Erro:Introduz um numero de telefonico");
                    contacto = 0;
                }
            }
        } while (contacto < 100000000 || contacto > 999999999);
        return contacto;
    }


    public void setNif(int nif) {
        this.nif = nif;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setContacto(int contacto) {
        this.contacto = contacto;
    }

    public int getNif() {
        return nif;
    }

    public int getGenero() {
        return genero;
    }

    public String getNome() {
        return nome;
    }

    public int getContacto() {
        return contacto;
    }

    private static List<String> ler() {
        return Ficheiros.ler(NOME_FICHEIRO);
    }

    /**
     * Lê os dados dos utentes armazenados no ficheiro.
     *
     * @return Uma lista de strings representando os dados dos utentes.
     */
    /*public static Utente verificarSeExiste(List<String> utentes, String dado) {
        //o dado tem que ser o nif o nome ou o contacto
        for (String utente : utentes) {
            String[] partes = utente.split("\\|");
            String[] partesFiltradas = {partes[0], partes[1], partes[3]};
            for (String parte : partesFiltradas) {
                if (parte.equals(dado)) {
                    return new Utente(Integer.parseInt(partes[0]), partes[1],
                            Integer.parseInt(partes[2]), Integer.parseInt(partes[3]));
                }
            }
        }
        System.out.println("Utilizador não encontrado");
        return null;
    }*/

    public static Utente procurar(String dado) {
        //o dado tem que ser o nif o nome ou o contacto
        boolean vazio = true;
        Utente utente= new Utente();
        for (Utente utenteProcurar : utentes) {
            if (utenteProcurar.nif == Integer.parseInt(dado) || utenteProcurar.nome == dado || utenteProcurar.contacto == Integer.parseInt(dado)) {
                //chamar o mostar com a paginação com o utente encontrado
                vazio = false;
                return utenteProcurar;
            }
        }
        return null;
        //ao receber null deve pedir outra vez a leitura de um dado para ler e procurar outro utente
    }
    public static void pesquisar() {
        //o dado tem que ser o nif o nome ou o contacto
        boolean vazio = true;
        int cont = 1;
        boolean first = true;
        String dado;
        do {

            if (!first) {
                cont = Funcionalidades.lerInt("Deseja Continuar?(0=não 1=sim)");
            }
            if (cont == 1) {
                dado = Funcionalidades.lerString("Digite o NIF ou o nome ou contacto do utente a pesquisar:");
                first = false;
                for (Utente utenteProcurar : utentes) {
                    if (utenteProcurar.nif == Integer.parseInt(dado) || utenteProcurar.nome == dado || utenteProcurar.contacto == Integer.parseInt(dado)) {
                        //chamar o mostar com a paginação com o utente encontrado
                        vazio = false;
                        utenteAtual = utenteProcurar;
                        Funcionalidades.escreverString(utenteProcurar.toString());
                        break;
                    }
                }
            }

        }while(vazio || cont == 1);

    }


    /**
     * Verifica se um utente existe em uma lista de strings.
     *
     * @param utentes A lista de strings contendo os dados dos utentes.
     * @param dado    O dado usado para localizar o utente.
     * @return O utente encontrado, ou null se nenhum for encontrado.
     */



    /**
     * Regista um novo utente, solicitando os dados ao usuário.
     *
     * @return O utente registrado.
     */
    private void adicionarUtente(Utente utente){
        utentes.add(utente);
    }

    public static Utente registar() {
        Utente tempUtente = new Utente(0, "", 0, 0);
        Utente newUtente = null;
        int cont = 1;
        boolean first = true;
        boolean encontrado = false;
        do {
            if (!first) {
                cont = Funcionalidades.lerInt("Deseja sair?(0=não 1=sim)");
            }
            if (cont == 1) {
                tempUtente.introNif("Introduza o Nif do Utente");
                tempUtente.introNome("Introduza o Nome do Utente");
                tempUtente.introGenero();
                tempUtente.introContacto("Introduza o Contacto Telefónico do Utente");
                newUtente = new Utente(tempUtente.nif, tempUtente.nome, tempUtente.genero, tempUtente.contacto);
                encontrado = newUtente.verficarUtente(newUtente);

                if (encontrado) {
                    Funcionalidades.escreverString("Não foi possivel inserir o utente pois ele já existe");
                }
                first = false;
            }
        } while (encontrado || cont == 1);
        //colocar na lista em memoria
        newUtente.adicionarUtente(newUtente);
        //guardar logo no ficheiro se estiver ligado o guardar automatico
        //Ficheiros.escrever(NOME_FICHEIRO, newUtente, FORMATO);
        boolean sucesso = newUtente.verficarUtente(newUtente);
        if (sucesso) {
            Funcionalidades.escreverString("Utente registado com sucesso.");
        } else  {
            Funcionalidades.escreverString("Erro:Utente não foi registado.");
        }
        return newUtente;
    }

    /**
     * Edita os dados de um utente com base na opção fornecida.
     *
     * @param op A opção que determina qual dado será editado (1 para NIF, 2 para nome, 3 para contato).
     */

    public void editar(int op) {
        String novoDado = null;
        String dadoPesquisa = null;
        switch (op) {
            case 1:
                dadoPesquisa = Integer.toString(nif);
                novoDado = Integer.toString(introNif("Introduza o novo Nif"));
                setNif(Integer.parseInt(novoDado));
                //verificar se o nif já existe
                break;
            case 2:
                dadoPesquisa = nome;
                novoDado = introNome("Introduza o novo Nome do Utente");
                setNome(novoDado);
                break;
            case 3:
                dadoPesquisa = Integer.toString(contacto);
                novoDado = Integer.toString(introContacto("Introduza o novo Contacto Telefonico do Utente"));
                setContacto(Integer.parseInt(novoDado));
            default:
                break;
        }

        Ficheiros.atualizar(NOME_FICHEIRO, dadoPesquisa, dadoPesquisa, novoDado, "");

    }

    /**
     * Remove os dados de um utente do ficheiro.
     */
    public void eliminar() {
        //falta verificar se exite alguma dependencia
        pesquisar();
        boolean r =Reserva.verificarDependencias(String.valueOf(utenteAtual.nif));
        boolean e =Emprestimo.verificarDependencias(String.valueOf(utenteAtual.nif));
        System.out.println(String.valueOf(r)+"T"+String.valueOf(e));
        if (e && r){
            for (int i = 0; i < utentes.size(); i++) {
                if (utentes.get(i).getNif() == utenteAtual.nif) {
                    utentes.remove(i);
                    break;  // Remove o primeiro Utente encontrado com esse NIF e sai do loop
                }
            }
        }else {
            Funcionalidades.escreverString("Erro ao eliminar existem dependencias");
        }
    }

    /**
     * Retorna os dados do utente como um array de objetos.
     *
     * @return Um array contendo o NIF, nome, gênero e contato do utente.
     */

    public Object[] getData() {
        return new Object[]{getNif(), getNome(), getGenero(), getContacto()};
    }
    /*public List<Utente> mostrar (){
        //código de enviar o range de utentes que estão no ficheiro caso não receba range enviar tudo
    }*/
}

