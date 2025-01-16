import java.util.List;

// Classe base Obra
public class Obra {
    String codigo;
    protected String titulo;
    protected String editora;
    protected String categoria;
    protected static String NOME_FICHEIRO;

    // Construtor
    public Obra(String codigo, String titulo, String editora, String categoria) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.editora = editora;
        this.categoria = categoria;
    }

    public Obra(String titulo, String editora, String categoria) {
        this.titulo = titulo;
        this.editora = editora;
        this.categoria = categoria;
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCodigo() {
        return null;
    }

    public void exibeDados() {
        System.out.println("Título: " + titulo);
        System.out.println("Editora: " + editora);
        System.out.println("Categoria: " + categoria);
        System.out.println("Código: " + getCodigo());
    }

    protected void introTitulo() {
        do {
            titulo = Funcionalidades.lerString("Introduza o Título:");
            if (titulo.length() <= 3 || titulo.length() >= 100) {
                Funcionalidades.escreverString("Erro: Introduza um Título válido (entre 3 e 100 caracteres)");
            }
        } while (titulo.length() <= 3 || titulo.length() >= 100);
    }
    protected void introEditora() {
        do {
            editora = Funcionalidades.lerString("Introduza os Editores:");
            if (editora.length() <= 3 || editora.length() >= 100) {
                Funcionalidades.escreverString("Erro: Introduza um nome de Editores válido (entre 3 e 100 caracteres)");
            }
        } while (editora.length() <= 3 || editora.length() >= 100);
    }

    protected static boolean validarIssn(String issn) {
        String issnSemHifen = issn.replace("-", "");
        String primeirosSete = issnSemHifen.substring(0, 7);

        if(!issn.matches("^\\d{4}-\\d{3}[0-9X]$")) {
            System.out.println("Formato Errado");
            return false;
        }

        if (JornalRevista.procurar(issn)!=null)
        {
            System.out.println("O issn já existe");
            return false;
        }

        return true;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

   /* protected boolean validarIsbn10(String isbn) {
        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += (isbn.charAt(i) - '0') * (10 - i);
        }

        char ultimo = isbn.charAt(9);
        if (ultimo == 'X') {
            soma += 10; //
        } else if (Character.isDigit(ultimo)) {
            soma += (ultimo - '0');
        } else {
            return false;
        }

        return soma % 11 == 0;
    }
    protected boolean validarIsbn13(String isbn){
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            int digito = isbn.charAt(i) - '0';
            soma += (i % 2 == 0) ? digito : digito * 3;
        }

        int checkDigitEsperado = (10 - (soma % 10)) % 10;

        int checkDigitReal = isbn.charAt(12) - '0';
        return checkDigitEsperado == checkDigitReal;
    }*/

    protected boolean isbnValido(String isbn) {
        if (isbn == null) return false;

        // Regex para validar formatos
        String regexISBN10 = "^[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9X]$";

        String regexISBN13 = "^[0-9]{3}-[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9]$";

        if(Livro.procurar(isbn)!=null){
            System.out.println("O código do livro já existe");
            return false;
        }

        if (isbn.matches(regexISBN10)) {
            return true;
        }
        if (isbn.matches(regexISBN13)) {
            return true;
        }
        return false;
    }
    protected void introCodigo(String pergunta) {

        do {
            codigo = Funcionalidades.lerString(pergunta);
            if (!identificarEValidar(codigo)) {
                Funcionalidades.escreverString("Erro: Codigo é inválido. Certifique-se de que seja um ISBN-10 ou ISBN-13 ou um ISNN válido (com hífens).");
            }
        } while (!identificarEValidar(codigo));

        Funcionalidades.escreverString("Codigo válido: " + codigo);
    }
    protected boolean identificarEValidar(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            return false;
        }

        // Remover possíveis hifens para a validação
        String codigoSemHifen = codigo.replace("-", "");

        // Verificar comprimento e formato para ISSN
        if (codigoSemHifen.length() == 8 && codigo.matches("^[0-9]{4}-[0-9]{3}[0-9X]$")) {
            boolean valido = validarIssn(codigo);
            return true;
        }

        // Verificar comprimento e formato para ISBN
        if (codigoSemHifen.length() == 10 && codigo.matches("^[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9X]$")) {
            return true;
        }

        if (codigoSemHifen.length() == 13 && codigo.matches("^[0-9]{3}-[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9]$")) {
            return true;
        }

        return false;
    }

    public Obra pesquisar() {
        String codigoSemHifen = codigo.replace("-", "");
        int comprimento = codigoSemHifen.length();

        if (comprimento == 8) {
            // Verificar nos jornais/revistas
            return JornalRevista.procurar(codigo);
        } else if (comprimento == 10 || comprimento == 13) {
            // Verificar nos livros
            return Livro.procurar(codigo);
        } else {
            System.out.println("Código inválido.");
            return null;
        }
    }
    public Obra verificarExiste() {
        String codigoSemHifen = codigo.replace("-", "");
        int comprimento = codigoSemHifen.length();

        if (comprimento == 8) {
            // Verificar nos jornais/revistas
            JornalRevista jornal = JornalRevista.procurar(codigo);
            Obra obra = new Obra(codigo,jornal.titulo,jornal.editora,jornal.categoria);
            System.out.println(obra);
            return obra;
        } else if (comprimento == 10 || comprimento == 13) {
            // Verificar nos livros
            Livro livro = Livro.procurar(codigo);
            Obra obra = new Obra(codigo,livro.titulo,livro.editora,livro.categoria);
            System.out.println(obra);
            return obra;
        } else {
            System.out.println("Código inválido.");
            return null;
        }
    }

    public static Obra pesquisarPorTitulo(String titulo) {
        // Pesquisar nos livros
        List<String> livros = Ficheiros.ler(Livro.NOME_FICHEIRO);
        for (String linha : livros) {
            String[] partes = linha.split("\\|");
            if (partes.length >= 6 && partes[0].toLowerCase().contains(titulo.toLowerCase())) {
                return new Livro(partes[0], partes[1], partes[2],
                        Integer.parseInt(partes[3]), partes[4], partes[5]);
            }
        }

        // Pesquisar nos jornais/revistas
        List<String> jornaisRevistas = Ficheiros.ler(JornalRevista.NOME_FICHEIRO);
        for (String linha : jornaisRevistas) {
            String[] partes = linha.split("\\|");
            if (partes.length >= 5 && partes[0].toLowerCase().contains(titulo.toLowerCase())) {
                return new JornalRevista(partes[0], partes[1], partes[2], partes[3], partes[4]);
            }
        }

        // Retornar null se não encontrou nenhum resultado
        return null;
    }
    protected void introCategoria() {do {
        categoria = Funcionalidades.lerString("Introduza a Categoria:");
        if (categoria.length() <= 3 || categoria.length() >= 100) {
            Funcionalidades.escreverString("Erro: Introduza uma Categoria válida (entre 3 e 100 caracteres)");
        }
    } while (categoria.length() <= 3 || categoria.length() >= 100);}


}


