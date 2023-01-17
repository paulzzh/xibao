package org.teacon.xibao.mixins.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.teacon.xibao.Xibao.LOCATION;

@Mixin(value = GuiDisconnected.class)
public class GuiDisconnectedMixin extends GuiScreen {
    @Override
    public void drawDefaultBackground() {
        Boolean showXibao = !Files.exists(Paths.get(".xibao_stop"));
        if (showXibao) {
            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            texturemanager.bindTexture(LOCATION);

            //https://github.com/GTNewHorizons/Custom-Main-Menu/blob/cc934c1d96691190d7479927735d58dac4c263a9/src/main/java/lumien/custommainmenu/util/RenderUtil.java#L26
            GL11.glPushMatrix();

            GL11.glTranslatef(0, 0, 0);
            GL11.glBegin(GL11.GL_QUADS);

            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(0, 0, 0);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex3f(0, this.height, 0);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(this.width, this.height, 0);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex3f(this.width, 0, 0);
            GL11.glEnd();

            GL11.glPopMatrix();
        }
    }
}
