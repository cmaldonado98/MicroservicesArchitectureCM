package com.bp.test.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TypeMovement {
    WITHDRAWAL("R", "Retiro"),
    DEPOSIT("D", "Deposito");

    private final String code;
    private final String type;

    TypeMovement(String code, String type) {
        this.code = code;
        this.type = type;
    }

    public static TypeMovement findByCode(String code) {
        return Arrays.stream(values()).filter(ennum -> ennum.getCode().equals(code)).findFirst()
                .orElse(WITHDRAWAL);
    }
}
