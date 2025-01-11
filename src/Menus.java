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

        int op;
        String[] opcoes = new String[]{"1. Adicionar Biblioteca: ","2. Editar Biblioteca","3. Apagar Biblioteca"};
        String[] pergunta = new String[]{"Escolha uma opção: "};
        int indice = 4;
        do {
            ArrayList<String> pastas = pesquisarBibliotecas();
            indice = 4;
            Funcionalidades.escreverStrings(opcoes);
            for (String pasta : pastas) {
                System.out.println(indice+". "+"Abrir na "+pasta);
                indice++;
            }
            op = Funcionalidades.lerOpcoesMenus(pastas.size()+3,pergunta);
            //falta definir a biblioteca
            switch(op) {
                case 1:
                    biblioteca.criarBiblioteca(Funcionalidades.lerString("Adicionar Biblioteca: "));

                    break;
                case 2:
                    biblioteca.setBibliotecaAtual(Funcionalidades.lerString("Editar Biblioteca: "));
                    biblioteca.RenomearBiblioteca(Funcionalidades.lerString("Qual nome deseja colocar: "));
                    break;
                case 3:
                    biblioteca.eliminarBiblioteca(Funcionalidades.lerString("Eliminar Biblioteca:"));
                    break;
                default:
                    System.out.println("Abrir com a biblioteca: "+pastas.get(op-4));
                    biblioteca.setBibliotecaAtual(pastas.get(op-4));

                    return;

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
                    menuReserva();
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
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Adicionar Livro");
                    Livro.registar();
                    break;
                case 2:
                    System.out.println("Escolheu: Editar Livro");
                    //faz o pedido e a leitura do nome ate encontrar o Livro
                    String isbn = Funcionalidades.lerString("Introduza o Isbn do livro a editar:");
                    menuLivroEditar(isbn);
                    break;
                case 3:
                    System.out.println("Escolheu: Remover Livro");
                    Livro.eliminar();
                    break;
                case 4:
                    System.out.println("Escolheu: Ler Livro");
                    String isbn2 = Funcionalidades.lerString("Introduza o ISBN do livro que deseja visualizar:");
                    Livro.mostrarLivroPorISBN(isbn2);
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

    public static void menuLivroEditar (String isbn) {
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
                    String novoTitulo = Funcionalidades.lerString("Introduza o Titulo: ");
                    Livro.editarCampo(isbn,Livro.procurar(isbn).getTitulo(),novoTitulo,1);

                    break;
                case 2:
                    System.out.println("Escolheu: Editora");
                    String novoEditora = Funcionalidades.lerString("Introduza a Editora: ");
                    Livro.editarCampo(isbn,Livro.procurar(isbn).getEditora(),novoEditora,2);
                    break;
                case 3:
                    System.out.println("Escolheu: Categoria");
                    String novoCategoria = Funcionalidades.lerString("Introduza a Categoria: ");
                    Livro.editarCampo(isbn,Livro.procurar(isbn).getCategoria(),novoCategoria,3);
                    op ++;
                    break;
                case 4:
                    System.out.println("Escolheu: Ano/Edição");
                    String novoAnoEdicao = Funcionalidades.lerString("Introduza o Ano de Edição: ");
                    Livro.editarCampo(isbn,String.valueOf(Livro.procurar(isbn).getAnoEdicao()),novoAnoEdicao,4);
                    op ++;
                    break;
                case 5:
                    System.out.println("Escolheu: ISBN");
                    String novoISBN = Funcionalidades.lerString("Introduza o ISBN: ");
                    Livro.editarCampo(isbn,Livro.procurar(isbn).getIsbn(),novoISBN,5);
                    op ++;
                    break;
                case 6:
                    System.out.println("Escolheu: Autores");
                    String novoAutores = Funcionalidades.lerString("Introduza os Autores: ");
                    Livro.editarCampo(isbn,Livro.procurar(isbn).getAutores(),novoAutores,6);
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
                    JornalRevista.registar();
                    break;
                case 2:
                    System.out.println("Escolheu: Editar Jornal ou Revista");
                    String issn = Funcionalidades.lerString("Introduza o Issn da Revista a editar:");
                    menuJornalRevistaEditar(issn);

                    break;
                case 3:
                    System.out.println("Escolheu: Remover Jornal ou Revista");
                    JornalRevista.eliminar();
                    break;
                case 4:
                    System.out.println("Escolheu: Ler JornalRevista");
                    String issn2 = Funcionalidades.lerString("Introduza o ISSN do livro que deseja visualizar:");
                    Livro.mostrarLivroPorISBN(issn2);
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

    public static void menuJornalRevistaEditar (String issn) {
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
                    String novoTitulo = Funcionalidades.lerString("Introduza o Titulo: ");
                   JornalRevista.editarCampo(issn,JornalRevista.procurar(issn).getTitulo(),novoTitulo,1);

                    break;
                case 2:
                    System.out.println("Escolheu: Editora");
                    String novoEditora = Funcionalidades.lerString("Introduza a Editora: ");
                    JornalRevista.editarCampo(issn,JornalRevista.procurar(issn).getEditora(),novoEditora,2);
                    break;
                case 3:
                    System.out.println("Escolheu: Categoria");
                    String novoCategoria = Funcionalidades.lerString("Introduza a Categoria: ");
                    JornalRevista.editarCampo(issn,JornalRevista.procurar(issn).getCategoria(),novoCategoria,3);
                    op ++;
                    break;
                case 4:
                    System.out.println("Escolheu: ISSN");
                    String novoISSN = Funcionalidades.lerString("Introduza o ISSN: ");
                    JornalRevista.editarCampo(issn,JornalRevista.procurar(issn).getIssn(),novoISSN,4);
                    op ++;
                    break;
                case 5:
                    System.out.println("Escolheu: Data de Publicação");
                    String novoDataPublicacao= Funcionalidades.lerString("Introduza a Data de publicação: ");
                    JornalRevista.editarCampo(issn,JornalRevista.procurar(issn).getDataPublicacao(),novoDataPublicacao,5);
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
        Reserva reserva = new Reserva(null,0,null,null,null,null);

        String[] menu = {
                "\n==== MENU Reserva ====",
                "1. Adicionar Reserva",
                "2. Editar Reserva",
                "3. Visualizar Reserva",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(4,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Adicionar Reserva");
                    reserva.registar();
                    break;
                case 2:
                    System.out.println("Escolheu: Editar Reserva");
                    String num = Funcionalidades.lerString("Introduza o numero da reserva (R*):");
                    menuReservaEditar(num);
                    break;
                case 3:
                    System.out.println("Escolheu: Visualizar Reserva");
                    String num2 = Funcionalidades.lerString("Introduza o numero da reserva (R*):");
                    reserva.mostrarReserva(num2);
                    break;
                case 4:
                    System.out.println("Escolheu: Eliminar Reserva");
                    reserva.eliminar();
                case 0:
                    // op = 0;
                    System.out.println("Voltar");
                    break;
                default:
                    System.out.println("Opcão inválida! Digite novamente. ");
            }
        } while (op != 0);
    }

    public static void menuReservaEditar (String num) {
        int op;
        Reserva reserva = new Reserva(null,0,num,null,null,null);
        String[] menu = {
                "\n==== MENU Reserva Edição ====",
                "1. Alterar o Utente da Reserva",
                "2. Alterar o Livro da Reserva",
                "3. Alterar a Data de Registo da Reserva",
                "4. Alterar a Data de Início da Reserva",
                "5. Alterar a Data de Fim da Reserva",
                "0. Voltar",
                "Escolha uma opção: "
        };
        do {
            op = Funcionalidades.lerOpcoesMenus(3,menu);
            System.out.printf(String.valueOf(op));
            switch (op) {
                case 1:
                    System.out.println("Escolheu: Utente da Reserva");
                    String novoUtente= Funcionalidades.lerString("Introduza um Utente: ");
                    reserva.editarCampo(num,String.valueOf(reserva.procurarReservas(num).getNif()),novoUtente,2);
                    break;
                case 2:
                    System.out.println("Escolheu: Livro da Reserva");
                    String novoLivro= Funcionalidades.lerString("Introduza um novo livro: ");
                    reserva.editarCampo(num,reserva.procurarReservas(num).getObra(),novoLivro,2);
                    op ++;
                    break;
                case 3:
                    System.out.println("Escolheu: Data de Registo da Reserva");
                    String novoRegisto= Funcionalidades.lerString("Introduza a nova data de registo: ");
                    reserva.editarCampo(num,reserva.procurarReservas(num).getRegisto(),novoRegisto,4);
                    op ++;
                    break;
                case 4:
                    System.out.println("Escolheu: Data de Início da Reserva");
                    String novoInicio= Funcionalidades.lerString("Introduza a nova data de registo: ");
                    reserva.editarCampo(num,reserva.procurarReservas(num).getRegisto(),novoInicio,3);
                    op ++;
                    break;
                case 5:
                    System.out.println("Escolheu: Data de Fim da Reserva");
                    String novoFim= Funcionalidades.lerString("Introduza a nova data de fim: ");
                    reserva.editarCampo(num,reserva.procurarReservas(num).getFim(),novoFim,5);
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
