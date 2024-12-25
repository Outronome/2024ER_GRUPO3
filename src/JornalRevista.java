public class JornalRevista {
    String titulo;
    Editora editora;
    Categoria categoria;
    Cod issn;
    Data dataPublicacao;

    public JornalRevista(String titulo, Editora editora, Categoria categoria, Cod issn, Data dataPublicacao) {
        this.titulo = titulo;
        this.editora = editora;
        this.categoria = categoria;
        this.issn = issn;
        this.dataPublicacao = dataPublicacao;
    }
    public String registar (){
        //código de registar o jornaisrevista no ficheiro
        return "Resultado";
    }
    public String editar (){
        //código de editar o jornaisrevista no ficheiro
        return "Resultado";
    }
    public String eliminar (){
        //código de eliminar o jornaisrevista no ficheiro
        return "Resultado";
    }
    /*public List<JornalRevista> mostrar (){
        //código de enviar o range de jornaisrevista que estão no ficheiro caso não receba range enviar tudo
    }*/
}
