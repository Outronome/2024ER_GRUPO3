import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.*;

public class Biblioteca {

    public static String bibliotecaAtual;


    public Biblioteca(String bibliotecaAtual) {
        this.bibliotecaAtual= bibliotecaAtual;
    }

   /* public void adicionaObra(Obra obra) {
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
*/
   public void criarBiblioteca(String nomeBiblioteca) {
       File novaBiblioteca = new File(nomeBiblioteca);

       if (novaBiblioteca.exists()) {
           System.out.println("A biblioteca já existe. Escolha outro nome.");
       } else {
           if (novaBiblioteca.mkdir()) {
               System.out.println("Biblioteca criada com sucesso.");

           } else {
               System.out.println("Erro ao criar biblioteca.");
           }
       }
   }

   public void RenomearBiblioteca(String nomeBiblioteca) {
           File pastaAntiga = new File(bibliotecaAtual);
           File pastaNova = new File(nomeBiblioteca);

           // Verifica se a pasta antiga existe
           if (!pastaAntiga.exists()) {
               System.out.println("A pasta atual não existe.");

           }

           // Renomeia a pasta
           pastaAntiga.renameTo(pastaNova);
       }



    public static String getBibliotecaAtual() {
        return bibliotecaAtual;
    }

    public void setBibliotecaAtual(String bibliotecaAtual) {
        this.bibliotecaAtual = bibliotecaAtual;
    }

    public boolean eliminarBiblioteca(String pasta){
       File file = new File(pasta);
        if (file.isDirectory()) {

            File[] conteudo = file.listFiles();
            if (conteudo != null) {
                for (File arquivo : conteudo) {
                    eliminarBiblioteca(arquivo.toString());
                }
            }
        }
        return file.delete();
    }
}




