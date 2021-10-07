package love.marblegate.flowingagony.capibility;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
    private final AbnormalJoyCapability abnormalJoyCapability = new AbnormalJoyCapability();
    private final LazyOptional<AbnormalJoyCapability> abnormalJoyCapabilityOptional = LazyOptional.of(() -> abnormalJoyCapability);
    private final CoolDown coolDown = new CoolDown();
    private final LazyOptional<CoolDown> coolDownOptional = LazyOptional.of(() -> coolDown);
    private final HatredBloodlineStatusCapability hatredBloodlineStatusCapability = new HatredBloodlineStatusCapability();
    private final LazyOptional<HatredBloodlineStatusCapability> hatredBloodlineStatusCapabilityOptional = LazyOptional.of(() -> hatredBloodlineStatusCapability);
    private final LastSweetDreamCapability lastSweetDreamCapability = new LastSweetDreamCapability();
    private final LazyOptional<LastSweetDreamCapability> lastSweetDreamCapabilityOptional = LazyOptional.of(() -> lastSweetDreamCapability);

    public void invalidate(){
        abnormalJoyCapabilityOptional.invalidate();
        coolDownOptional.invalidate();
        hatredBloodlineStatusCapabilityOptional.invalidate();
        lastSweetDreamCapabilityOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if(cap == CapabilityManager.ABNORMALJOY_CAPABILITY) return abnormalJoyCapabilityOptional.cast();
        if(cap == CapabilityManager.COOL_DOWN_CAPABILITY) return coolDownOptional.cast();
        if(cap == CapabilityManager.LAST_SWEET_DREAM_CAPABILITY) return lastSweetDreamCapabilityOptional.cast();
        if(cap == CapabilityManager.HATRED_BLOODLINE_STATUS_CAPABILITY) return hatredBloodlineStatusCapabilityOptional.cast();
        else return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundNBT = new CompoundTag();
        if (CapabilityManager.ABNORMALJOY_CAPABILITY != null) {
            compoundNBT.putFloat("abnormal_joy", abnormalJoyCapability.get());
        }
        if (CapabilityManager.COOL_DOWN_CAPABILITY != null) {
            for (CoolDown.CoolDownType coolDownType : CoolDown.CoolDownType.values()) {
                compoundNBT.putInt(coolDownType.name(), coolDown.get(coolDownType));
            }
        }
        if (CapabilityManager.LAST_SWEET_DREAM_CAPABILITY != null) {
            compoundNBT.put("last_sweet_dream_itemstack", lastSweetDreamCapability.getItemStack().serializeNBT());
        }
        if (CapabilityManager.HATRED_BLOODLINE_STATUS_CAPABILITY != null) {
            compoundNBT.putInt("hatred_bloodline_level", hatredBloodlineStatusCapability.getActiveLevel());
        }
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (CapabilityManager.ABNORMALJOY_CAPABILITY != null) {
            abnormalJoyCapability.set(((CompoundTag) nbt).getFloat("abnormal_joy"));
        }
        if (CapabilityManager.COOL_DOWN_CAPABILITY != null) {
            for (CoolDown.CoolDownType coolDownType : CoolDown.CoolDownType.values()) {
                coolDown.set(coolDownType, ((CompoundTag) nbt).getInt(coolDownType.name()));
            }
        }
        if (CapabilityManager.LAST_SWEET_DREAM_CAPABILITY != null) {
            Tag NBTedItem = ((CompoundTag) nbt).get("last_sweet_dream_itemstack");
            if (NBTedItem != null) {
                lastSweetDreamCapability.saveItemStack(ItemStack.of((CompoundTag) NBTedItem));
            } else
                lastSweetDreamCapability.clear();
        }
        if (CapabilityManager.HATRED_BLOODLINE_STATUS_CAPABILITY != null) {
            hatredBloodlineStatusCapability.setActiveLevel(((CompoundTag) nbt).getInt("hatred_bloodline_level"));
        }
    }
}
