import java.util.ArrayList;

public class Biblioteca {
    private String nome;
    private ArrayList<Obra> obras;
    private ArrayList<Utente> utentes;
    private ArrayList<Operacao> operacoes;


    public Biblioteca(String nome) {
        this.nome = nome;
        this.obras = new ArrayList<>();
        this.utentes = new ArrayList<>();
        this.operacoes = new ArrayList<>();
    }

    public void adicionaObra(Obra obra) {
        obras.add(obra);
    }

    public void removeObra(String codigo) {
        Obra obraParaRemover = null; // Armazena a obra que será removida

        for (Obra obra : obras) {
            if (obra.getCodigo() != null && obra.getCodigo().equals(codigo)) {
                obraParaRemover = obra;
                break;
            }
        }

        if (obraParaRemover != null) {
            obras.remove(obraParaRemover);
            System.out.println("Obra removida com sucesso.");
        } else {
            System.out.println("Obra com o código especificado não encontrada.");
        }
    }

    public void leDadosObra(String codigo) {
        boolean obraEncontrada = false;

        for (Obra obra : obras) {
            if (obra.getCodigo() != null && obra.getCodigo().equals(codigo)) {
                obraEncontrada = true;
                obra.exibeDados();
                break;
            }
        }

        if (!obraEncontrada) {
            System.out.println("Obra com o código especificado não encontrada.");
        }
    }
}




