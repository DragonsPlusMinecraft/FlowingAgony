package love.marblegate.flowingagony.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "flowingagony");
    public static final RegistryObject<SoundEvent> miraculous_escape_heartbeat = SOUNDS.register("miraculous_escape_heartbeat", () -> new SoundEvent(new ResourceLocation("flowingagony", "miraculous_escape_heartbeat")));
    public static final RegistryObject<SoundEvent> malice_outbreak_knockback_sound = SOUNDS.register("malice_outbreak_knockback_sound", () -> new SoundEvent(new ResourceLocation("flowingagony", "malice_outbreak_knockback_sound")));
    public static final RegistryObject<SoundEvent> extreme_hatred_first_stage_sound = SOUNDS.register("extreme_hatred_first_stage_sound", () -> new SoundEvent(new ResourceLocation("flowingagony", "extreme_hatred_first_stage_sound")));
    public static final RegistryObject<SoundEvent> extreme_hatred_medium_stage_sound = SOUNDS.register("extreme_hatred_medium_stage_sound", () -> new SoundEvent(new ResourceLocation("flowingagony", "extreme_hatred_medium_stage_sound")));
    public static final RegistryObject<SoundEvent> extreme_hatred_final_stage_sound = SOUNDS.register("extreme_hatred_final_stage_sound", () -> new SoundEvent(new ResourceLocation("flowingagony", "extreme_hatred_final_stage_sound")));


}
