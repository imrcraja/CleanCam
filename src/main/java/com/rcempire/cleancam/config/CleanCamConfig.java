package com.rcempire.cleancam.config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rcempire.cleancam.CleanCamClient;
import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class CleanCamConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("cleancam.json");
    public static boolean showTutorialOnFirstOpen = true;
    public static boolean captureRequested = false;
    public static int targetFps = 30, targetWidth = 1280, targetHeight = 720;
    private CleanCamConfig() {}
    public static void load() {
        if (!Files.exists(FILE)) { save(); return; }
        try {
            ConfigData d = GSON.fromJson(Files.readString(FILE, StandardCharsets.UTF_8), ConfigData.class);
            if (d == null) return;
            showTutorialOnFirstOpen=d.showTutorialOnFirstOpen; captureRequested=d.captureRequested;
            targetFps=clamp(d.targetFps,15,60); targetWidth=clamp(d.targetWidth,640,1920); targetHeight=clamp(d.targetHeight,360,1080);
        } catch (Exception e) { CleanCamClient.LOGGER.warn("Using CleanCam defaults; config could not be loaded",e); }
    }
    public static void save() {
        try {
            Files.createDirectories(FILE.getParent()); ConfigData d=new ConfigData();
            d.showTutorialOnFirstOpen=showTutorialOnFirstOpen; d.captureRequested=captureRequested;
            d.targetFps=targetFps; d.targetWidth=targetWidth; d.targetHeight=targetHeight;
            Files.writeString(FILE,GSON.toJson(d),StandardCharsets.UTF_8);
        } catch(IOException e) { CleanCamClient.LOGGER.error("Could not save CleanCam config",e); }
    }
    private static int clamp(int v,int min,int max) { return Math.max(min,Math.min(max,v)); }
    private static final class ConfigData {
        boolean showTutorialOnFirstOpen=true, captureRequested=false;
        int targetFps=30,targetWidth=1280,targetHeight=720;
    }
}
