import java.util.List;

public class Utente {
    private int nif;
    private String nome;
    private int genero;
    private int contacto;
    private String FORMATO = "%d|%s|%d|%d%n";
    private String NOME_FICHEIRO = "utentes.txt";

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
            int nifComparar = Integer.parseInt(partes[0].trim());
            if(nif == nifComparar){
               sucesso = 1;
            }
        }
        return sucesso;
    }
    public void registar (){
        do {
            nif=0;
            String val = Funcionalidades.lerstring("Introduza o NIF do Utente");
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
        }while (nif < 100000000 && nif > 999999999);
        //Neste caso está a supor-se que não existem nomes com mais de 100 caracteres
        do {
            nome = Funcionalidades.lerstring("Introduza o Nome do Utente");
            if (nome.length()<=3 && nome.length()>=100){
                Funcionalidades.escreverString("Erro:Introduza um Nome");
            }
        }while (nome.length()<=3 && nome.length()>=100);//supomos que todos os nomes tenham mais de 3 caracteres
        do {
            genero = Funcionalidades.lerint(
                    "Se o genero for masculino insira 0 se o genero for feminino insira 1");
        }while(genero!=0 || genero!=1);
        do {
            contacto=0;
            String val = Funcionalidades.lerstring("Introduza o Contacto Telefonico do Utente");
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
        }while (contacto < 100000000 && contacto > 999999999);

        Utente newUtente = new Utente(nif, nome, genero, contacto);
        Ficheiros.escrever("utente",newUtente,FORMATO);
        int sucesso = verficarUtente(newUtente);
        if (sucesso == 1){
            Funcionalidades.escreverString("Utente registado com sucesso.");
        } else if (sucesso == 0) {
            Funcionalidades.escreverString("Utente não foi registado.");
        }
    }
    public String editar (){
        //código de editar o livro no ficheiro
        return "Resultado";
    }
    public String eliminar (){
        //código de eliminar o livro no ficheiro
        return "Resultado";
    }
    /*public List<Utente> mostrar (){
        //código de enviar o range de utentes que estão no ficheiro caso não receba range enviar tudo
    }*/
}
