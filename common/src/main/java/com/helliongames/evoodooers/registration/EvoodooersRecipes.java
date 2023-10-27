package com.helliongames.evoodooers.registration;

import com.helliongames.evoodooers.Constants;
import com.helliongames.evoodooers.item.crafting.VoodooDollRecipe;
import com.helliongames.evoodooers.registration.util.RegistrationProvider;
import com.helliongames.evoodooers.registration.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class EvoodooersRecipes {
    /**
     * The provider for recipes
     */
    public static final RegistrationProvider<RecipeSerializer<?>> RECIPES = RegistrationProvider.get(Registries.RECIPE_SERIALIZER, Constants.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> VOODOO_DOLL_RECIPE = RECIPES.register("voodoo_doll", () -> new SimpleCraftingRecipeSerializer<>(VoodooDollRecipe::new));

    // Called in the mod initializer / constructor in order to make sure that items are registered
    public static void loadClass() {}
}
