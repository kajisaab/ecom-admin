package com.kajisaab.ecommerce.common;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LabelValuePair {
    private String label;
    private String value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String toJsonString(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}