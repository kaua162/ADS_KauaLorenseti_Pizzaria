package br.com.pizzaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "entrega")
public class EntregaEntity {

    @Id
    private int id;
    private String endereco;
    private String status;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datahorareg;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public PedidoEntity getPedido() { return pedido; }
    public void setPedido(PedidoEntity pedido) { this.pedido = pedido; }

    public Date getDatahorareg() { return datahorareg; }
    public void setDatahorareg(Date datahorareg) { this.datahorareg = datahorareg; }
}