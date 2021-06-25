package love.marblegate.flowingagony.effect.special;

import love.marblegate.flowingagony.network.Networking;
import love.marblegate.flowingagony.network.PlaySoundPacket;
import love.marblegate.flowingagony.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.network.PacketDistributor;

public class MiraculousEscapeForceEscapeEffect extends Effect {
    public MiraculousEscapeForceEscapeEffect() {
        super(EffectType.HARMFUL, 6881280);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EffectRegistry.miraculous_escape_enchantment_force_escape_effect.get()) {
            int duration = entityLivingBaseIn.getActivePotionEffect(this.getEffect()).getDuration();
            if(duration==39){
                //Play Sound Effect
                if (!entityLivingBaseIn.world.isRemote && entityLivingBaseIn instanceof PlayerEntity) {
                    Networking.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> (ServerPlayerEntity) entityLivingBaseIn
                            ),
                            new PlaySoundPacket(PlaySoundPacket.ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT,true));
                }
            }
            if(duration%40>25){
                entityLivingBaseIn.setMotion(0,2,0);
                entityLivingBaseIn.velocityChanged=true;
                entityLivingBaseIn.markPositionDirty();
            }
            if(duration==1){
                if (!entityLivingBaseIn.world.isRemote && entityLivingBaseIn instanceof PlayerEntity) {
                    Networking.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> (ServerPlayerEntity) entityLivingBaseIn
                            ),
                            new PlaySoundPacket(PlaySoundPacket.ModSoundType.MIRACULOUS_ESCAPE_HEARTBEAT,false));
                }
            }
            else{
                entityLivingBaseIn.setMotion(entityLivingBaseIn.getLookVec().getX(),duration/12.5,entityLivingBaseIn.getLookVec().getZ());
                entityLivingBaseIn.velocityChanged=true;
                entityLivingBaseIn.markPositionDirty();
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
