package com.helliongames.evoodooers.item.crafting;


import com.helliongames.evoodooers.registration.EvoodooersItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class VoodooDollRecipe extends ShapedRecipe {

    public VoodooDollRecipe(ResourceLocation resourceLocation, CraftingBookCategory craftingBookCategory) {
        super(resourceLocation, "", craftingBookCategory, 3, 3, NonNullList.of(Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.of(EvoodooersItems.HAIR_TUFT.get()), Ingredient.EMPTY, Ingredient.of(Items.WHEAT), Ingredient.of(Items.BONE), Ingredient.of(Items.WHEAT), Ingredient.EMPTY, Ingredient.of(Items.WHEAT), Ingredient.EMPTY), new ItemStack(EvoodooersItems.VOODOO_DOLL.get()));
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer, RegistryAccess registryAccess) {
        ItemStack result = super.assemble(craftingContainer, registryAccess);

        ItemStack invItem = craftingContainer.getItem(1);

        if (invItem.isEmpty() || !invItem.is(EvoodooersItems.HAIR_TUFT.get()) || !invItem.getOrCreateTag().contains("HairOwner")) return result;

        Tag owner = invItem.getOrCreateTag().get("HairOwner");

        result.addTagElement("ConnectedPlayer", owner);

        return result;
    }
}
