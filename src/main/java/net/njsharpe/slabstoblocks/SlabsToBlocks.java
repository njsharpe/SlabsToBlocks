package net.njsharpe.slabstoblocks;

import net.njsharpe.slabstoblocks.recipe.BlockRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// =================== REQUIREMENTS ===================
// 1. Be able to identify (programmatically) all slabs
// 2. Be able to determine if slab has block counterpart
// 3. Generate recipes for turning slabs to blocks
// 4. Register all recipes on startup
public class SlabsToBlocks extends JavaPlugin {

    private static SlabsToBlocks instance;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        final String ending = Constants.getMaterialNameSlabEnding();
        final List<String> names = Materials.getMaterialNames();

        Arrays.stream(Material.values())
                .map(m -> {
                    String slabName = m.name();
                    if(!slabName.contains(ending)) {
                        return Map.entry(m, Material.AIR);
                    }

                    String blockNameDefault = slabName.substring(0, slabName.length() - ending.length());
                    String blockNamePlanks = blockNameDefault + "_PLANKS";
                    String blockNameBlocks = blockNameDefault + "_BLOCK";
                    String blockNamePlural = blockNameDefault + "S";

                    // Do this to catch things like this:
                    // Material.STONE_SLAB => Material.STONE
                    if(names.contains(blockNameDefault)) {
                        Material block = Objects.requireNonNull(Material.getMaterial(blockNameDefault));
                        if(block.isBlock() && block.isSolid() && block.isOccluding()) {
                            // Do this to avoid the following:
                            // Material.BRICK_SLAB  => Material.BRICK
                            // Material.QUARTZ_SLAB => Material.QUARTZ
                            // Material.BAMBOO_SLAB => Material.BAMBOO
                            return Map.entry(m, block);
                        }
                    }

                    // Do this to catch things like this:
                    // Material.OAK_SLAB => Material.OAK_PLANKS
                    if(names.contains(blockNamePlanks)) {
                        return Map.entry(m, Objects.requireNonNull(Material.getMaterial(blockNamePlanks)));
                    }

                    // Do this to catch things like this:
                    // Material.QUARTZ_SLAB => Material.QUARTZ_BLOCK
                    if(names.contains(blockNameBlocks)) {
                        return Map.entry(m, Objects.requireNonNull(Material.getMaterial(blockNameBlocks)));
                    }

                    // Do this to catch things like this:
                    // Material.STONE_BRICK_SLAB => Material.STONE_BRICKS
                    if(names.contains(blockNamePlural)) {
                        return Map.entry(m, Objects.requireNonNull(Material.getMaterial(blockNamePlural)));
                    }

                    return Map.entry(m, Material.AIR);
                })
                .filter(e -> e.getValue() != Material.AIR)
                .forEach(e -> {
                    BlockRecipe recipe = new BlockRecipe(e.getKey(), new ItemStack(e.getValue(), 1));
                    this.getServer().addRecipe(recipe);
                });
    }

    @Override
    public void onDisable() {
        instance = null;
        super.onDisable();
    }

    public static SlabsToBlocks getInstance() {
        return instance;
    }

}
