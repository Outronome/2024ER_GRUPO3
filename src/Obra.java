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

    protected boolean validarIssn(String issn) {
        String issnSemHifen = issn.replace("-", "");
        String primeirosSete = issnSemHifen.substring(0, 7);
        char ultimoDigito = issnSemHifen.charAt(7);
        // Calcular a soma ponderada dos primeiros 7 dígitos
        int soma = 0;
        for (int i = 0; i < 7; i++) {
            int digito = Character.getNumericValue(primeirosSete.charAt(i));
            soma += digito * (8 - i); // Peso decrescente de 8 a 2
        }
        // Calcular o dígito de verificação
        int resto = soma % 11;
        int digitoVerificador = (11 - resto) % 11;
        char digitoEsperado = (digitoVerificador == 10) ? 'X' : Character.forDigit(digitoVerificador, 10);
        return ultimoDigito == digitoEsperado;
    }

    protected boolean validarIsbn10(String isbn) {
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
    }

    protected boolean isbnValido(String isbn) {
        if (isbn == null) return false;

        // Regex para validar formatos
        String regexISBN10 = "^[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9X]$";
        String regexISBN13 = "^[0-9]{3}-[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9]$";

        if (isbn.matches(regexISBN10)) {
            return validarIsbn10(isbn.replace("-", ""));
        }
        if (isbn.matches(regexISBN13)) {
            return validarIsbn13(isbn.replace("-", ""));
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
            boolean valido = validarIsbn10(codigoSemHifen);
            return true;
        }

        if (codigoSemHifen.length() == 13 && codigo.matches("^[0-9]{3}-[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9]$")) {
            boolean valido = validarIsbn13(codigoSemHifen);
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


    protected void introCategoria() {do {
        categoria = Funcionalidades.lerString("Introduza a Categoria:");
        if (categoria.length() <= 3 || categoria.length() >= 100) {
            Funcionalidades.escreverString("Erro: Introduza uma Categoria válida (entre 3 e 100 caracteres)");
        }
    } while (categoria.length() <= 3 || categoria.length() >= 100);}


}


