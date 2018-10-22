package com.mytaxi.domainvalue;

public enum EngineType
{
    GAS,
    ELECTRIC;

    public static EngineType fromString(String value) {
        for (EngineType e : EngineType.values()) {
            if (e.toString().equals(value)) return e;
        }
        return null;
    }
}
