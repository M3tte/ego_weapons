package net.m3tte.ego_weapons.keybind;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.network.packages.KeybindPackages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

import static net.m3tte.ego_weapons.network.packages.KeybindPackages.pressAction;

public class EgoWeaponsKeybinds {

    private static int uiPage = 0;

    private static boolean holdingShift = false;
    private static boolean holdingAltAbility = false;

    public static int getUiPage() {
        return uiPage;
    }

    public static boolean isHoldingShift() {
        return holdingShift;
    }

    public static boolean isHoldingAltAbility() {
        return holdingAltAbility;
    }

    public static void setUiPage(int uiPage) {
        EgoWeaponsKeybinds.uiPage = uiPage;
    }

    @OnlyIn(Dist.CLIENT)
    private final KeyBinding weaponAbilityBind;
    @OnlyIn(Dist.CLIENT)
    private final KeyBinding armorAbilityKeybind;
    @OnlyIn(Dist.CLIENT)
    private final KeyBinding reloadAbilityKeybind;
    @OnlyIn(Dist.CLIENT)
    private final KeyBinding fireModeKeybind;
    @OnlyIn(Dist.CLIENT)
    private final KeyBinding nextPage;
    @OnlyIn(Dist.CLIENT)
    private final KeyBinding prevPage;
    @OnlyIn(Dist.CLIENT)
    private final KeyBinding statusDetail;



    @OnlyIn(Dist.CLIENT)
    private final KeyBinding altAbility;
    public EgoWeaponsKeybinds() {
        MinecraftForge.EVENT_BUS.register(this);

        this.weaponAbilityBind = new KeyBinding("key.ego_weapons.weapon_ability", GLFW.GLFW_KEY_X, "key.categories.ego_weapons");
        ClientRegistry.registerKeyBinding(this.weaponAbilityBind);

        this.armorAbilityKeybind = new KeyBinding("key.ego_weapons.armor_ability", GLFW.GLFW_KEY_C, "key.categories.ego_weapons");
        ClientRegistry.registerKeyBinding(this.armorAbilityKeybind);

        this.reloadAbilityKeybind = new KeyBinding("key.ego_weapons.reload_ability", GLFW.GLFW_KEY_V, "key.categories.ego_weapons");
        ClientRegistry.registerKeyBinding(this.reloadAbilityKeybind);

        this.fireModeKeybind = new KeyBinding("key.ego_weapons.fire_mode", GLFW.GLFW_KEY_Z, "key.categories.ego_weapons");
        ClientRegistry.registerKeyBinding(this.fireModeKeybind);

        this.prevPage = new KeyBinding("key.ego_weapons.previous_page", GLFW.GLFW_KEY_LEFT, "key.categories.ego_weapons");
        ClientRegistry.registerKeyBinding(this.prevPage);

        this.nextPage = new KeyBinding("key.ego_weapons.next_page", GLFW.GLFW_KEY_RIGHT, "key.categories.ego_weapons");
        ClientRegistry.registerKeyBinding(this.nextPage);

        this.statusDetail = new KeyBinding("key.ego_weapons.status_detail", GLFW.GLFW_KEY_LEFT_SHIFT, "key.categories.ego_weapons");
        ClientRegistry.registerKeyBinding(this.statusDetail);

        this.altAbility = new KeyBinding("key.ego_weapons.alt_ability", GLFW.GLFW_KEY_LEFT_SHIFT, "key.categories.ego_weapons");
        ClientRegistry.registerKeyBinding(this.altAbility);
    }


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event) {

        // Keys that only trigger outside of any GUI
        int key = event.getKey();


        int idxType = -1;

        boolean requiresNullScreen = true;

        // Server Side Press Events
        if (event.getAction() == GLFW.GLFW_PRESS) {
            idxType = -1;
            // If alt ability is held, change weapon and reload ability.

            // Need to have no screen open to work
            if (Minecraft.getInstance().screen == null) {
                if (event.getKey() == weaponAbilityBind.getKey().getValue())
                    idxType = 0;

                if (event.getKey() == armorAbilityKeybind.getKey().getValue())
                    idxType = 1;

                if (event.getKey() == reloadAbilityKeybind.getKey().getValue())
                    idxType = 2;

            }
            // Do not need to have all screens closed
            if (event.getKey() == fireModeKeybind.getKey().getValue())
                idxType = 3;



            if (holdingAltAbility) {
                if (idxType == 0 || idxType == 2)
                    idxType = idxType == 0 ? 8 : 9; // Change to 4 for weapon ability or 5 for reload ability
            }


            if (idxType >= 0) {
                EgoWeaponsMod.PACKET_HANDLER.sendToServer(new KeybindPackages.GenericKeybindingPressedMessage(idxType, 0));
                pressAction(Minecraft.getInstance().player, idxType);
            }
        }

        // Client Side Press Events

        idxType = event.getKey();

        if (event.getAction() == GLFW.GLFW_PRESS) {

            if (idxType == this.nextPage.getKey().getValue())
                nextPage();

            if (idxType == this.prevPage.getKey().getValue())
                prevPage();

            if (idxType == this.statusDetail.getKey().getValue())
                holdingShift = true;

            if (idxType == this.altAbility.getKey().getValue()) {
                holdingAltAbility = true;
                if (Minecraft.getInstance().player != null) {
                    Minecraft.getInstance().player.playSound(EgoWeaponsSounds.PAPER_FLIP, 1, 1);
                }
            }

        } else if (event.getAction() == GLFW.GLFW_RELEASE) {

            if (idxType == this.statusDetail.getKey().getValue())
                holdingShift = false;

            if (idxType == this.altAbility.getKey().getValue()) {
                holdingAltAbility = false;
                if (Minecraft.getInstance().player != null) {
                    Minecraft.getInstance().player.playSound(EgoWeaponsSounds.PAPER_FLIP, 1, 0.5f);
                }
            }



        }

    }

    private void nextPage() {
        uiPage += 1;
    }

    private void prevPage() {
        uiPage -= 1;
        if (uiPage < 0)
            uiPage = 0;
    }
}
