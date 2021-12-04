package com.prodato.norma.demo.tomcat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.component.selectonelistbox.SelectOneListbox;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;

@Named(value = "buttonInSelectOneListboxModel")
@SessionScoped
public class ButtonInSelectOneListboxModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public static final Logger logger = LogManager.getLogger();
    
    private SelectOneListbox listBox;
    
    private final List<ListEntry> entries;
    
    private int sumValue;
    
    private ListEntry selectedValue;
    
    private String selectionStringValue;
    
    public ButtonInSelectOneListboxModel() {
        logger.debug("Creating: ButtonInSelectOneListboxModel");
        this.entries = new ArrayList<>();
        entries.add(new ListEntry(1));
        entries.add(new ListEntry(2));
        entries.add(new ListEntry(3));
        entries.add(new ListEntry(4));
        selectedValue = entries.get(0);
        sumValue = 0;
        selectionStringValue = "";
    }

    public String generalAction() {
        return "";
    }

    public void rowAction(ActionEvent event) {
        
        SelectOneListbox box = (SelectOneListbox)event.getComponent().getParent().getParent();
        
        logger.debug("ML Event called " + event.getComponent()); // TODO
        logger.debug("ML Event called " + event.getComponent().getParent()); // TODO
        logger.debug("ML Event called " + event.getComponent().getParent().getParent()); // TODO

        logger.debug("ML 1 " + box.getLocalValue()); // TODO
        logger.debug("ML 1 " + box.getValue()); // TODO
        logger.debug("ML 1 " + box.getVar()); // TODO

        logger.debug("ML CurrentEntry: " + ((ListEntry)box.getValue()).getValue()); // TODO
        
        ListEntry calledEntry  = (ListEntry) box.getValue();
        logger.debug("ML rowAction value " + calledEntry.value); // TODO
        
        sumValue += selectedValue.getValue();
        logger.debug("ML Adding to result " + sumValue); // TODO
    }
    
    public void valueChanged(ValueChangeEvent event) {
        logger.debug("ML Value Changed in Listener to " + ((ListEntry)event.getNewValue()).getValue()); // TODO
    }
    
    public List<ListEntry> getEntries() {
        return entries;
    }

    public ListEntry getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(ListEntry selectedValue) {
        this.selectedValue = selectedValue;
        logger.debug("ML set Value to  " + selectedValue.getValue()); // TODO
    }
    
    public String getSelectionStringValue() {
        this.selectionStringValue = "Ausgewählt: " + selectedValue.label;
        return selectionStringValue;
    }
    
    public int getSumValue() {
        return sumValue;
    }
    
    public SelectOneListbox getListBox() {
        return listBox;
    }

    public void setListBox(SelectOneListbox listBox) {
        this.listBox = listBox;
    }

    public static class ListEntry {
        
        private String label;
        private int value;
        
        public ListEntry(int value) {
            this.label = "+" + value;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public int getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + value;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ListEntry other = (ListEntry) obj;
            if (value != other.value)
                return false;
            return true;
        }
        
    }
    
    @FacesConverter("listEntryConverter")
    public static class ListEntryConverter implements Converter<ListEntry> {

        @Override
        public ListEntry getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
            ListEntry listEntry = new ListEntry(Integer.valueOf(value));
            return listEntry;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, ListEntry value) throws ConverterException {
            return String.valueOf(value.getValue());
        }
        
    }
    
}
