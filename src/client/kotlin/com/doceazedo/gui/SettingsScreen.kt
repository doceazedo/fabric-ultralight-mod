package com.doceazedo.gui

import com.doceazedo.SlenderClient
import com.doceazedo.ultralight.web.WebController
import com.doceazedo.ultralight.web.WebWindow
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 23/08/2023
 */
class SettingsScreen : Screen(Text.literal("Settings")) {
	private lateinit var window: WebWindow
	override fun init() {
		window = WebController.INSTANCE.createWindow(
			{ SlenderClient.mc.window.framebufferWidth }
		) { SlenderClient.mc.window.framebufferHeight }
		window.view.loadURL("https://google.com")
		val scale = this.client!!.window.scaleFactor.toFloat()
		window.windowContentScaleCallback(scale, scale)
	}

	override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
		WebController.INSTANCE.update()
		WebController.INSTANCE.render(context)
	}

	override fun shouldPause(): Boolean {
		return false
	}

	override fun close() {
		super.close()
		WebController.INSTANCE.closeWindow(window)
	}

	override fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean {
//		window.onScroll(amount)
		return super.mouseScrolled(mouseX, mouseY, amount)
	}

	override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
		window.onMouseButton(button, GLFW.GLFW_PRESS, 0)
		return true
	}

	override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
		window.onMouseButton(button, GLFW.GLFW_RELEASE, 0)
		return true
	}

	override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
		window.onKey(keyCode, scanCode, GLFW.GLFW_PRESS, modifiers)
		return true
	}

	override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
		if (keyCode == 256 && this.shouldCloseOnEsc()) {
			this.close();
			return true;
		}
		window.onKey(keyCode, scanCode, GLFW.GLFW_RELEASE, modifiers)
		return true
	}

	override fun mouseMoved(mouseX: Double, mouseY: Double) {
		window.onCursorPos(mouseX, mouseY)
		super.mouseMoved(mouseX, mouseY)
	}
}
