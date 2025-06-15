
package net.m3tte.ego_weapons.item.stigma_workshop;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.movesets.FirefistMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.StigmaWorkshopMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class StigmaWorkshopSword extends EgoWeaponsWeapon {

	private static IItemTier stigmaSwordTier = new IItemTier() {

		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 4.0f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 4.5f;
		}

		@Override
		public int getLevel() {
			return 1;
		}

		@Override
		public int getEnchantmentValue() {
			return 2;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.EMPTY;
		}

	};

	public StigmaWorkshopSword(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(stigmaSwordTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}




	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("A sword manufactured by Stigma Workshop").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.teth"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 5) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn", "branding_blade"});
				else
					generateDescription(list,"stigma_workshop_sword", "passive", 4);
				break;
			case 1:
                if (EgoWeaponsKeybinds.isHoldingShift())
                    generateStatusDescription(list, new String[]{"red", "burn", "branding_blade"});
                else
                    generateDescription(list, "stigma_workshop_sword", "ability", 8);
                break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn"});
				else {
					generateDescription(list,"stigma_workshop_sword", "innate", 4);
				}

				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn"});
				else
					generateDescription(list,"stigma_workshop_sword", "auto", 5);
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "burn"});
				else
					generateDescription(list,"stigma_workshop_sword", "dash", 2);
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}



	public static void interruptedAttack(LivingEntity target) {

	}



	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);
		double x = target.getX();
		double y = target.getY();
		double z = target.getZ();


		World world = target.level;

		PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 8, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));


		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

			AttackAnimation.Phase phase = null;
			if (currentanim instanceof EgoAttackAnimation) {
				phase = ((EgoAttackAnimation)currentanim).getPhaseByTime(entitypatch.getAnimator().getPlayerFor(currentanim).getElapsedTime());
			}

			if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
				String elp = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER).orElse(null);

				if (elp != null)
					weaponIdentifier = elp;
			}
			int brandingBladeBuff = sourceentity.hasEffect(EgoWeaponsEffects.BRANDING_BLADE.get()) ? 1 : 0;

			switch (weaponIdentifier) {
				case "stigma_w_s_sp_1":
				case "stigma_w_s_sp_2":
					EgoWeaponsEffects.BURN.get().increment(target, brandingBladeBuff, 2 + brandingBladeBuff);
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get().getRegistryName()));

					break;
				case "stigma_w_s_innate_1":
				case "stigma_w_s_innate_2":
				case "stigma_w_s_innate_3":
					EgoWeaponsEffects.BURN.get().increment(target, 1, brandingBladeBuff);
					if (!sourceentity.level.isClientSide()) {
						if (weaponIdentifier.equals("stigma_w_s_innate_2")) {
							EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.STIGMA_WORKSHOP_SLASH_DOWN.get().getRegistryName()));
						} else {
							EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.STIGMA_WORKSHOP_SLASH_UP.get().getRegistryName()));

						}
					}


					break;
				case "stigma_w_s_dash":
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), sourceentity.getId(), EgoWeaponsParticles.LIU_S6_AUTO_SIDE.get().getRegistryName()));

				case "stigma_w_s_auto3":
				case "stigma_w_s_auto2":
				case "stigma_w_s_auto1":
					EgoWeaponsEffects.BURN.get().increment(target, 0, 1 + brandingBladeBuff);
					break;
			}
		}

		return true;
	}

	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

		PlayerVariables entityData = source.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);


		if (source.hasEffect(EgoWeaponsEffects.BRANDING_BLADE.get())) {
			SharedFunctions.incrementBonusDamage(damageSource, 0.1f);
			mult += 0.1f;
		}

		switch (weaponIdentifier) {
			case "stigma_w_s_sp_1":
			case "stigma_w_s_sp_2":
				int burnMult = EgoWeaponsEffects.BURN.get().getPotency(target) / 7;

				if (burnMult > 0) {
					SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.2f, burnMult * 0.1f));
					mult += Math.min(0.2f, burnMult * 0.1f);
				}
				break;
			case "stigma_w_s_innate_2":
				SharedFunctions.incrementBonusDamage(damageSource, 0.2f);
				mult += 0.2f;
				break;
			case "stigma_w_s_innate_3":
				SharedFunctions.incrementBonusDamage(damageSource, 0.4f);
				mult += 0.4f;
				break;
		}



		return mult;
	}

	public static StaticAnimation.Event[] bladeCheckEvent() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {


			if (!entitypatch.getOriginal().hasEffect(EgoWeaponsEffects.BRANDING_BLADE.get()) && entitypatch.getValidItemInHand(Hand.MAIN_HAND).getItem().equals(EgoWeaponsItems.STIGMA_WORKSHOP_SWORD.get())) {
				entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("glow", 0);
			}

		}, StaticAnimation.Event.Side.BOTH);

		return events;
	}

	public static StaticAnimation.Event[] stigmatizeEvents(float secondEvent) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[4];

		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {

			entitypatch.playSound(EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_SPECIAL_CHARGE, 1, 1);
		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(0.55f, (entitypatch) -> {
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("glow", 1);

			if (entitypatch.getOriginal().level.isClientSide()) {
				entitypatch.playSound(EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_SPECIAL_IGNITE, 1, 1);
				spawnArmatureParticle(entitypatch, 0, new Vector3d(-0.05,0.15,-0.3), 1, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_IGNITE.get(), 0, "Tool_R", false);
				spawnArmatureParticle(entitypatch, 0, new Vector3d(-0.05,0.15,-0.6), 1, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_IGNITE.get(), 0, "Tool_R", false);

				spawnArmatureParticle(entitypatch, 0, new Vector3d(-0.03,0.15,-0.6), 1, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_IGNITE_SIDE.get(), new Vector3f(0, entitypatch.getOriginal().getId(), entitypatch.getOriginal().getId()), "Tool_R");
				spawnArmatureParticle(entitypatch, 0, new Vector3d(-0.06,0.15,-0.6), 1, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_IGNITE_SIDE.get(), new Vector3f(0, entitypatch.getOriginal().getId(), entitypatch.getOriginal().getId()), "Tool_R");
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0.0,0.15,-0.6), 1, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_IGNITE_SIDE.get(), new Vector3f(0, entitypatch.getOriginal().getId(), entitypatch.getOriginal().getId()), "Tool_R");

				for (int i = 0; i < 10; i++)
					spawnArmatureParticle(entitypatch, 0, new Vector3d(0.0 + random.nextFloat() * -0.1f,0.05 + random.nextFloat() * 0.2f,-0.3 - random.nextFloat() * 1.5f), 1, EgoWeaponsParticles.SIMPLE_EMBER.get(), new Vector3f(0.05f, 0.5f, 1.5f), "Tool_R");

			}

			if (!entitypatch.getOriginal().hasEffect(EgoWeaponsEffects.BRANDING_BLADE.get())) {
				entitypatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.BRANDING_BLADE.get(), 300, 0));
			}

		}, StaticAnimation.Event.Side.BOTH);


		events[2] = StaticAnimation.Event.create(1.8f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);
		}, StaticAnimation.Event.Side.BOTH);

		events[3] = StaticAnimation.Event.create(secondEvent, (entitypatch) -> {

			entitypatch.playAnimationSynchronized(StigmaWorkshopMovesetAnims.STIGMA_SWORD_SPECIAL_2, 0.15f);
		}, StaticAnimation.Event.Side.SERVER);

		return events;
	}

	@Override
	public String getDefaultKillIdentifier() {
		return "stigma_sword";
	}
	public static StaticAnimation.Event[] stigmatizeEventsB(float secondEvent) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];

		events[0] = StaticAnimation.Event.create(0.8f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);
		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(secondEvent, (entitypatch) -> {


			entitypatch.playAnimationSynchronized(StigmaWorkshopMovesetAnims.STIGMA_SWORD_SPECIAL_2, 0.15f);
		}, StaticAnimation.Event.Side.SERVER);

		return events;
	}

	public static StaticAnimation.Event[] stigmatizeEvents2() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.15f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();

			entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);

			entitypatch.playSound(EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_SPECIAL_BACKPEDAL, 1, 1);
		}, StaticAnimation.Event.Side.BOTH);

		return events;
	}
}
