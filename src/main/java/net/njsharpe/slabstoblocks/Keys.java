package net.njsharpe.slabstoblocks;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class Keys {

    public static NamespacedKey getBlockKeyForMaterial(Material material) {
        return new NamespacedKey(SlabsToBlocks.getInstance(), "%s_to_block"
                .formatted(material.name().toLowerCase()));
    }

}
