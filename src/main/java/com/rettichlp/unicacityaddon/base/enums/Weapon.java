package com.rettichlp.unicacityaddon.base.enums;

import com.rettichlp.unicacityaddon.base.text.ColorCode;

import java.util.Arrays;

public enum Weapon {
    M4("M4"),
    MP5("MP5"),
    PISTOLE("Pistole"),
    HUNTING_RIFLE("Jagdflinte");

    private final String name;

    Weapon(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getItemName() {
        return ColorCode.DARK_GRAY.getCode() + name;
    }

    public static Weapon getWeaponByName(String name) {
        return Arrays.stream(Weapon.values())
                .filter(weapon -> name.equalsIgnoreCase(weapon.getName()))
                .findFirst()
                .orElse(null);
    }

    public static Weapon getWeaponByItemName(String displayName) {
        return Arrays.stream(Weapon.values())
                .filter(weapon -> displayName.equalsIgnoreCase(weapon.getItemName()))
                .findFirst()
                .orElse(null);
    }
}
