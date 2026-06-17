package br.com.pizzaria.entity;

import br.com.pizzaria.enumeration.TamanhoPizzaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "pizza")
public class PizzaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome")
    private String nome;

    @Size(max = 500)
    @Column(name = "descricao")
    private String descricao;

    @Basic(optional = false)
    @NotNull
    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;

    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tamanho")
    private TamanhoPizzaEnum tamanho;

    @Column(name = "disponivel")
    private boolean disponivel = true;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public TamanhoPizzaEnum getTamanho() { return tamanho; }
    public void setTamanho(TamanhoPizzaEnum tamanho) { this.tamanho = tamanho; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final PizzaEntity other = (PizzaEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return nome + " (" + (tamanho != null ? tamanho.getDescricao() : "") + ") - R$ " + preco;
    }
}
