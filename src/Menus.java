import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public class Menus {

    private static Utente utente = new Utente(0,"",0,0);
    private Biblioteca biblioteca = new Biblioteca(null);

    private ArrayList<String> pesquisarBibliotecas() {
        // Caminho para a raiz do projeto (mude se necessário)
        Path raizProjeto = Paths.get(".");

        // Pastas a serem ignoradas (pastas comuns de projetos Java)
        Set<String> pastasIgnoradas = new HashSet<>();
        pastasIgnoradas.add(".git");
        pastasIgnoradas.add(".idea");
        pastasIgnoradas.add("out");
        pastasIgnoradas.add("src");
        pastasIgnoradas.add(".gitignore");
        // pastasIgnoradas.add("2024ER_GRUPO3.iml");

        // Lista para armazenar os nomes das pastas encontradas
        List<String> pastasEncontradas = new ArrayList<>();

        // Listar pastas na raiz do projeto que não são as pastas comuns
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(raizProjeto)) {
            for (Path entry : stream) {
                // Verifica se é um diretório e não está na lista de ignorados
                if (Files.isDirectory(entry) && !pastasIgnoradas.contains(entry.getFileName().toString())) {
                    pastasEncontradas.add(entry.getFileName().toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Retorna a lista com as pastas encontradas
        return new ArrayList<>(pastasEncontradas);
    }

    private void menuBiblioteca(){
        ArrayList<String> pastas = pesquisarBibliotecas();
        int op;
        String[] opcoes = new String[]{"1. Adicionar Biblioteca: ","2. Editar Biblioteca","3. Apagar Biblioteca"};
        String[] pergunta = new String[]{"Escolha uma opção: "};
        int indice = 4;
        do {
            indice = 4;
            Funcionalidades.escreverStrings(opcoes);
            for (String pasta : pastas) {
                System.out.println(indice+". "+"Abrir na "+pasta);
                indice++;
            }
            op = Funcionalidades.lerOpcoesMenus(5,pergunta);
            //falta definir a biblioteca
            switch(op) {
                case 1:
                    System.out.println("Adicionar Biblioteca: ");
                    //adicionar biblioteca
                    //cria todos os ficheiros que vão ser necessários
                    //e abre na respetiva biblioteca
                    break;
                case 2:
                    System.out.println("Editar Biblioteca: ");
                    //editar biblioteca
                    //e abre na respetiva biblioteca
                    break;
                case 3:
                    System.out.println("Apagar Biblioteca: ");
                    //eliminar biblioteca
                    break;
                default:
                    System.out.println("abrir com a biblioteca: "+pastas.get(indice-6));
                    biblioteca.setBibliotecaAtual(pastas.get(indice-6));

                    //continua o ciclo
                    break;
            }
        }while(op<4 || op>indice);

        //abre com a respetiva biblioteca

    }

    public void menuPrincipal() {
        menuBiblioteca();
        int op;
        String[] menu = {
                         "\n==== MENU PRINCIPAL ====",
                         "1. Menu Livros",
                         "2. Menu Jornais/Revistas",
                         "3. Menu Utente",
                         "4. Menu Empréstimo",
                         "5. Menu Reserva",
                         "0. Sair",
                         "Escolha uma opção: "
                        };
        do {
            op = Funcionalidades.lerOpcoesMenus(5,menu);
            switch (op) {
                case 1:
                    menuLivro();
                    System.out.println("Escolheu: Menu Livros");
                    break;
                case 2:
                    menuJornalRevista();
                    System.out.println("Escolheu: Menu Jornais/Revistas");
                    break;
                case 3:
                    menuUtente();
                    System.out.println("Escolheu: Menu Utente");
                    break;
                case 4:
                    System.out.println("Escolheu: Menu Empréstimo");
                    break;
                case 5:
                    System.out.println("Escolheu: Menu Reserva");
                    break;
                case 0:
                    System.out.println("Sair");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }

        } while (op != 0);
    }

    public static void menuLivro() {
        int op;
        String[] menu = {
                "\n==== MENU Livro ====",
                "1. Adicionar Livro",
                "2. Editar Livro",
                "3. Remover Livro",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Adicionar Livro");
                    // Utente.registar();
                    break;
                case 2:
                    System.out.println("Escolheu: Editar Livro");
                    //faz o pedido e a leitura do Nif/nome/contacto ate encontrar o utente
                    menuLivroEditar();
                    //apaga o utente de memoria
                    break;
                //MenuUtentePesquisa(0);
                case 3:
                    System.out.println("Escolheu: Remover Livro");
                    //faz o pedido e a leitura do Nif/nome/contacto ate encontrar o utente
                    //chama a função apagar com o nif do utente
                    break;
                // MenuUtentePesquisa(1);
                case 0:
                    // op = 0;
                    System.out.println("Voltar");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }
        } while (op != 0);
    }

    public static void menuLivroEditar () {
        int op;
        String[] menu = {
                "\n==== MENU Livro Edição ====",
                "1. Alterar o NIF",
                "2. Alterar o Nome",
                "3. Alterar o Contacto",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: NIF");
                    // Ler o novo NIF
                    // Chamar a função editar

                    break;
                case 2:
                    System.out.println("Escolheu: Nome");
                    // Ler o novo Nome
                    // Chamar a função editar
                    break;
                case 3:
                    System.out.println("Escolheu: Contacto");
                    // Ler o novo Contacto
                    // Chamar a função editar
                    op ++;
                    break;
                case 0:
                    System.out.println("Voltar");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }

        } while (op != 0);
    }

    public static void menuJornalRevista() {
        int op;
        String[] menu = {
                "\n==== MENU Jornal/Revista ====",
                "1. Adicionar Jornal ou Revista",
                "2. Editar Jornal ou Revista",
                "3. Remover Jornal ou Revista",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Adicionar Jornal ou Revista");
                    // Utente.registar();
                    break;
                case 2:
                    System.out.println("Escolheu: Editar Jornal ou Revista");
                    //faz o pedido e a leitura do Nif/nome/contacto ate encontrar o utente
                    menuJornalRevistaEditar();
                    //apaga o utente de memoria
                    break;
                //MenuUtentePesquisa(0);
                case 3:
                    System.out.println("Escolheu: Remover Jornal ou Revista");
                    //faz o pedido e a leitura do Nif/nome/contacto ate encontrar o utente
                    //chama a função apagar com o nif do utente
                    break;
                // MenuUtentePesquisa(1);
                case 0:
                    // op = 0;
                    System.out.println("Voltar");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }
        } while (op != 0);
    }

    public static void menuJornalRevistaEditar () {
        int op;
        String[] menu = {
                "\n==== MENU Jornal/Revista Edição ====",
                "1. Alterar o NIF",
                "2. Alterar o Nome",
                "3. Alterar o Contacto",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: NIF");
                    // Ler o novo NIF
                    // Chamar a função editar

                    break;
                case 2:
                    System.out.println("Escolheu: Nome");
                    // Ler o novo Nome
                    // Chamar a função editar
                    break;
                case 3:
                    System.out.println("Escolheu: Contacto");
                    // Ler o novo Contacto
                    // Chamar a função editar
                    op ++;
                    break;
                case 0:
                    System.out.println("Voltar");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }

        } while (op != 0);
    }

    public void menuUtente() {
        int op;

        String[] menu = {
                "\n==== MENU Utente ====",
                //"Utente Atual:"+this.utente.getNif()+this.utente.getNome()+this.utente.getGenero()+this.utente.getContacto(),
                "1. Adicionar Utente",
                "2. Editar Utente",
                "3. Remover Utente",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            int cont = 1;
            boolean first = true;
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Adicionar Utente");
                    utente = Utente.registar();
                    //verificar se o utente fica bem na memoria
                    break;
                case 2:
                    System.out.println("Escolheu: Editar Utente");
                    do {
                        if (!first) {
                            cont = Funcionalidades.lerInt("Deseja sair?(0=não 1=sim)");
                        }
                        if (cont == 1) {
                            String dadoPesquisa = Funcionalidades.lerString("Introduza o Nif, Nome ou Contacto a Procurar");
                            utente = Utente.procurar(dadoPesquisa);
                            first = false;
                        }
                    } while (utente == null && cont == 1);
                    menuUtenteAlterar();
                    //apaga o utente de memoria
                    break;
                    //MenuUtentePesquisa(0);
                case 3:
                    System.out.println("Escolheu: Remover Utente");
                    do {
                        if (!first) {
                            cont = Funcionalidades.lerInt("Deseja sair?(0=não 1=sim)");
                        }
                        if (cont == 1) {
                            String dadoPesquisa = Funcionalidades.lerString("Introduza o Nif, Nome ou Contacto a Procurar");
                            utente = Utente.procurar(dadoPesquisa);
                            first = false;
                        }
                    } while (utente == null && cont == 1);
                    utente.eliminar();
                    //faz o pedido e a leitura do Nif/nome/contacto ate encontrar o utente
                    //chama a função apagar com o nif do utente
                    break;
                   // MenuUtentePesquisa(1);
                case 0:
                    // op = 0;
                    System.out.println("Voltar");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }
        } while (op != 0);
    }

    public  void menuUtenteAlterar () {
        int op;
        String[] menu = {
                "\n==== MENU Utente Edição ====",
                "Utente Atual:"+"|"+utente.getNif()+"|"+utente.getNome()+"|"+utente.getGenero()+"|"+utente.getContacto()+"|",
                "1. Alterar o NIF",
                "2. Alterar o Nome",
                "3. Alterar o Contacto",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            switch (op) {
                case 1:
                case 2:
                case 3:
                    utente.editar(op);
                    break;
                case 0:
                    System.out.println("Voltar");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }

        } while (op != 0);
    }

}
