package love.marblegate.flowingagony.capibility.lastsweetdream;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class LastSweetDreamCapability {
    @CapabilityInject(ILastSweetDreamCapability.class)
    public static final Capability<ILastSweetDreamCapability> LAST_SWEET_DREAM_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(ILastSweetDreamCapability.class, new LastSweetDreamCapability.Storage(), LastSweetDreamCapabilityStardardImpl::new);
    }

    public static class Storage implements Capability.IStorage<ILastSweetDreamCapability> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<ILastSweetDreamCapability> capability, ILastSweetDreamCapability instance, Direction side) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("last_sweet_dream_itemstack", instance.getItemStack().serializeNBT());
            return compoundNBT;
        }

        @Override
        public void readNBT(Capability<ILastSweetDreamCapability> capability, ILastSweetDreamCapability instance, Direction side, INBT nbt) {
            INBT NBTedItem = ((CompoundNBT)nbt).get("last_sweet_dream_itemstack");
            if(NBTedItem!=null){
                ItemStack itemStack = ItemStack.read((CompoundNBT) NBTedItem);
                instance.saveItemStack(itemStack);
            }
            else
                instance.clear();

        }
    }
}
