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
@Table(name = "pedido")
public class PedidoEntity {

    @Id
    private int id;
    private String status;
    private int quantidade;
    private double total;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private PizzaEntity pizza;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datahorareg;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public ClienteEntity getCliente() { return cliente; }
    public void setCliente(ClienteEntity cliente) { this.cliente = cliente; }

    public PizzaEntity getPizza() { return pizza; }
    public void setPizza(PizzaEntity pizza) { this.pizza = pizza; }

    public Date getDatahorareg() { return datahorareg; }
    public void setDatahorareg(Date datahorareg) { this.datahorareg = datahorareg; }
}