package com.helliongames.evoodooers.item;

import com.helliongames.evoodooers.registration.EvoodooersItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HairTuftItem extends Item {
    public static final String TAG_HAIR_OWNER = "HairOwner";

    public HairTuftItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack) {
        if (itemStack.is(EvoodooersItems.HAIR_TUFT.get()) && itemStack.hasTag()) {
            CompoundTag tag;
            String string = null;

            CompoundTag compoundTag = itemStack.getTag();
            if (compoundTag.contains(TAG_HAIR_OWNER, 8)) {
                string = compoundTag.getString(TAG_HAIR_OWNER);
            } else if (compoundTag.contains(TAG_HAIR_OWNER, 10) && (tag = compoundTag.getCompound(TAG_HAIR_OWNER)).contains("Name", 8)) {
                string = tag.getString("Name");
            }
            if (string != null) {
                return Component.translatable(this.getDescriptionId() + ".named", string);
            }
        }

        return super.getName(itemStack);
    }
}
