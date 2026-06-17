package br.com.pizzaria.enumeration;

public enum StatusEntregaEnum {
    AGUARDANDO("Aguardando"),
    EM_ROTA("Em Rota"),
    ENTREGUE("Entregue"),
    FALHOU("Falhou");

    private final String descricao;

    StatusEntregaEnum(String descricao) {
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
