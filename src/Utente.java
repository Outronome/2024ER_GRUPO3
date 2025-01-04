import java.io.IOException;
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
    private void introNif(){
        do {
            nif=0;
            String val = Funcionalidades.lerString("Introduza o NIF do Utente");
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
    }
    private void introNome(){
        //Neste caso está a supor-se que não existem nomes com mais de 100 caracteres
        do {
            nome = Funcionalidades.lerString("Introduza o Nome do Utente");
            if (nome.length()<=3 || nome.length()>=100){
                Funcionalidades.escreverString("Erro:Introduza um Nome");
            }
        }while (nome.length()<=3 || nome.length()>=100);//supomos que todos os nomes tenham mais de 3 caracteres
    }
    private void introGenero(){
        do {
            genero = Funcionalidades.lerInt(
                    "Se o genero for masculino insira 0 se o genero for feminino insira 1");
        }while(genero!=0 && genero!=1);
    }
    private void introContacto(){
        do {
            contacto=0;
            String val = Funcionalidades.lerString("Introduza o Contacto Telefonico do Utente");
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
        Utente newUtente;
        Utente utente;
        do{
            tempUtente.introNif();
            tempUtente.introNome();
            tempUtente.introGenero();
            tempUtente.introContacto();
            newUtente = new Utente(tempUtente.nif, tempUtente.nome, tempUtente.genero, tempUtente.contacto);
            List<String> utentes;
            utentes = ler();
            utente = verificarSeExiste(utentes,String.valueOf(newUtente.nif));

        }while(utente==null);
        Ficheiros.escrever(NOME_FICHEIRO,newUtente,FORMATO);
        int sucesso = newUtente.verficarUtente(newUtente);
        if (sucesso == 1){
            Funcionalidades.escreverString("Utente registado com sucesso.");
        } else if (sucesso == 0) {
            Funcionalidades.escreverString("Erro:Utente não foi registado.");
        }
        return newUtente;
    }


    public void editar (String dadoPesquisa,String novoDado,int posCampo){
        //a pos do campo é para saber se qual campo se esta a trabalhar
        Utente utente = procurar(dadoPesquisa);
        String palavraAnterior = switch (posCampo) {
            case 0 -> String.valueOf(utente.nif);
            case 1 -> utente.nome;
            case 3 -> String.valueOf(utente.contacto);
            default -> null;
        };
        if (palavraAnterior != null){
            Ficheiros.atualizar(NOME_FICHEIRO,dadoPesquisa,palavraAnterior,novoDado,"");
        }
    }
    public void eliminar (String dadoPesquisa){
        Ficheiros.apagar(NOME_FICHEIRO,dadoPesquisa);
    }
    public Object[] getData() {
        return new Object[]{getNif(), getNome(), getGenero(), getContacto()};
    }
    /*public List<Utente> mostrar (){
        //código de enviar o range de utentes que estão no ficheiro caso não receba range enviar tudo
    }*/
}
