package com.doceazedo.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 23/08/2023
 */
@Mixin(Window.class)
public class MixinWindow {
	@Shadow private int framebufferWidth;

	@Shadow private int framebufferHeight;

	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwDefaultWindowHints()V"), remap = false)
	public void fixRetina() {
		GLFW.glfwDefaultWindowHints();

		if (MinecraftClient.IS_SYSTEM_MAC) {
			GLFW.glfwWindowHint(GLFW.GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW.GLFW_FALSE);
		}
	}

	@Inject(at = @At(value = "RETURN"), method = "updateFramebufferSize")
	private void afterUpdateFrameBufferSize(CallbackInfo ci) {
		if (MinecraftClient.IS_SYSTEM_MAC) {
			framebufferWidth /= 2;
			framebufferHeight /= 2;
		}
	}
}
