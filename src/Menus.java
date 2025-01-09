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
                    System.out.println("abrir com a biblioteca: "+pastas.get(indice-5));
                    biblioteca.setBibliotecaAtual(pastas.get(indice-5));

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
                    menuEmprestimo();
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
                "4. Ler Livro",
                "5. Pesquisar Livro",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Adicionar Livro");
                    // Livro.registar();
                    break;
                case 2:
                    System.out.println("Escolheu: Editar Livro");
                    //faz o pedido e a leitura do nome ate encontrar o Livto
                    menuLivroEditar();
                    //apaga o Livro de memoria
                    break;
                case 3:
                    System.out.println("Escolheu: Remover Livro");
                    //faz o pedido e a leitura do nome ate encontrar o Livro
                    //chama a função apagar com o nome do Livro
                    break;
                case 4:
                    System.out.println("Escolheu: Ler Livro");
                    //faz o pedido e a leitura do nome ate encontrar o Livro
                    //chama a função leitura com o nome do Livro
                    break;
                case 5:
                    System.out.println("Escolheu: Pesquisar Livro");
                    //faz o pedido e a leitura do nome ate encontrar o Livro
                    //chama a função pesquisar com o nome do Livro
                    break;
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
                "1. Alterar o Titulo",
                "2. Alterar a Editora",
                "3. Alterar a Categoria",
                "4. Alterar o Ano/Edição",
                "5. Alterar o ISBN",
                "6. Alterar o/a(s) Autores",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Titulo");
                    // Ler o novo Titulo
                    // Chamar a função editar

                    break;
                case 2:
                    System.out.println("Escolheu: Editora");
                    // Ler a nova Editora
                    // Chamar a função editar
                    break;
                case 3:
                    System.out.println("Escolheu: Categoria");
                    // Ler a nova Categoria
                    // Chamar a função editar
                    op ++;
                    break;
                case 4:
                    System.out.println("Escolheu: Ano/Edição");
                    // Ler o novo Ano/Edição
                    // Chamar a função editar
                    op ++;
                    break;
                case 5:
                    System.out.println("Escolheu: ISBN");
                    // Ler o novo ISBN
                    // Chamar a função editar
                    op ++;
                    break;
                case 6:
                    System.out.println("Escolheu: Autores");
                    // Ler os novos Autores
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
                "4. Ler Jornal ou Revista",
                "5. Pesquisar Jornal ou Revista",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Adicionar Jornal ou Revista");
                    // JornalRevista.registar();
                    break;
                case 2:
                    System.out.println("Escolheu: Editar Jornal ou Revista");
                    //faz o pedido e a leitura do nome ate encontrar o JornalRevista
                    menuJornalRevistaEditar();
                    //apaga o JornalRevista de memoria
                    break;
                case 3:
                    System.out.println("Escolheu: Remover Jornal ou Revista");
                    //faz o pedido e a leitura do nome ate encontrar o JornalRevista
                    //chama a função apagar com o nome do JornalRevista
                    break;
                case 4:
                    System.out.println("Escolheu: Ler JornalRevista");
                    //faz o pedido e a leitura do nome ate encontrar o JornalRevista
                    //chama a função leitura com o nome do JornalRevista
                    break;
                case 5:
                    System.out.println("Escolheu: Pesquisar JornalRevista");
                    //faz o pedido e a leitura do nome ate encontrar o JornalRevista
                    //chama a função pesquisar com o nome do JornalRevista
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
                "1. Alterar o Titulo",
                "2. Alterar a Editora",
                "3. Alterar a Categoria",
                "4. Alterar o ISSN",
                "5. Alterar a Data de Publicação",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Titulo");
                    // Ler o novo Titulo
                    // Chamar a função editar

                    break;
                case 2:
                    System.out.println("Escolheu: Editora");
                    // Ler a nova Editora
                    // Chamar a função editar
                    break;
                case 3:
                    System.out.println("Escolheu: Categoria");
                    // Ler a nova Categoria
                    // Chamar a função editar
                    op ++;
                    break;
                case 4:
                    System.out.println("Escolheu: ISSN");
                    // Ler o novo ISSN
                    // Chamar a função editar
                    op ++;
                    break;
                case 5:
                    System.out.println("Escolheu: Data de Publicação");
                    // Ler a nova Data de Publicação
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
                "4. Ler Utente",
                "5. Pesquisar Utente",
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
                case 4:
                    System.out.println("Escolheu: Ler Utente");
                    //faz o pedido e a leitura do nome ate encontrar o Utente
                    //chama a função leitura com o nome do Utente
                    break;
                case 5:
                    System.out.println("Escolheu: Pesquisar Utente");
                    //faz o pedido e a leitura do nome ate encontrar o JornalRevista
                    //chama a função pesquisar com o nome do JornalRevista
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

    public static void menuEmprestimo() {
        int op;
        String[] menu = {
                "\n==== MENU Empréstimo ====",
                "1. Adicionar Empréstimo",
                "2. Editar Empréstimo",
                "3. Visualizar Empréstimo",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Adicionar Empréstimo");
                    Emprestimo.registar();
                    break;
                case 2:
                    System.out.println("Escolheu: Editar Empréstimo");
                    //faz o pedido e a leitura do número ate encontrar o Empréstimo
                    menuEmprestimoEditar();
                    break;
                case 3:
                    System.out.println("Escolheu: Visualizar Empréstimo");
                    //faz o pedido e a leitura do número ate encontrar o Empréstimo
                    //chama a função visualizar com o número do Empréstimo
                    break;
                case 0:
                    // op = 0;
                    System.out.println("Voltar");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }
        } while (op != 0);
    }

    public static void menuEmprestimoEditar () {
        int op;
        String[] menu = {
                "\n==== MENU Empréstimo Edição ====",
                "1. Alterar o N.º Empréstimo",
                "2. Alterar o Utente do Empréstrimo",
                "3. Alterar o Livro do Empréstrimo",
                "4. Alterar a Data de Início",
                "5. Alterar a Data Prevista de Devolução",
                "6. Alterar a Data Efetiva de Devolução",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: N.º Empréstimo");
                    // Ler o novo N.º Empréstimo
                    // Chamar a função editar

                    break;
                case 2:
                    System.out.println("Escolheu: Utente do Empréstrimo\"");
                    // Ler o novo Utente do Empréstrimo"
                    // Chamar a função editar
                    break;
                case 3:
                    System.out.println("Escolheu: Livro do Empréstrimo");
                    // Ler o novo Livro do Empréstrimo
                    // Chamar a função editar
                    op ++;
                    break;
                case 4:
                    System.out.println("Escolheu: Data de Início");
                    // Ler a nova Data de Início
                    // Chamar a função editar
                    op ++;
                    break;
                case 5:
                    System.out.println("Escolheu: Data Prevista de Devolução");
                    // Ler a nova Data Prevista de Devolução
                    // Chamar a função editar
                    op ++;
                    break;
                case 6:
                    System.out.println("Escolheu: Data Efetiva de Devolução");
                    // Ler a nova Data Efetiva de Devolução
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
    public static void menuReserva() {
        int op;
        String[] menu = {
                "\n==== MENU Reserva ====",
                "1. Adicionar Reserva",
                "2. Editar Reserva",
                "3. Visualizar Reserva",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Adicionar Reserva");
                    // Reserva.registar();
                    break;
                case 2:
                    System.out.println("Escolheu: Editar Reserva");
                    //faz o pedido e a leitura do número ate encontrar a Reserva
                    menuReservaEditar();
                    break;
                case 3:
                    System.out.println("Escolheu: Visualizar Reserva");
                    //faz o pedido e a leitura do número ate encontrar a Reserva
                    //chama a função visualizar com o número da Reserva
                    break;
                case 0:
                    // op = 0;
                    System.out.println("Voltar");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }
        } while (op != 0);
    }

    public static void menuReservaEditar () {
        int op;
        String[] menu = {
                "\n==== MENU Reserva Edição ====",
                "1. Alterar o N.º Reserva",
                "2. Alterar o Utente da Reserva",
                "3. Alterar o Livro da Reserva",
                "4. Alterar a Data de Registo da Reserva",
                "5. Alterar a Data de Início da Reserva",
                "6. Alterar a Data de Fim da Reserva",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: N.º Reserva");
                    // Ler o novo N.º Reserva
                    // Chamar a função editar
                    break;
                case 2:
                    System.out.println("Escolheu: Utente da Reserva");
                    // Ler o novo Utente da Reserva"
                    // Chamar a função editar
                    break;
                case 3:
                    System.out.println("Escolheu: Livro da Reserva");
                    // Ler o novo Livro da Reserva
                    // Chamar a função editar
                    op ++;
                    break;
                case 4:
                    System.out.println("Escolheu: Data de Registo da Reserva");
                    // Ler o novo Registo da Reserva
                    // Chamar a função editar
                    op ++;
                    break;
                case 5:
                    System.out.println("Escolheu: Data de Início da Reserva");
                    // Ler a nova Data de Início da Reserva
                    // Chamar a função editar
                    op ++;
                    break;
                case 6:
                    System.out.println("Escolheu: Data de Fim da Reserva");
                    // Ler a nova Data de Fim da Reserva
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



}
