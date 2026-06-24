package br.com.clinic.service.utils;

/**
 *
 * @author Raul Pablo
 *
*/
public class ValidadorUtils {

    // evitar que ela seja instanciada
    private ValidadorUtils() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não deve ser instanciada.");
    }

    /**
     * Valida se uma string é nula ou vazia.
     */
    public static void validarTextoObrigatorio(String texto, String mensagemErro) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(mensagemErro);
        }
    }

    /**
     * Valida se uma string tem um tamanho mínimo, garantindo antes que ela não é nula.
     */
    public static void validarTamanhoMinimo(String texto, int tamanhoMinimo, String mensagemErro) {
        validarTextoObrigatorio(texto, mensagemErro);

        if (texto.trim().length() < tamanhoMinimo) {
            throw new IllegalArgumentException("Forneça pelo menos " + tamanhoMinimo + " caracteres para a busca.");
        }
    }

    /**
     * Valida matematicamente um CPF.
     *
     */
    public static void validarCpf(String cpf) {
        validarTextoObrigatorio(cpf, "O CPF não pode ser nulo ou vazio.");

        String cpfLimpo = cpf.replaceAll("\\D", "");

        // Verifica tamanho e sequencia repetida conhecida
        if (cpfLimpo.length() != 11 || cpfLimpo.matches("(\\d)\\1{10}")) {
            throw new IllegalArgumentException("O CPF deve conter exatamente 11 digitos validos.");
        }

//        // Validação do primeiro dígito verificador
//        int soma = 0;
//        int peso = 10;
//        for (int i = 0; i < 9; i++) {
//            soma += (Character.getNumericValue(cpfLimpo.charAt(i)) * peso--);
//        }
//        int resto = 11 - (soma % 11);
//        char digito1 = (resto == 10 || resto == 11) ? '0' : Character.forDigit(resto, 10);
//
//        // Validação do segundo dígito verificador
//        soma = 0;
//        peso = 11;
//        for (int i = 0; i < 10; i++) {
//            soma += (Character.getNumericValue(cpfLimpo.charAt(i)) * peso--);
//        }
//        resto = 11 - (soma % 11);
//        char digito2 = (resto == 10 || resto == 11) ? '0' : Character.forDigit(resto, 10);
//
//        if (digito1 != cpfLimpo.charAt(9) || digito2 != cpfLimpo.charAt(10)) {
//            throw new IllegalArgumentException("CPF informado é inválido.");
//        }
    }
}
