package net.njsharpe.slabstoblocks;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class Materials {

    private static final List<String> MATERIAL_NAMES = Arrays.stream(Material.values())
            .map(Material::name)
            .toList();

    public static List<String> getMaterialNames() {
        return MATERIAL_NAMES;
    }



}
