import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um Utente (usuário), armazenando informações como NIF, nome, gênero e contato.
 * A classe também fornece métodos para manipulação e persistência de dados de utentes em um ficheiro.
 */

public class Utente{
    private int nif;
    private String nome;
    private int genero;
    private int contacto;
    private static final String FORMATO = "%d|%s|%d|%d%n";
    private static final String NOME_FICHEIRO = "utentes.txt";
    static List<Utente> utentes = new ArrayList<>();


    // Construtor completo para instância
    public Utente(int nif, String nome, int genero, int contacto) {
        this.nif = nif;
        this.nome = nome;
        this.genero = genero;
        this.contacto = contacto;
    }

    // Construtor sem argumentos (necessário para reflexão)
    public Utente() {}


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
            if (utenteProcurar.getNif() == utenteProcurar.nif) {
                return true;
            }
        }
        return false;

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

    public static Utente procurar(String dado) {
        List<String> utentes;
        utentes = ler();
        return verificarSeExiste(utentes, dado);
        //ao receber null deve pedir outra vez a leitura de um dado para ler e procurar outro utente
    }

    /**
     * Verifica se um utente existe em uma lista de strings.
     *
     * @param utentes A lista de strings contendo os dados dos utentes.
     * @param dado    O dado usado para localizar o utente.
     * @return O utente encontrado, ou null se nenhum for encontrado.
     */

    public static Utente verificarSeExiste(List<String> utentes, String dado) {
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
    }

    /**
     * Regista um novo utente, solicitando os dados ao usuário.
     *
     * @return O utente registrado.
     */


    public static Utente registar() {
        Utente tempUtente = new Utente(0, "", 0, 0);
        Utente newUtente = null;
        Utente utente = null;
        int cont = 1;
        boolean first = true;
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
                List<String> utentes;
                utentes = ler();
                utente = verificarSeExiste(utentes, String.valueOf(newUtente.nif));
                if (utente != null) {
                    Funcionalidades.escreverString("Não foi possivel inserir o utente pois ele já existe");
                }
                first = false;
            }
        } while (utente != null && cont == 1);



        boolean sucesso = !newUtente.verficarUtente(newUtente);
        if (sucesso) {
            Ficheiros.escrever(NOME_FICHEIRO, newUtente, FORMATO);
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
        Ficheiros.apagar(NOME_FICHEIRO, Integer.toString(nif));
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

