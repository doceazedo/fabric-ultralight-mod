package com.doceazedo.shaders

import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback
import net.minecraft.client.gl.ShaderProgram
import net.minecraft.client.render.VertexFormats
import net.minecraft.util.Identifier


object MinecraftShaders : CoreShaderRegistrationCallback {
    lateinit var bgraPositionTextureShader: ShaderProgram

    override fun registerShaders(context: CoreShaderRegistrationCallback.RegistrationContext) {
        val id = Identifier("slender", "bgra_position_tex")
        context.register(id, VertexFormats.POSITION_TEXTURE) { program ->
            bgraPositionTextureShader = program
        }
    }
}