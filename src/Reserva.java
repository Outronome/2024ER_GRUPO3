public class Reserva {
    int num;
    Utente utente;
    Obra obra;
    Data inicio;
    Data registo;
    Data fim;

    public Reserva(int num, Utente utente, Obra obra, Data inicio, Data registo, Data fim) {
        this.num = num;
        this.utente = utente;
        this.obra = obra;
        this.inicio = inicio;
        this.registo = registo;
        this.fim = fim;
    }
    //ler toda a informação de uma reseva e colocar em memória
    //transformar uma reserva num emprestimo
    //retirar uma reserva de um utilizador
    public String registar (){
        //código de registar o reserva no ficheiro
        return "Resultado";
    }
    public String editar (){
        //código de editar o reserva no ficheiro
        return "Resultado";
    }
    public String eliminar (){
        //código de eliminar o reserva no ficheiro
        return "Resultado";
    }
    /*public List<Reserva> mostrar (){
        //código de enviar o range de reservas que estão no ficheiro caso não receba range enviar tudo
    }*/
}
