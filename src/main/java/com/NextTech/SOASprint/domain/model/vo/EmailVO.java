package com.NextTech.SOASprint.domain.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class EmailVO {
    @Email @NotBlank
    @Column(name = "email", nullable = false)
    private String value;

    protected EmailVO() {} 

    public EmailVO(String value) { this.value = value; }

    public String getValue() { return value; }
}

