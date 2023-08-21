package com.smartChart.patient.Permission;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    PATIENT_READ("patient:read"),

    PATIENT_UPDATE("patient:update"),

    PATIENT_CREATE("patient:create"),

    PATIENT_DELETE("patient:delete"),

    DOCTOR_READ("DOCTOR:read"),

    DOCTOR_UPDATE("DOCTOR:update"),

    DOCTOR_CREATE("DOCTOR:create"),

    DOCTOR_DELETE("DOCTOR:delete");

    @Getter
    private final String permission;
}
