package love.marblegate.flowingagony.fx;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "flowingagony");
    public static final RegistryObject<SoundEvent> MIRACULOUS_ESCAPE_HEARTBEAT = SOUNDS.register("miraculous_escape_heartbeat", () -> new SoundEvent(new ResourceLocation("flowingagony", "miraculous_escape_heartbeat")));
    public static final RegistryObject<SoundEvent> MALICE_OUTBREAK_KNOCKBACK_SOUND = SOUNDS.register("malice_outbreak_knockback_sound", () -> new SoundEvent(new ResourceLocation("flowingagony", "malice_outbreak_knockback_sound")));
    public static final RegistryObject<SoundEvent> EXTREME_HATRED_FIRST_STAGE_SOUND = SOUNDS.register("extreme_hatred_first_stage_sound", () -> new SoundEvent(new ResourceLocation("flowingagony", "extreme_hatred_first_stage_sound")));
    public static final RegistryObject<SoundEvent> EXTREME_HATRED_MEDIUM_STAGE_SOUND = SOUNDS.register("extreme_hatred_medium_stage_sound", () -> new SoundEvent(new ResourceLocation("flowingagony", "extreme_hatred_medium_stage_sound")));
    public static final RegistryObject<SoundEvent> EXTREME_HATRED_FINAL_STAGE_SOUND = SOUNDS.register("extreme_hatred_final_stage_sound", () -> new SoundEvent(new ResourceLocation("flowingagony", "extreme_hatred_final_stage_sound")));


}
