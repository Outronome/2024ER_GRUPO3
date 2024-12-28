public class Livro extends Obra{
    String titulo;
    Editora editora;
    Categoria categoria;
    Data anoEdicao;
    Cod isbn;
    Autor autor;

    public Livro(String titulo, Editora editora, Autor autor, Cod isbn, Data anoEdicao, Categoria categoria) {
        this.titulo = titulo;
        this.editora = editora;
        this.autor = autor;
        this.isbn = isbn;
        this.anoEdicao = anoEdicao;
        this.categoria = categoria;
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
    /*public List<Livro> mostrar (){
        //código de enviar o range de livros que estão no ficheiro caso não receba range enviar tudo
    }*/

}
