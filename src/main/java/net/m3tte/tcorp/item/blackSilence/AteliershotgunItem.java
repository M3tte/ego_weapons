
package net.m3tte.tcorp.item.blackSilence;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ActionResult;
import net.minecraft.network.IPacket;
import net.minecraft.item.UseAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Blocks;

import net.m3tte.tcorp.procedures.legacy.AteliershotgunhitProcedure;
import net.m3tte.tcorp.procedures.legacy.AtelierpistolsRangedItemUsedProcedure;
import net.m3tte.tcorp.TcorpModElements;

import javax.annotation.Nullable;
import java.util.stream.Stream;
import java.util.Random;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.AbstractMap;

@TcorpModElements.ModElement.Tag
public class AteliershotgunItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:atelier_shotgun")
	public static final Item block = null;
	public static final EntityType<ArrowCustomEntity> shotgun_slug = (EntityType<ArrowCustomEntity>) (EntityType.Builder.<ArrowCustomEntity>of(ArrowCustomEntity::new, EntityClassification.MISC)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).setCustomClientFactory(ArrowCustomEntity::new)
			.sized(0.5f, 0.5f)).build("projectile_ateliershotgun");

	public AteliershotgunItem(TcorpModElements instance) {
		super(instance, 204);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemRanged());
	}

	public static class ItemRanged extends Item {
		public ItemRanged() {
			super(new Properties().tab(null).stacksTo(1));
			setRegistryName("atelier_shotgun");
		}

		@Override
		public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {
			entity.startUsingItem(hand);
			return new ActionResult(ActionResultType.SUCCESS, entity.getItemInHand(hand));
		}

		@Override
		public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
			super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
			list.add(new StringTextComponent("A shotgun... darkened with wrath"));
			list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Swap to next weapon. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 1 E").withStyle(TextFormatting.AQUA)));

		}

		@Override
		public UseAction getUseAnimation(ItemStack p_77661_1_)  {
			return UseAction.BOW;
		}
		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 72000;
		}


		@Override
		public void releaseUsing(ItemStack itemstack, World world, LivingEntity entityLiving, int timeLeft) {
			if (!world.isClientSide && entityLiving instanceof ServerPlayerEntity) {
				ServerPlayerEntity entity = (ServerPlayerEntity) entityLiving;
				double x = entity.getX();
				double y = entity.getY();
				double z = entity.getZ();
				if (true) {
					ArrowCustomEntity entityarrow = shoot(world, entity, random, 1.4f, 3.5, 0);
					itemstack.hurt(1, random, entity);
					entityarrow.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;

					AtelierpistolsRangedItemUsedProcedure.executeProcedure(
							Stream.of(new AbstractMap.SimpleEntry<>("entity", entity), new AbstractMap.SimpleEntry<>("itemstack", itemstack))
									.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
				}
			}
		}
	}

	@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
	public static class ArrowCustomEntity extends AbstractArrowEntity implements IRendersAsItem {
		public ArrowCustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			super(shotgun_slug, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, World world) {
			super(type, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, double x, double y, double z, World world) {
			super(type, x, y, z, world);
		}

		public ArrowCustomEntity(EntityType<? extends ArrowCustomEntity> type, LivingEntity entity, World world) {
			super(type, entity, world);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack getItem() {
			return new ItemStack(Blocks.WARPED_STEM);
		}




		@Override
		protected void doPostHurtEffects(LivingEntity entity) {
			super.doPostHurtEffects(entity);
		}

		@Override
		public void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
			super.onHitEntity(entityRayTraceResult);
			Entity entity = entityRayTraceResult.getEntity();
			Entity sourceentity = this.getEntity();
			Entity immediatesourceentity = this;
			double x = this.getX();
			double y = this.getY();
			double z = this.getZ();
			World world = this.level;

			AteliershotgunhitProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity),
							new AbstractMap.SimpleEntry<>("sourceentity", sourceentity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}

		@Override
		public void tick() {
			super.tick();
			double x = this.getX();
			double y = this.getY();
			double z = this.getZ();
			World world = this.level;
			Entity entity = this.getEntity();
			Entity immediatesourceentity = this;
			if (this.inGround)
				this.remove();
		}

		@Override
		protected ItemStack getPickupItem()  {
			return ItemStack.EMPTY;
		}
	}

	public static ArrowCustomEntity shoot(World world, LivingEntity entity, Random random, float power, double damage, int knockback) {
		ArrowCustomEntity entityarrow = new ArrowCustomEntity(shotgun_slug, entity, world);
		entityarrow.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		world.playSound((PlayerEntity) null, (double) x, (double) y, (double) z,
				(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:ateliershoot")),
				SoundCategory.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

}
