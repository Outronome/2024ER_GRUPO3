import java.util.List;

// Classe base Obra
public class Obra {
    protected String titulo;
    protected String editora;
    protected String categoria;
    protected static String NOME_FICHEIRO;

    // Construtor
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

    protected void introCategoria() {do {
        categoria = Funcionalidades.lerString("Introduza a Categoria:");
        if (categoria.length() <= 3 || categoria.length() >= 100) {
            Funcionalidades.escreverString("Erro: Introduza uma Categoria válida (entre 3 e 100 caracteres)");
        }
    } while (categoria.length() <= 3 || categoria.length() >= 100);}


}


