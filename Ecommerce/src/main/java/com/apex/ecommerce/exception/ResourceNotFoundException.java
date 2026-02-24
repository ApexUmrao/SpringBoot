package com.apex.ecommerce.exception;

public class ResourceNotFoundException extends RuntimeException {

    String resource;
    String field;
    String fieldName;
    Integer fieldId;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String resource, String field, String fieldName) {
        super(String.format("%s Resource having %s --> %s is not found", resource, field, fieldName));
        this.resource = resource;
        this.field = field;
        this.fieldName = fieldName;
    }

    public ResourceNotFoundException(String resource, String field, Integer fieldId) {
        super(String.format("%s Resource having %s --> %s is not found", resource, field, fieldId));
        this.fieldId = fieldId;
        this.field = field;
        this.resource = resource;
    }
}
