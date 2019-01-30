package com.lucasrodrigo.cursomc.resorces.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
    public static final long serialVersionUID = 1L;

    private String fieldName;
    private String messaghe;

    public FieldMessage() {
    }

    public FieldMessage(String fieldName, String messaghe) {
        this.fieldName = fieldName;
        this.messaghe = messaghe;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessaghe() {
        return messaghe;
    }

    public void setMessaghe(String messaghe) {
        this.messaghe = messaghe;
    }
}
