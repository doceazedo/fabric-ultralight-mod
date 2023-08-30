package com.doceazedo

import com.doceazedo.gui.SettingsScreen
import com.doceazedo.shaders.MinecraftShaders
import com.doceazedo.ultralight.web.WebController
import com.doceazedo.utils.KBHelper
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback
import net.minecraft.client.MinecraftClient

object SlenderClient : ClientModInitializer {
	val mc: MinecraftClient = MinecraftClient.getInstance()

	val openSettings = KBHelper.of("key.slenderclient.openSettings", 66) {
		mc.setScreen(SettingsScreen())
	}

	override fun onInitializeClient() {
		WebController.INSTANCE.load()
		CoreShaderRegistrationCallback.EVENT.register(MinecraftShaders)
//		HudRenderCallback.EVENT.register(HudOverlay)
	}
}
