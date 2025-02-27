package net.m3tte.ego_weapons.world.capabilities;

import net.m3tte.ego_weapons.item.guns.AmmoDescriptors;
import net.m3tte.ego_weapons.item.guns.AmmoItem;
import net.m3tte.ego_weapons.item.guns.GunItem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.LinkedList;

public class AmmoSystem {




    public static int[] loadLimitedFromRight(ItemStack gunItem, ItemStack ammoItem, LivingEntity target, int roundLimit) {
        /*
        Add ammo detection handling later
        if (ammoItem == null) {
            // Later add logic for detecting ammo.
            return;
        }*/

        int roundsLoaded = 0;

        if (gunItem.getItem() instanceof GunItem) {
            GunItem gunType = (GunItem) gunItem.getItem();

            int[] oldAmmo = gunItem.getOrCreateTag().getIntArray("loadedAmmo");
            LinkedList<Integer> newAmmo = new LinkedList<>();

            for (int ammoIdx : oldAmmo)
                newAmmo.add(ammoIdx);

            if (target instanceof PlayerEntity) {
                int x = 0;

                LinkedList<ItemStack> validInventoryItems = new LinkedList<>();
                for (ItemStack it : ((PlayerEntity) target).inventory.items) {
                    validInventoryItems.addFirst(it);
                    if (x >= 8)
                        break;

                    x++;
                }

                for (ItemStack it : validInventoryItems) {
                    int length = newAmmo.size();

                    if (it.getItem() instanceof AmmoItem) {

                        if (((AmmoItem)it.getItem()).getCaliber().equals(gunType.getCaliber())) {

                            if (it.getCount() + length <= gunType.getMaxAmmo() && it.getCount() <= roundLimit - roundsLoaded) {
                                for (int i = 0; i < it.getCount(); i++) {
                                    roundsLoaded++;
                                    newAmmo.push(((AmmoItem) it.getItem()).getAmmoType().ordinal());
                                }
                                ((PlayerEntity) target).inventory.removeItem(it);
                            } else if ((roundLimit - roundsLoaded) > 0) {
                                int consumed = 0;
                                while (newAmmo.size() < gunType.getMaxAmmo()) {
                                    roundsLoaded++;
                                    consumed++;
                                    newAmmo.push(((AmmoItem) it.getItem()).getAmmoType().ordinal());

                                    if (roundsLoaded >= roundLimit)
                                        break;
                                }
                                it.setCount(it.getCount() - consumed);
                            }
                        }


                    }

                    if (newAmmo.size() >= gunType.getMaxAmmo()) {
                        break;
                    }
                }
                ((PlayerEntity) target).inventory.setChanged();
            }

            /*while (newAmmo.size() < gunType.getMaxAmmo()) {
                newAmmo.push(target.getRandom().nextInt(AmmoType.values().length));
            }*/

            gunItem.getOrCreateTag().putIntArray("loadedAmmo", newAmmo);
            if (target instanceof PlayerEntity)
                ((PlayerEntity) target).inventory.setChanged();

            return new int[]{newAmmo.size(), roundsLoaded};
        }
        return new int[]{0, 0};

    }

    public static int[] loadLimitedFromLeft(ItemStack gunItem, ItemStack ammoItem, LivingEntity target, int roundLimit) {
        /*
        Add ammo detection handling later
        if (ammoItem == null) {
            // Later add logic for detecting ammo.
            return;
        }*/

        int roundsLoaded = 0;

        if (gunItem.getItem() instanceof GunItem) {
            GunItem gunType = (GunItem) gunItem.getItem();

            int[] oldAmmo = gunItem.getOrCreateTag().getIntArray("loadedAmmo");
            LinkedList<Integer> newAmmo = new LinkedList<>();

            for (int ammoIdx : oldAmmo)
                newAmmo.add(ammoIdx);


            if (target instanceof PlayerEntity) {
                int x = 0;

                LinkedList<ItemStack> validInventoryItems = new LinkedList<>();
                for (ItemStack it : ((PlayerEntity) target).inventory.items) {
                    validInventoryItems.addLast(it);
                    if (x >= 8)
                        break;

                    x++;
                }

                for (ItemStack it : validInventoryItems) {
                    int length = newAmmo.size();

                    if (it.getItem() instanceof AmmoItem) {

                        if (((AmmoItem)it.getItem()).getCaliber().equals(gunType.getCaliber())) {

                            if (it.getCount() + length <= gunType.getMaxAmmo() && it.getCount() <= roundLimit - roundsLoaded) {
                                for (int i = 0; i < it.getCount(); i++) {
                                    roundsLoaded++;
                                    newAmmo.push(((AmmoItem) it.getItem()).getAmmoType().ordinal());
                                }
                                ((PlayerEntity) target).inventory.removeItem(it);
                            } else if ((roundLimit - roundsLoaded) > 0) {
                                int consumed = 0;
                                while (newAmmo.size() < gunType.getMaxAmmo()) {
                                    roundsLoaded++;
                                    consumed++;
                                    newAmmo.push(((AmmoItem) it.getItem()).getAmmoType().ordinal());

                                    if (roundsLoaded >= roundLimit)
                                        break;
                                }
                                it.setCount(it.getCount() - consumed);
                            }
                        }


                    }

                    if (newAmmo.size() >= gunType.getMaxAmmo()) {
                        break;
                    }
                }
                ((PlayerEntity) target).inventory.setChanged();
            }

            /*while (newAmmo.size() < gunType.getMaxAmmo()) {
                newAmmo.push(target.getRandom().nextInt(AmmoType.values().length));
            }*/

            gunItem.getOrCreateTag().putIntArray("loadedAmmo", newAmmo);
            if (target instanceof PlayerEntity)
                ((PlayerEntity) target).inventory.setChanged();

            return new int[]{newAmmo.size(), roundsLoaded};
        }
        return new int[]{0, 0};

    }

    public static void reloadGun(ItemStack gunItem, ItemStack ammoItem, LivingEntity target) {
        /*
        Add ammo detection handling later
        if (ammoItem == null) {
            // Later add logic for detecting ammo.
            return;
        }*/

        if (gunItem.getItem() instanceof GunItem) {
            GunItem gunType = (GunItem) gunItem.getItem();

            int[] oldAmmo = gunItem.getOrCreateTag().getIntArray("loadedAmmo");
            LinkedList<Integer> newAmmo = new LinkedList<>();

            for (int ammoIdx : oldAmmo)
                newAmmo.add(ammoIdx);


            if (target instanceof PlayerEntity) {
                int x = 0;
                for (ItemStack it : ((PlayerEntity) target).inventory.items) {
                    System.out.println("ITEM IN SLOT: "+x+" IS : "+it.toString());
                    int length = newAmmo.size();

                    if (it.getItem() instanceof AmmoItem) {

                        if (((AmmoItem)it.getItem()).getCaliber().equals(gunType.getCaliber())) {
                            if (it.getCount() + length <= gunType.getMaxAmmo()) {
                                for (int i = 0; i < it.getCount(); i++)
                                    newAmmo.push(((AmmoItem) it.getItem()).getAmmoType().ordinal());
                                ((PlayerEntity) target).inventory.removeItem(it);
                            } else {
                                int consumed = 0;
                                while (newAmmo.size() < gunType.getMaxAmmo()) {
                                    consumed++;
                                    newAmmo.push(((AmmoItem) it.getItem()).getAmmoType().ordinal());
                                }
                                it.setCount(it.getCount() - consumed);
                            }
                        }


                    }

                    if (newAmmo.size() >= gunType.getMaxAmmo()) {
                        break;
                    }

                    if (x >= 8)
                        break;
                    x++;
                }
                ((PlayerEntity) target).inventory.setChanged();
            }

            /*while (newAmmo.size() < gunType.getMaxAmmo()) {
                newAmmo.push(target.getRandom().nextInt(AmmoType.values().length));
            }*/

            gunItem.getOrCreateTag().putIntArray("loadedAmmo", newAmmo);
            if (target instanceof PlayerEntity)
                ((PlayerEntity) target).inventory.setChanged();
        }
    }


    public static boolean hasValidAmmoFor(ItemStack gunItem, LivingEntity target) {
        if (gunItem.getItem() instanceof GunItem) {
            GunItem gunType = (GunItem) gunItem.getItem();

            if (target instanceof PlayerEntity) {
                int x = 0;
                for (ItemStack it : ((PlayerEntity) target).inventory.items) {

                    if (it.getItem() instanceof AmmoItem) {

                        if (((AmmoItem)it.getItem()).getCaliber().equals(gunType.getCaliber())) {
                            return true;
                        }


                    }


                    if (x >= 8)
                        break;
                    x++;
                }
            }

        }
        return false;
    }

    public static int getAmmoCount(ItemStack gunItem) {
        int[] ammo = gunItem.getOrCreateTag().getIntArray("loadedAmmo");
        return ammo.length;
    }

    public static AmmoType detectAmmo(ItemStack gunItem) {
        int[] ammo = gunItem.getOrCreateTag().getIntArray("loadedAmmo");
        return ammo.length > 0 ? AmmoType.values()[(ammo[ammo.length-1])] : null;
    }

    public static AmmoType getAndRemovelastammo(ItemStack gunItem, LivingEntity target, boolean markWeapon) {
        int[] ammo = gunItem.getOrCreateTag().getIntArray("loadedAmmo");

        AmmoType ammoType = null;

        if (ammo.length > 0) {

            LinkedList<Integer> newAmmo = new LinkedList<>();
            for (int ammoIdx : ammo)
                newAmmo.add(ammoIdx);

            ammoType = AmmoType.values()[newAmmo.removeFirst()];

            if (markWeapon)
                gunItem.getOrCreateTag().putInt("lastFired", ammoType.ordinal());

            gunItem.getOrCreateTag().putIntArray("loadedAmmo", newAmmo);

            if (target instanceof PlayerEntity)
                ((PlayerEntity) target).inventory.setChanged();
        }

        return ammoType;
    }
}
