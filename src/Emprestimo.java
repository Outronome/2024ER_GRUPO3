public class Emprestimo{
    int num;
    Utente utente;
    Obra obra;
    Data inicio;
    Data devolucaoPrevista;
    Data devolucaoDefinitiva;

    public Emprestimo(int num, Utente utente, Obra obra,
                      Data inicio, Data devolucaoPrevista, Data devolucaoDefinitiva) {
        this.num = num;
        this.utente = utente;
        this.obra = obra;
        this.inicio = inicio;
        this.devolucaoPrevista = devolucaoPrevista;
        this.devolucaoDefinitiva = devolucaoDefinitiva;
    }
    public String registar (){
        //código de registar o emprestimo no ficheiro
        return "Resultado";
    }
    public String editar (){
        //código de editar o emprestimo no ficheiro
        return "Resultado";
    }
    public String eliminar (){
        //código de eliminar o emprestimo no ficheiro
        return "Resultado";
    }
    /*public List<Emprestimo> mostrar (){
        //código de enviar o range de emprestimos que estão no ficheiro caso não receba range enviar tudo
    }*/
}
