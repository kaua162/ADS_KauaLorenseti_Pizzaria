package br.com.pizzaria.entity;

import br.com.pizzaria.enumeration.StatusPedidoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "pedido")
public class PedidoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datahorapedido")
    private Date datahoraPedido;

    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade")
    private Integer quantidade;

    @Basic(optional = false)
    @NotNull
    @Column(name = "valortotal", precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedidoEnum status = StatusPedidoEnum.PENDENTE;

    // Relacionamento n:1 com Cliente
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private ClienteEntity cliente;

    // Relacionamento n:1 com Pizza
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pizza", referencedColumnName = "id")
    private PizzaEntity pizza;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Date getDatahoraPedido() { return datahoraPedido; }
    public void setDatahoraPedido(Date datahoraPedido) { this.datahoraPedido = datahoraPedido; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public StatusPedidoEnum getStatus() { return status; }
    public void setStatus(StatusPedidoEnum status) { this.status = status; }

    public ClienteEntity getCliente() { return cliente; }
    public void setCliente(ClienteEntity cliente) { this.cliente = cliente; }

    public PizzaEntity getPizza() { return pizza; }
    public void setPizza(PizzaEntity pizza) { this.pizza = pizza; }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final PedidoEntity other = (PedidoEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Pedido #" + id;
    }
}
