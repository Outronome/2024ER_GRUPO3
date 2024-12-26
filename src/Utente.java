public class Utente {
    int nif;
    String nome;
    int genero;
    int contacto;

    public Utente(int nif, String nome, int genero, int contacto) {
        this.nif = nif;
        this.nome = nome;
        this.genero = genero;
        this.contacto = contacto;
    }
    public String registar (){
        //código de registar o livro no ficheiro
        return "Resultado";
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
