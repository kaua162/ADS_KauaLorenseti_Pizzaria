package br.com.pizzaria.enumeration;

public enum StatusPedidoEnum {
    PENDENTE("Pendente"),
    EM_PREPARO("Em Preparo"),
    PRONTO("Pronto"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedidoEnum(String descricao) {
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
