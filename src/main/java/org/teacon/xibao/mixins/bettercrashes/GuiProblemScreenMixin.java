package org.teacon.xibao.mixins.bettercrashes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vfyjxf.bettercrashes.utils.GuiProblemScreen;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.teacon.xibao.Xibao.LOCATION;

@Mixin(value = GuiScreen.class, priority = 1)
public class GuiProblemScreenMixin {

    @Inject(method = "drawDefaultBackground", at = @At("TAIL"))
    private void drawDefaultBackground(CallbackInfo ci) {
        GuiScreen s = Minecraft.getMinecraft().currentScreen;
        Boolean showXibao = !Files.exists(Paths.get(".xibao_stop"));
        if (showXibao && s instanceof GuiProblemScreen) {
            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            texturemanager.bindTexture(LOCATION);

            //https://github.com/GTNewHorizons/Custom-Main-Menu/blob/cc934c1d96691190d7479927735d58dac4c263a9/src/main/java/lumien/custommainmenu/util/RenderUtil.java#L26
            GL11.glPushMatrix();

            GL11.glTranslatef(0, 0, 0);
            GL11.glBegin(GL11.GL_QUADS);

            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(0, 0, 0);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex3f(0, s.height, 0);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(s.width, s.height, 0);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex3f(s.width, 0, 0);
            GL11.glEnd();

            GL11.glPopMatrix();
        }
    }
}
