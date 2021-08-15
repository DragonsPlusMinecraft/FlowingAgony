package love.marblegate.flowingagony.eventhandler.enchantment;

import love.marblegate.flowingagony.config.Configuration;
import love.marblegate.flowingagony.damagesource.CustomDamageSource;
import love.marblegate.flowingagony.registry.EffectRegistry;
import love.marblegate.flowingagony.registry.EnchantmentRegistry;
import love.marblegate.flowingagony.util.EnchantmentUtil;
import love.marblegate.flowingagony.util.EntityUtil;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.loot.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
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
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.AGONY_SCREAMER.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.AGONY_RESONANCE.get(), 140 + 20 * enchantmentLvl, enchantmentLvl));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onInsanePoetEnchantmentEven(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                int enchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.INSANE_POET.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantmentLvl != 0) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.LISTEN_TO_ME_SINGING.get(), enchantmentLvl * 40, enchantmentLvl - 1));
                    ((PlayerEntity) event.getSource().getTrueSource()).addPotionEffect(new EffectInstance(EffectRegistry.INSANE_POET_ENCHANTMENT_ACTIVE.get(), enchantmentLvl * 40));
                    event.setAmount((float) (event.getAmount() * (1.0D - Configuration.GeneralSetting.INSANE_POET_DAMAGE_REDUCTION.get())));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPaperBrainEnchantmentEven(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.PAPER_BRAIN.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.PAPER_BRAIN_ENCHANTMENT_ACTIVE.get(), 20 + 40 * enchantLvl, enchantLvl - 1));
                    event.setAmount((float) (event.getAmount() * (1.0D - Configuration.GeneralSetting.PAPER_BRAIN_DAMAGE_REDUCTION.get())));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onShockTherapyEnchantmentEven(LivingDamageEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                int enchantLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.SHOCK_THERAPY.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (enchantLvl != 0) {
                    event.getEntityLiving().addPotionEffect(new EffectInstance(EffectRegistry.SHOCK_THERAPY_ENCHANTMENT_ACTIVE.get(), 20 + 40 * enchantLvl, enchantLvl - 1));
                    event.setAmount((float) (event.getAmount() * (1.0D - Configuration.GeneralSetting.SHOCK_THERAPY_DAMAGE_REDUCTION.get())));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCuttingWatermelonDreamEnchantmentEvent_dealDamage(BlockEvent.BreakEvent event) {
        if (!event.getPlayer().world.isRemote()) {
            if (event.getState().getBlock().equals(Blocks.MELON)) {
                if (EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), EnchantmentRegistry.CUTTING_WATERMELON_DREAM.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                    List<LivingEntity> targets = EntityUtil.getTargetsExceptOneself(event.getPlayer(), 12, 2,
                            livingEntity -> EntityUtil.isHostile(livingEntity, false));
                    if (!targets.isEmpty()) {
                        int silkTouchEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), Enchantments.SILK_TOUCH, EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL);
                        int unbreakingEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted(event.getPlayer(), Enchantments.UNBREAKING, EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                        float damage = 0;
                        if (event.getPlayer().getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() instanceof ToolItem) {
                            damage += ((ToolItem) event.getPlayer().getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem()).getAttackDamage();
                        }
                        if (silkTouchEnchantmentLvl == 1)
                            damage *= 0.5;
                        if (event.getPlayer().world.getDayTime() % 24000 > 13000)
                            damage *= (4 + event.getPlayer().getRNG().nextDouble() * 2);
                        else damage *= (2 + event.getPlayer().getRNG().nextDouble());
                        for (LivingEntity target : targets) {
                            target.attackEntityFrom(CustomDamageSource.causeCuttingWaterMelonDream(event.getPlayer()), damage);
                        }
                        int damageAppliedToItem = 5;
                        if (unbreakingEnchantmentLvl != 0) {
                            if (unbreakingEnchantmentLvl == 3)
                                damageAppliedToItem = 3;
                            else damageAppliedToItem = 4;
                        }
                        event.getPlayer().getItemStackFromSlot(EquipmentSlotType.MAINHAND).damageItem(damageAppliedToItem, event.getPlayer(), x -> {
                        });
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCuttingWatermelonDreamEnchantmentEvent_dropHeadAndExtraLoot(LivingDeathEvent event) {
        if (!event.getEntityLiving().world.isRemote && !event.isCanceled()
                && event.getSource().getDamageType().equals("flowingagony.cutting_watermelon_dream")
                && event.getSource().getTrueSource() instanceof PlayerEntity
                && EntityUtil.supportHeadDrop(event.getEntityLiving())) {
            if (EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), EnchantmentRegistry.CUTTING_WATERMELON_DREAM.get(), EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL) == 1) {
                int silkTouchEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), Enchantments.SILK_TOUCH, EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.GENERAL);
                int fortuneEnchantmentLvl = EnchantmentUtil.isPlayerItemEnchanted((PlayerEntity) event.getSource().getTrueSource(), Enchantments.FORTUNE, EquipmentSlotType.MAINHAND, EnchantmentUtil.ItemEncCalOp.TOTAL_LEVEL);
                if (silkTouchEnchantmentLvl > 0) {
                    double dropHeadRate = 0.025 + 0.01 * fortuneEnchantmentLvl;
                    if (Math.random() < dropHeadRate) {
                        if (event.getEntityLiving() instanceof ZombieEntity)
                            InventoryHelper.spawnItemStack(event.getEntityLiving().world, event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(), event.getEntityLiving().getPosZ(), Items.ZOMBIE_HEAD.getDefaultInstance());
                        else if (event.getEntityLiving() instanceof SkeletonEntity)
                            InventoryHelper.spawnItemStack(event.getEntityLiving().world, event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(), event.getEntityLiving().getPosZ(), Items.SKELETON_SKULL.getDefaultInstance());
                        else if (event.getEntityLiving() instanceof CreeperEntity)
                            InventoryHelper.spawnItemStack(event.getEntityLiving().world, event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(), event.getEntityLiving().getPosZ(), Items.CREEPER_HEAD.getDefaultInstance());
                        else if (event.getEntityLiving() instanceof WitherSkeletonEntity)
                            InventoryHelper.spawnItemStack(event.getEntityLiving().world, event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(), event.getEntityLiving().getPosZ(), Items.WITHER_SKELETON_SKULL.getDefaultInstance());
                        else if (event.getEntityLiving() instanceof EnderDragonEntity)
                            InventoryHelper.spawnItemStack(event.getEntityLiving().world, event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(), event.getEntityLiving().getPosZ(), Items.DRAGON_HEAD.getDefaultInstance());
                    }
                }
                if (fortuneEnchantmentLvl > 0) {
                    dropLoot(event.getEntityLiving(), (PlayerEntity) event.getSource().getTrueSource(), DamageSource.causePlayerDamage((PlayerEntity) event.getSource().getTrueSource()), fortuneEnchantmentLvl);
                }
            }
        }
    }

    protected static void dropLoot(LivingEntity livingEntity, PlayerEntity playerEntity, DamageSource damageSourceIn, int lootLevel) {
        ResourceLocation resourcelocation = livingEntity.getLootTableResourceLocation();
        LootTable loottable = livingEntity.world.getServer().getLootTableManager().getLootTableFromLocation(resourcelocation);
        LootContext.Builder lootcontext$builder = getLootContextBuilder(livingEntity, playerEntity, damageSourceIn);
        LootContext ctx = lootcontext$builder.build(LootParameterSets.ENTITY);
        loottable.generate(ctx).forEach(itemStack -> {
            itemStack = recalculateLootByLootingLevel(itemStack, ctx, lootLevel);
            livingEntity.entityDropItem(itemStack);
        });
    }

    public static ItemStack recalculateLootByLootingLevel(ItemStack stack, LootContext context, int lootLevel) {
        float f = (float) lootLevel * RandomValueRange.of(0F, 1F).generateFloat(context.getRandom());
        stack.grow(Math.round(f));
        return stack;
    }

    protected static LootContext.Builder getLootContextBuilder(LivingEntity livingEntity, PlayerEntity playerEntity, DamageSource damageSourceIn) {
        LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) livingEntity.world)).withRandom(livingEntity.getRNG()).withParameter(LootParameters.THIS_ENTITY, livingEntity).withParameter(LootParameters.ORIGIN, livingEntity.getPositionVec()).withParameter(LootParameters.DAMAGE_SOURCE, damageSourceIn).withNullableParameter(LootParameters.KILLER_ENTITY, damageSourceIn.getTrueSource()).withNullableParameter(LootParameters.DIRECT_KILLER_ENTITY, damageSourceIn.getImmediateSource());
        lootcontext$builder = lootcontext$builder.withParameter(LootParameters.LAST_DAMAGE_PLAYER, playerEntity).withLuck(playerEntity.getLuck());
        return lootcontext$builder;
    }


}
