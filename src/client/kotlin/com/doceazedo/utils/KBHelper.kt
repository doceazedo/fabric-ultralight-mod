package com.doceazedo.utils

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import java.util.function.Consumer

/*
* @author Lucasmellof, Lucas de Mello Freitas created on 21/11/2022
*/
object KBHelper {
	/**
	 * Register a keybinding and a consumer to be called when the key is pressed
	 * @param name The translation key of the keybinding's name
	 * @param key The keycode of the key. Use GLFW.GLFW_KEY_*
	 * @param clientConsumer The consumer to be called when the key is pressed
	 */
	fun of(name: String, key: Int, clientConsumer: Consumer<MinecraftClient?>) {
		val kb = KeyBinding(
			"key.slender.$name",  // The translation key of the keybinding's name
			InputUtil.Type.KEYSYM,  // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
			key,  // The keycode of the key
			"category.nmrf.helpers" // The translation key of the keybinding's category.
		)
		KeyBindingHelper.registerKeyBinding(kb)
		ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client: MinecraftClient? ->
			while (kb.wasPressed()) {
				clientConsumer.accept(client)
			}
		})
	}
}
