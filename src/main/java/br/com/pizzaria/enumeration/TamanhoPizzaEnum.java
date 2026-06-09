package br.com.pizzaria.enumeration;

public enum TamanhoPizzaEnum {
    PEQUENA("Pequena"),
    MEDIA("Média"),
    GRANDE("Grande"),
    FAMILIA("Família");

    private final String descricao;

    TamanhoPizzaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
