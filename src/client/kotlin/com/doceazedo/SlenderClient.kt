package com.doceazedo

import com.doceazedo.gui.HudOverlay
import com.doceazedo.shaders.MinecraftShaders
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient

object SlenderClient : ClientModInitializer {
	val mc: MinecraftClient = MinecraftClient.getInstance()

	override fun onInitializeClient() {
		CoreShaderRegistrationCallback.EVENT.register(MinecraftShaders)
		HudRenderCallback.EVENT.register(HudOverlay)
	}
}