package br.com.pizzaria.converter;
 
import br.com.pizzaria.entity.ClienteEntity;
import br.com.pizzaria.facade.ClienteFacade;
import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
 
@FacesConverter(value = "clienteConverter", managed = true)
public class ClienteConverter implements Converter<ClienteEntity> {
 
    @EJB
    private ClienteFacade clienteFacade;
 
    @Override
    public ClienteEntity getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return clienteFacade.find(Integer.parseInt(value));
        } catch (Exception e) {
            return null;
        }
    }
 
    @Override
    public String getAsString(FacesContext context, UIComponent component, ClienteEntity value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}
 