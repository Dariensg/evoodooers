package com.helliongames.evoodooers.item;

import com.helliongames.evoodooers.registration.EvoodooersItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class VoodooDollBlockItem extends BlockItem {
    public static final String TAG_CONNECTED_PLAYER = "ConnectedPlayer";
    public VoodooDollBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public Component getName(ItemStack itemStack) {
        if (itemStack.is(EvoodooersItems.VOODOO_DOLL.get()) && itemStack.hasTag()) {
            CompoundTag tag;
            String string = null;

            CompoundTag compoundTag = itemStack.getTag();
            if (compoundTag.contains(TAG_CONNECTED_PLAYER, 8)) {
                string = compoundTag.getString(TAG_CONNECTED_PLAYER);
            } else if (compoundTag.contains(TAG_CONNECTED_PLAYER, 10) && (tag = compoundTag.getCompound(TAG_CONNECTED_PLAYER)).contains("Name", 8)) {
                string = tag.getString("Name");
            }
            if (string != null) {
                return Component.translatable(this.getDescriptionId() + ".named", string);
            }
        }

        return super.getName(itemStack);
    }
}
