package com.rcempire.cleancam;
import com.rcempire.cleancam.config.CleanCamConfig;
import com.rcempire.cleancam.ui.CleanCamScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CleanCamClient implements ClientModInitializer {
    public static final String MOD_ID="cleancam";
    public static final Logger LOGGER=LoggerFactory.getLogger(MOD_ID);
    private static KeyBinding openStudio,toggleCapture,openTutorial;
    @Override public void onInitializeClient() {
        CleanCamConfig.load();
        openStudio=KeyBindingHelper.registerKeyBinding(new KeyBinding("key.cleancam.open_studio",InputUtil.Type.KEYSYM,GLFW.GLFW_KEY_UNKNOWN,"category.cleancam"));
        toggleCapture=KeyBindingHelper.registerKeyBinding(new KeyBinding("key.cleancam.toggle_capture",InputUtil.Type.KEYSYM,GLFW.GLFW_KEY_F8,"category.cleancam"));
        openTutorial=KeyBindingHelper.registerKeyBinding(new KeyBinding("key.cleancam.open_tutorial",InputUtil.Type.KEYSYM,GLFW.GLFW_KEY_F7,"category.cleancam"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(openStudio.wasPressed()) openScreen(client,CleanCamScreen.Page.HOME);
            while(openTutorial.wasPressed()) openScreen(client,CleanCamScreen.Page.TUTORIAL);
            while(toggleCapture.wasPressed()) {
                CleanCamConfig.captureRequested=!CleanCamConfig.captureRequested; CleanCamConfig.save();
                if(client.player!=null) client.player.sendMessage(Text.literal("CleanCam capture request "+(CleanCamConfig.captureRequested?"ON":"OFF")+" — video recorder not implemented yet."),true);
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(new ClientTickEvents.EndTick() {
            private boolean checked;
            @Override public void onEndTick(MinecraftClient client) {
                if(!checked && client.player!=null && client.world!=null) {
                    checked=true;
                    if(CleanCamConfig.showTutorialOnFirstOpen) {
                        CleanCamConfig.showTutorialOnFirstOpen=false; CleanCamConfig.save();
                        client.setScreen(new CleanCamScreen(null,CleanCamScreen.Page.TUTORIAL));
                    }
                }
            }
        });
        LOGGER.info("CleanCam alpha initialized for Minecraft 1.20.1");
    }
    private static void openScreen(MinecraftClient client,CleanCamScreen.Page page) { client.setScreen(new CleanCamScreen(client.currentScreen,page)); }
}
