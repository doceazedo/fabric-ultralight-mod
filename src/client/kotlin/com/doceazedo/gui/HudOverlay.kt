package com.doceazedo.gui

import com.doceazedo.SlenderClient.mc
import com.doceazedo.ultralight.web.WebController
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.gui.DrawContext

object HudOverlay : HudRenderCallback {
    var webController: WebController? = null
    private var hasSetup = false

    override fun onHudRender(drawContext: DrawContext?, tickDelta: Float) {
        render(drawContext)
    }

    private fun render(context: DrawContext?) {
        if (!hasSetup) {
            webController = WebController()
            val window = webController!!.createWindow(
                { mc.window.framebufferWidth }
            ) { mc.window.framebufferHeight }
            window.view.loadURL("https://svelte.dev")
            hasSetup = true
        }
        if (webController != null) {
            webController!!.update()
            webController!!.render(context)
        }
    }
}