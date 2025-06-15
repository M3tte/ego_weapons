package net.m3tte.ego_weapons.procedures.legacy;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.MimicryMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.particle.DamagefxParticle;
import net.m3tte.ego_weapons.particle.RedflashParticle;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Map;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class MimicryabilityProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		LivingEntity entity = (LivingEntity) dependencies.get("entity");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
		}
		if (!world.isClientSide()) {
			((World) world).playSound(null, new BlockPos(x, y, z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot")),
					SoundCategory.PLAYERS, (float) 2, (float) 1.5);
			((World) world).playSound(null, new BlockPos(x, y, z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:ominouseffect")),
					SoundCategory.PLAYERS, (float) 2, (float) 1.5);
		}
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, (int) 80, (int) 4, (false), (false)));
		{
			double _setval = ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new EgoWeaponsModVars.PlayerVariables())).light - 7);
			entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.light = _setval;
				capability.syncPlayerVariables(entity);
			});
		}

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
		EgoWeaponsModVars.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
		((PlayerEntity) entity).getCooldowns().addCooldown(EgoWeaponsItems.MIMICRY.get(), (int) 20);
		entityData.globalcooldown = 20;

		entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 75, 1));
		entitypatch.playSound(EgoWeaponsSounds.KALI_SPLIT_HORIZONTAL_RING, 1, 1);
		entitypatch.playAnimationSynchronized(MimicryMovesetAnims.GREAT_SPLIT_HORIZONTAL, 0.1f);
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
			EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0.2,0.2f,-0.4f),1,RedflashParticle.particle,0,"Head",true);
		}
		entityData.syncPlayerVariables(entity);
		/*new Object() {
			private int ticks = 0;
			private float waitTicks;
			private IWorld world;

			public void start(IWorld world, int waitTicks) {
				this.waitTicks = waitTicks;
				MinecraftForge.EVENT_BUS.register(this);
				this.world = world;
			}

			@SubscribeEvent
			public void tick(TickEvent.ServerTickEvent event) {
				if (event.phase == TickEvent.Phase.END) {
					this.ticks += 1;
					if (this.ticks >= this.waitTicks)
						run();
				}
			}

			private void run() {
				if (world instanceof ServerWorld) {
					((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
				}
				if (world instanceof ServerWorld) {
					((ServerWorld) world).sendParticles(RedpowerupParticle.particle, x, (y + 1), z, (int) 1, 0, 0, 0, 0);
				}
				if (!entity.level.isClientSide && entity.level.getServer() != null) {
					Optional<FunctionObject> _fopt = entity.level.getServer().getFunctions().get(new ResourceLocation("tcorp:mimicryslashatk"));
					if (_fopt.isPresent()) {
						FunctionObject _fobj = _fopt.get();
						entity.level.getServer().getFunctions().execute(_fobj, entity.createCommandSourceStack());
					}
				}
				if (!world.isClientSide()) {
					((World) world).playSound(null, new BlockPos(x, y, z),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:heavyslash")),
							SoundCategory.PLAYERS, (float) 2, (float) 1);
					((World) world).playSound(null, new BlockPos(x, y, z),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:cross_slash")),
							SoundCategory.PLAYERS, (float) 2, (float) 1);
					((World) world).playSound(null, new BlockPos(x, y, z),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:heavyslash")),
							SoundCategory.PLAYERS, (float) 1, (float) 0.8);
					((World) world).playSound(null, new BlockPos(x, y, z),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:heavyslash")),
							SoundCategory.PLAYERS, (float) 1, (float) 0.5);
					((World) world).playSound(null, new BlockPos(x, y, z),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:heavyslash")),
							SoundCategory.PLAYERS, (float) 1, (float) 0);
				}
				if ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blipcooldown < 40) {
					{
						double _setval = 40;
						entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.blipcooldown = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
				}
				{
					List<Entity> _entfound = world
							.getEntities(entity,
									new AxisAlignedBB(x - (14 / 2d), y - (14 / 2d), z - (14 / 2d), x + (14 / 2d), y + (14 / 2d), z + (14 / 2d)), null)
							.stream().sorted(new Object() {
								Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
									return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
								}
							}.compareDistOf(x, y, z)).collect(Collectors.toList());
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof LivingEntity
								&& (!(entityiterator instanceof PlayerEntity || entityiterator instanceof ServerPlayerEntity)
										|| (entityiterator.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
												.orElse(new TcorpModVariables.PlayerVariables())).teamid != (entity
														.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
														.orElse(new TcorpModVariables.PlayerVariables())).teamid)) {
							if (!world.isClientSide()) {
								new Object() {
									private int ticks = 0;
									private float waitTicks;
									private IWorld world;

									public void start(IWorld world, int waitTicks) {
										this.waitTicks = waitTicks;
										MinecraftForge.EVENT_BUS.register(this);
										this.world = world;
									}

									@SubscribeEvent
									public void tick(TickEvent.ServerTickEvent event) {
										if (event.phase == TickEvent.Phase.END) {
											this.ticks += 1;
											if (this.ticks >= this.waitTicks)
												run();
										}
									}

									private void run() {
										if (entity instanceof LivingEntity)
											((LivingEntity) entity).setHealth(
													(float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) + 1));
										entityiterator.hurt(DamageSource.GENERIC, (float) (22
												+ ((entityiterator instanceof LivingEntity) ? ((LivingEntity) entityiterator).getMaxHealth() : -1)
														* 0.2));
										if (world instanceof World && !((World) world).isClientSide) {
											((World) world).playSound(null,
													new BlockPos(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()),
													(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
															.getValue(new ResourceLocation("tcorp:cross_slash")),
													SoundCategory.PLAYERS, (float) 1, (float) 1);
										} else {
											((World) world).playLocalSound((entityiterator.getX()), (entityiterator.getY()),
													(entityiterator.getZ()),
													(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
															.getValue(new ResourceLocation("tcorp:cross_slash")),
													SoundCategory.PLAYERS, (float) 1, (float) 1, false);
										}
										if (world instanceof ServerWorld) {
											((ServerWorld) world).sendParticles(ReddamageslashParticle.particle, (entityiterator.getX()),
													(entityiterator.getY() + 1), (entityiterator.getZ()), (int) 4, 0.4, 0.6, 0.4, 0);
										}
										if (((entity instanceof LivingEntity)
												? ((LivingEntity) entity).getHealth()
												: -1) > ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMaxHealth() : -1)) {
											if (entity instanceof LivingEntity)
												((LivingEntity) entity).setHealth(
														(float) ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMaxHealth() : -1));
										}
										MinecraftForge.EVENT_BUS.unregister(this);
									}
								}.start(world, (int) (10 + MathHelper.nextInt(new Random(), 1, 20)));
							}
						}
					}
				}
				if (entity instanceof PlayerEntity)
					((PlayerEntity) entity).getCooldowns().addCooldown(
							((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem()), (int) 40);
				{
					double _setval = 40;
					entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.globalcooldown = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				MinecraftForge.EVENT_BUS.unregister(this);
			}
		}.start(world, (int) 40);*/
	}
}
