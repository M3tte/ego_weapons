package net.m3tte.ego_weapons.keybind;

import net.m3tte.ego_weapons.EgoWeaponsMod;
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

    public static int getUiPage() {
        return uiPage;
    }

    public static boolean isHoldingShift() {
        return holdingShift;
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
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event) {

        // Keys that only trigger outside of any GUI
        int key = event.getKey();
        if (Minecraft.getInstance().screen == null) {


            int idxType = -1;
            if (event.getAction() == GLFW.GLFW_PRESS) {
                if (key == this.weaponAbilityBind.getKey().getValue())
                    idxType = 0;
                else if (key == this.armorAbilityKeybind.getKey().getValue())
                    idxType = 1;
                else if (key == this.reloadAbilityKeybind.getKey().getValue())
                    idxType = 2;
                else if (key == this.fireModeKeybind.getKey().getValue())
                    idxType = 3;

                if (idxType >= 0) {
                    EgoWeaponsMod.PACKET_HANDLER.sendToServer(new KeybindPackages.GenericKeybindingPressedMessage(idxType, 0));
                    pressAction(Minecraft.getInstance().player, idxType);
                }


            }
        } else { // Exclusively for generic client side events.
            if (event.getAction() == GLFW.GLFW_PRESS) {
                if (key == this.nextPage.getKey().getValue())
                    nextPage();
                else if (key == this.prevPage.getKey().getValue())
                    prevPage();
                else if (key == this.statusDetail.getKey().getValue())
                    holdingShift = true;
            } else if (event.getAction() == GLFW.GLFW_RELEASE) {
                if (key == this.statusDetail.getKey().getValue())
                    holdingShift = false;
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
