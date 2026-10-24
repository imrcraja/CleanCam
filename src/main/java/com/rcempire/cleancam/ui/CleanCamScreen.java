package com.rcempire.cleancam.ui;
import com.rcempire.cleancam.config.CleanCamConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.nio.file.Path;

public final class CleanCamScreen extends Screen {
    public enum Page { HOME,TUTORIAL,ABOUT,STORAGE }
    private final Screen parent;
    private Page page;
    private int tutorialPage;
    public CleanCamScreen(Screen parent,Page page) { super(Text.literal("CleanCam")); this.parent=parent; this.page=page; }
    @Override protected void init() {
        int cx=width/2,y=52;
        if(page==Page.HOME) {
            addDrawableChild(ButtonWidget.builder(Text.literal("Tutorial & Help"),b->setPage(Page.TUTORIAL)).dimensions(cx-100,y,200,20).build()); y+=25;
            addDrawableChild(ButtonWidget.builder(Text.literal("Storage & Paths"),b->setPage(Page.STORAGE)).dimensions(cx-100,y,200,20).build()); y+=25;
            addDrawableChild(ButtonWidget.builder(Text.literal("About & Keybinds"),b->setPage(Page.ABOUT)).dimensions(cx-100,y,200,20).build()); y+=25;
            addDrawableChild(ButtonWidget.builder(Text.literal("Capture request: "+(CleanCamConfig.captureRequested?"ON":"OFF")),b->{CleanCamConfig.captureRequested=!CleanCamConfig.captureRequested;CleanCamConfig.save();setPage(Page.HOME);}).dimensions(cx-100,y,200,20).build());
        } else {
            addDrawableChild(ButtonWidget.builder(Text.literal("Back to CleanCam"),b->setPage(Page.HOME)).dimensions(cx-100,height-30,200,20).build());
            if(page==Page.TUTORIAL) addDrawableChild(ButtonWidget.builder(Text.literal(tutorialPage==0?"Next tutorial page":"Previous tutorial page"),b->{tutorialPage=tutorialPage==0?1:0;clearAndInit();}).dimensions(cx-100,height-56,200,20).build());
        }
    }
    private void setPage(Page p) { page=p;tutorialPage=0;clearAndInit(); }
    @Override public void render(DrawContext c,int mx,int my,float delta) {
        renderBackground(c);super.render(c,mx,my,delta);int x=width/2;
        c.drawCenteredTextWithShadow(textRenderer,"CleanCam",x,16,0xFFFFFF);
        c.drawCenteredTextWithShadow(textRenderer,"Created by RC Empire • Minecraft Java 1.20.1",x,30,0xA8DADC);
        if(page==Page.HOME) line(c,"Open tutorial any time with F7 by default.",x,height-48,0xFFFFFF);
        if(page==Page.TUTORIAL) tutorial(c,x);
        if(page==Page.ABOUT) about(c,x);
        if(page==Page.STORAGE) storage(c,x);
    }
    private void tutorial(DrawContext c,int x) {
        if(tutorialPage==0) {
            line(c,"TUTORIAL 1/2 — SETUP",x,58,0x8FE3CF);
            line(c,"1. Install Fabric Loader for Minecraft 1.20.1.",x,80,0xFFFFFF);
            line(c,"2. Add matching Fabric API to your mods folder.",x,96,0xFFFFFF);
            line(c,"3. Add CleanCam JAR and launch Fabric profile.",x,112,0xFFFFFF);
            line(c,"4. Options > Controls > Key Binds > CleanCam.",x,128,0xFFFFFF);
            line(c,"5. Reassign shortcuts if another mod uses the key.",x,144,0xFFFFFF);
        } else {
            line(c,"TUTORIAL 2/2 — MOBILE CAPTURE",x,58,0x8FE3CF);
            line(c,"This alpha does NOT record video or audio yet.",x,80,0xFFCC80);
            line(c,"F8 only toggles a saved prototype flag.",x,96,0xFFFFFF);
            line(c,"Launcher controls need per-launcher testing.",x,112,0xFFFFFF);
            line(c,"Android storage and encoder integration are planned.",x,128,0xFFFFFF);
            line(c,"Test on a copy of your world and share crash logs.",x,144,0xFFFFFF);
        }
    }
    private void about(DrawContext c,int x) {
        line(c,"ABOUT & KEYBINDS",x,58,0x8FE3CF);line(c,"Author: RC Empire | License: MIT",x,80,0xFFFFFF);
        line(c,"F7 — open tutorial (configurable)",x,96,0xFFFFFF);line(c,"F8 — capture request only, not a recorder",x,112,0xFFFFFF);
        line(c,"Open Studio — unbound; assign in Controls",x,128,0xFFFFFF);line(c,"Minecraft 1.20.1 | Fabric | Java 17",x,144,0xFFFFFF);
        line(c,"github.com/imrcraja/CleanCam",x,160,0xA8DADC);
    }
    private void storage(DrawContext c,int x) {
        Path root=FabricLoader.getInstance().getGameDir().resolve("cleancam").resolve("recordings");
        line(c,"STORAGE PATHS (PLANNED)",x,58,0x8FE3CF);line(c,"Raw: "+root.resolve("raw"),x,82,0xFFFFFF);
        line(c,"Exports: "+root.resolve("exports"),x,100,0xFFFFFF);line(c,"This prototype does not write video files.",x,124,0xFFCC80);
        line(c,"Android Movies export needs a storage bridge.",x,140,0xFFFFFF);
    }
    private void line(DrawContext c,String s,int x,int y,int color) { c.drawCenteredTextWithShadow(textRenderer,s,x,y,color); }
    @Override public void close() { if(client!=null) client.setScreen(parent); }
}
