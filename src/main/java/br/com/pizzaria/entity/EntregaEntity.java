package br.com.pizzaria.entity;

import br.com.pizzaria.enumeration.StatusEntregaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "entrega")
public class EntregaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "enderecoentrega")
    private String enderecoEntrega;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datahoraenvio")
    private Date datahoraEnvio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datahoraentrega")
    private Date datahoraEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEntregaEnum status = StatusEntregaEnum.AGUARDANDO;

    @Size(max = 500)
    @Column(name = "observacao")
    private String observacao;

    // Relacionamento n:1 com Pedido
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pedido", referencedColumnName = "id")
    private PedidoEntity pedido;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(String enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }

    public Date getDatahoraEnvio() { return datahoraEnvio; }
    public void setDatahoraEnvio(Date datahoraEnvio) { this.datahoraEnvio = datahoraEnvio; }

    public Date getDatahoraEntrega() { return datahoraEntrega; }
    public void setDatahoraEntrega(Date datahoraEntrega) { this.datahoraEntrega = datahoraEntrega; }

    public StatusEntregaEnum getStatus() { return status; }
    public void setStatus(StatusEntregaEnum status) { this.status = status; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public PedidoEntity getPedido() { return pedido; }
    public void setPedido(PedidoEntity pedido) { this.pedido = pedido; }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final EntregaEntity other = (EntregaEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Entrega #" + id;
    }
}
