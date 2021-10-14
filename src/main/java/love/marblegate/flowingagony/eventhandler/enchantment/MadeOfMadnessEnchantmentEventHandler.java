package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.effect.EffectRegistry;
import love.marblegate.flowingagony.enchantment.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EffectUtil;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber()
public class MadeOfMadnessEnchantmentEventHandler {
    @SubscribeEvent
    public static void onAgonyScreamerEnchantmentEven(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.AGONY_SCREAMER.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    event.getEntityLiving().addEffect(new MobEffectInstance(EffectRegistry.AGONY_RESONANCE.get(), 140 + 20 * enchantmentLvl, enchantmentLvl));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onInsanePoetEnchantmentEven(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.INSANE_POET.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    event.getEntityLiving().addEffect(new MobEffectInstance(EffectRegistry.LISTEN_TO_ME_SINGING.get(), enchantmentLvl * 40, enchantmentLvl - 1));
                    ((Player) event.getSource().getEntity()).addEffect(EffectUtil.genImplicitEffect(EffectRegistry.INSANE_POET_ENCHANTMENT_ACTIVE.get(), enchantmentLvl * 40));
                    event.setAmount((float) (event.getAmount() * (1.0D - Configuration.GeneralSetting.INSANE_POET_DAMAGE_REDUCTION.get())));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPaperBrainEnchantmentEven(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.PAPER_BRAIN.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    event.getEntityLiving().addEffect(EffectUtil.genImplicitEffect(EffectRegistry.PAPER_BRAIN_ENCHANTMENT_ACTIVE.get(), 20 + 40 * enchantLvl, enchantLvl - 1));
                    event.setAmount((float) (event.getAmount() * (1.0D - Configuration.GeneralSetting.PAPER_BRAIN_DAMAGE_REDUCTION.get())));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onShockTherapyEnchantmentEven(LivingDamageEvent event) {
        if (!event.getEntityLiving().level.isClientSide()) {
            if (event.getSource().getEntity() instanceof Player) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.SHOCK_THERAPY.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    event.getEntityLiving().addEffect(EffectUtil.genImplicitEffect(EffectRegistry.SHOCK_THERAPY_ENCHANTMENT_ACTIVE.get(), 20 + 40 * enchantLvl, enchantLvl - 1));
                    event.setAmount((float) (event.getAmount() * (1.0D - Configuration.GeneralSetting.SHOCK_THERAPY_DAMAGE_REDUCTION.get())));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCuttingWatermelonDreamEnchantmentEvent_dealDamage(BlockEvent.BreakEvent event) {
        if (!event.getPlayer().level.isClientSide()) {
            if (event.getState().getBlock().equals(Blocks.MELON)) {
                if (EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), EnchantmentRegistry.CUTTING_WATERMELON_DREAM.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    List<LivingEntity> targets = EntityUtil.getTargetsExceptOneself(event.getPlayer(), 12, 2,
                            livingEntity -> EntityUtil.isHostile(livingEntity, false));
                    if (!targets.isEmpty()) {
                        int silkTouchEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), Enchantments.SILK_TOUCH, EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL);
                        int unbreakingEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), Enchantments.UNBREAKING, EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                        float damage = 0;
                        if (event.getPlayer().getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof DiggerItem) {
                            damage += ((DiggerItem) event.getPlayer().getItemBySlot(EquipmentSlot.MAINHAND).getItem()).getAttackDamage();
                        }
                        if (silkTouchEnchantmentLvl == 1)
                            damage *= 0.5;
                        if (event.getPlayer().level.getDayTime() % 24000 > 13000)
                            damage *= (4 + event.getPlayer().getRandom().nextDouble() * 2);
                        else damage *= (2 + event.getPlayer().getRandom().nextDouble());
                        for (LivingEntity target : targets) {
                            target.hurt(CustomDamageSource.causeCuttingWaterMelonDream(event.getPlayer()), damage);
                        }
                        int damageAppliedToItem = 5;
                        if (unbreakingEnchantmentLvl != 0) {
                            if (unbreakingEnchantmentLvl == 3)
                                damageAppliedToItem = 3;
                            else damageAppliedToItem = 4;
                        }
                        event.getPlayer().getItemBySlot(EquipmentSlot.MAINHAND).hurtAndBreak(damageAppliedToItem, event.getPlayer(), x -> {
                        });
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCuttingWatermelonDreamEnchantmentEvent_dropHeadAndExtraLoot(LivingDeathEvent event) {
        if (!event.getEntityLiving().level.isClientSide && !event.isCanceled()
                && event.getSource().getMsgId().equals("flowingagony.cutting_watermelon_dream")
                && event.getSource().getEntity() instanceof Player
                && EntityUtil.supportHeadDrop(event.getEntityLiving())) {
            if (EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), EnchantmentRegistry.CUTTING_WATERMELON_DREAM.get(), EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                int silkTouchEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), Enchantments.SILK_TOUCH, EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL);
                int fortuneEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((Player) event.getSource().getEntity(), Enchantments.BLOCK_FORTUNE, EquipmentSlot.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (silkTouchEnchantmentLvl > 0) {
                    double dropHeadRate = 0.025 + 0.01 * fortuneEnchantmentLvl;
                    if (Math.random() < dropHeadRate) {
                        if (event.getEntityLiving() instanceof Zombie)
                            Containers.dropItemStack(event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ(), Items.ZOMBIE_HEAD.getDefaultInstance());
                        else if (event.getEntityLiving() instanceof Skeleton)
                            Containers.dropItemStack(event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ(), Items.SKELETON_SKULL.getDefaultInstance());
                        else if (event.getEntityLiving() instanceof Creeper)
                            Containers.dropItemStack(event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ(), Items.CREEPER_HEAD.getDefaultInstance());
                        else if (event.getEntityLiving() instanceof WitherSkeleton)
                            Containers.dropItemStack(event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ(), Items.WITHER_SKELETON_SKULL.getDefaultInstance());
                        else if (event.getEntityLiving() instanceof EnderDragon)
                            Containers.dropItemStack(event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ(), Items.DRAGON_HEAD.getDefaultInstance());
                    }
                }
                if (fortuneEnchantmentLvl > 0) {
                    dropLoot(event.getEntityLiving(), (Player) event.getSource().getEntity(), DamageSource.playerAttack((Player) event.getSource().getEntity()), fortuneEnchantmentLvl);
                }
            }
        }
    }

    protected static void dropLoot(LivingEntity livingEntity, Player playerEntity, DamageSource damageSourceIn, int lootLevel) {
        ResourceLocation resourcelocation = livingEntity.getLootTable();
        LootTable loottable = livingEntity.level.getServer().getLootTables().get(resourcelocation);
        LootContext.Builder lootcontext$builder = getLootContextBuilder(livingEntity, playerEntity, damageSourceIn);
        LootContext ctx = lootcontext$builder.create(LootContextParamSets.ENTITY);
        loottable.getRandomItems(ctx).forEach(itemStack -> {
            itemStack = recalculateLootByLootingLevel(itemStack, ctx, lootLevel);
            livingEntity.spawnAtLocation(itemStack);
        });
    }

    public static ItemStack recalculateLootByLootingLevel(ItemStack stack, LootContext context, int lootLevel) {
        float f = (float) lootLevel * UniformGenerator.between(0F, 1F).getFloat(context);
        stack.grow(Math.round(f));
        return stack;
    }

    protected static LootContext.Builder getLootContextBuilder(LivingEntity livingEntity, Player playerEntity, DamageSource damageSourceIn) {
        LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel) livingEntity.level)).withRandom(livingEntity.getRandom()).withParameter(LootContextParams.THIS_ENTITY, livingEntity).withParameter(LootContextParams.ORIGIN, livingEntity.position()).withParameter(LootContextParams.DAMAGE_SOURCE, damageSourceIn).withOptionalParameter(LootContextParams.KILLER_ENTITY, damageSourceIn.getEntity()).withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, damageSourceIn.getDirectEntity());
        lootcontext$builder = lootcontext$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, playerEntity).withLuck(playerEntity.getLuck());
        return lootcontext$builder;
    }


}
