package br.com.pizzaria.converter;

import br.com.pizzaria.entity.PedidoEntity;
import br.com.pizzaria.facade.PedidoFacade;
import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "pedidoConverter", managed = true)
public class PedidoConverter implements Converter<PedidoEntity> {

    @EJB
    private PedidoFacade pedidoFacade;

    @Override
    public PedidoEntity getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return pedidoFacade.find(Integer.parseInt(value));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PedidoEntity value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}