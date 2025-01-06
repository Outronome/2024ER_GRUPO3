import java.util.List;

public class Utente {
    private int nif;
    private String nome;
    private int genero;
    private int contacto;
    private static final String FORMATO = "%d|%s|%d|%d%n";
    private static final String NOME_FICHEIRO = "utentes.txt";

    public Utente(int nif, String nome, int genero, int contacto) {
        this.nif = nif;
        this.nome = nome;
        this.genero = genero;
        this.contacto = contacto;
    }

    private int verficarUtente(Utente newUtente){
        int sucesso = 0;
        //verifica se existe no ficheiro
        List<String> utentes = Ficheiros.ler(NOME_FICHEIRO);
        for ( String utente : utentes ) {
            String[] partes = utente.split("\\|");  // Usa expressão regular para dividir por "|"
            int nifCompararLista = Integer.parseInt(partes[0].trim());
            if (newUtente.nif == nifCompararLista) {
                sucesso = 1;
                break;
            }
        }
        return sucesso;
    }

    public int introNif(String pergunta){
        do {
            nif=0;
            String val = Funcionalidades.lerString(pergunta);
            if (val.length()!=9){
                Funcionalidades.escreverString("O NIF tem que conter 9 numeros");
            }else{
                try {
                    nif = Integer.parseInt((val));
                } catch (NumberFormatException e) {
                    Funcionalidades.escreverString("Erro:Introduz um numero");
                    nif=0;
                }
            }
        }while (nif < 100000000 || nif > 999999999);
        return nif;
    }

    public String introNome(String pergunta){
        //Neste caso está a supor-se que não existem nomes com mais de 100 caracteres
        do {
            nome = Funcionalidades.lerString(pergunta);
            if (nome.length()<=3 || nome.length()>=100){
                Funcionalidades.escreverString("Erro:Introduza um Nome");
            }
        }while (nome.length()<=3 || nome.length()>=100);//supomos que todos os nomes tenham mais de 3 caracteres
        return nome;
    }

    private void introGenero(){
        do {
            genero = Funcionalidades.lerInt(
                    "Se o genero for masculino insira 0 se o genero for feminino insira 1");
        }while(genero!=0 && genero!=1);
    }

    private int introContacto(String pergunta){
        do {
            contacto=0;
            String val = Funcionalidades.lerString(pergunta);
            if (val.length()!=9){
                Funcionalidades.escreverString("O Contacto Telefonico tem que conter 9 numeros");
            } else if (val.startsWith("91") || val.startsWith("92") || val.startsWith("93") || val.startsWith("95") || val.startsWith("96")) {
                try {
                    contacto = Integer.parseInt(val);
                } catch (NumberFormatException e) {
                    Funcionalidades.escreverString("Erro:Introduz um numero de telefonico");
                    contacto=0;
                }
            }
        }while (contacto < 100000000 || contacto > 999999999);
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

    private static List<String> ler(){
        return Ficheiros.ler(NOME_FICHEIRO);
    }

    public static Utente procurar(String dado){
        List<String> utentes;
        utentes = ler();
        return verificarSeExiste(utentes, dado);
        //ao receber null deve pedir outra vez a leitura de um dado para ler e procurar outro utente
    }

    private static Utente verificarSeExiste(List<String> utentes, String dado){
        //o dado tem que ser o nif o nome ou o contacto
        for (String utente : utentes){
            String[] partes = utente.split("\\|");
            String[] partesFiltradas = { partes[0], partes[1], partes[3] };
            for (String parte : partesFiltradas) {
                if (parte.equals(dado)){
                    return new Utente(Integer.parseInt(partes[0]),partes[1],Integer.parseInt(partes[2]),Integer.parseInt(partes[3]));
                }
            }
        }
        System.out.println("Utilizador não encontrado");
        return null;
    }

    public static Utente registar(){
        Utente tempUtente = new Utente(0,"",0,0);
        Utente newUtente=null;
        Utente utente=null;
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
                utente = verificarSeExiste(utentes,String.valueOf(newUtente.nif));
                if (utente!=null){
                    Funcionalidades.escreverString("Não foi possivel inserir o utente pois ele já existe");
                }
                first = false;
            }
        } while (utente != null && cont == 1);
        Ficheiros.escrever(NOME_FICHEIRO,newUtente,FORMATO);
        int sucesso = newUtente.verficarUtente(newUtente);
        if (sucesso == 1){
            Funcionalidades.escreverString("Utente registado com sucesso.");
        } else if (sucesso == 0) {
            Funcionalidades.escreverString("Erro:Utente não foi registado.");
        }
        return newUtente;
    }

    public void editar(int op){
        String novoDado = null;
        String dadoPesquisa = null;
        switch (op){
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

        Ficheiros.atualizar(NOME_FICHEIRO,dadoPesquisa,dadoPesquisa,novoDado,"");

    }

    public void eliminar (){
        Ficheiros.apagar(NOME_FICHEIRO,Integer.toString(nif));
    }

    public Object[] getData() {
        return new Object[]{getNif(), getNome(), getGenero(), getContacto()};
    }
    /*public List<Utente> mostrar (){
        //código de enviar o range de utentes que estão no ficheiro caso não receba range enviar tudo
    }*/
}

