package br.com.pizzaria.converter;

import br.com.pizzaria.entity.PizzaEntity;
import br.com.pizzaria.facade.PizzaFacade;
import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "pizzaConverter", managed = true)
public class PizzaConverter implements Converter<PizzaEntity> {

    @EJB
    private PizzaFacade pizzaFacade;

    @Override
    public PizzaEntity getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return pizzaFacade.find(Integer.parseInt(value));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PizzaEntity value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}