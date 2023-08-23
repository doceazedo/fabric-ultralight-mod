package com.doceazedo.ultralight.web;

import com.doceazedo.Slender;
import com.doceazedo.SlenderClient;
import com.doceazedo.shaders.MinecraftShaders;
import com.mojang.blaze3d.systems.RenderSystem;
import net.janrupf.ujr.api.*;
import net.janrupf.ujr.api.event.UlKeyEvent;
import net.janrupf.ujr.api.event.UlKeyEventType;
import net.janrupf.ujr.api.event.UlMouseButton;
import net.janrupf.ujr.api.event.UlScrollEventType;
import com.doceazedo.ultralight.surface.GlfwSurface;
import com.doceazedo.ultralight.web.listener.WebViewListener;
import com.doceazedo.ultralight.web.listener.WebViewLoadListener;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

public class WebWindow {
    public final UltralightView view;

    //private WebDrawParameters drawParameters;
    private final Supplier<Integer> width;
    private final Supplier<Integer> height;
    private double lastMouseX;
    private double lastMouseY;
    private int lastWidth = -1;//use this instead of view.width, because this is technically faster
    private int lastHeight = -1;

    public WebWindow(Supplier<Integer> width, Supplier<Integer> height) {
        this.lastWidth = width.get();
        this.lastHeight = height.get();
        this.view = UltralightRenderer.getOrCreate().createView(this.lastWidth, this.lastHeight, new UltralightViewConfigBuilder().transparent(true).build());
        this.width = width;
        this.height = height;

        // Make sure we receive all events
        this.view.setViewListener(new WebViewListener(this));
        this.view.setLoadListener(new WebViewLoadListener());
    }

    public void resizeIfNeeded() {
        int width = this.width.get();
        int height = this.height.get();
        if (this.lastWidth != width || this.lastHeight != height) {
            this.lastWidth = width;
            this.lastHeight = height;
            this.view.resize(this.lastWidth, this.lastHeight);
        }
    }

    public void renderToFramebuffer(DrawContext drawContext, int viewportWidth, int viewportHeight) {
        GlfwSurface surface = (GlfwSurface) view.surface();

        float u1 = 0;
        float v1 = 0;
        float u2 = 1;
        float v2 = 1;

        float x1 = 0;
        float y1 = 0;
        float x2 = viewportWidth;
        float y2 = viewportHeight;
        float z = 0;

        RenderSystem.setShaderTexture(0, surface.getTexture());
        RenderSystem.setShader(() -> MinecraftShaders.bgraPositionTextureShader);
        Matrix4f matrix4f = drawContext.getMatrices().peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix4f, x1, y1, z).texture(u1, v1).next();
        bufferBuilder.vertex(matrix4f, x1, y2, z).texture(u1, v2).next();
        bufferBuilder.vertex(matrix4f, x2, y2, z).texture(u2, v2).next();
        bufferBuilder.vertex(matrix4f, x2, y1, z).texture(u2, v1).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

    public void onCursorPos(double x, double y) {
        this.lastMouseX = x;
        this.lastMouseY = y;

        // We need mouse buttons here in order for drag events to properly work
        UlMouseButton button = UlMouseButton.NONE;
        if (this.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            button = UlMouseButton.LEFT;
        } else if (this.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            button = UlMouseButton.RIGHT;
        } else if (this.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_MIDDLE)) {
            button = UlMouseButton.MIDDLE;
        }

        this.view.fireMouseEvent(UltralightMouseEventBuilder.moved().x((int) x).y((int) y).button(button).build());
    }


    public void onMouseButton(int button, int action, int mods) {
        UlMouseButton ulButton;

        switch (button) {
            case GLFW.GLFW_MOUSE_BUTTON_LEFT:
                ulButton = UlMouseButton.LEFT;
                break;

            case GLFW.GLFW_MOUSE_BUTTON_RIGHT:
                ulButton = UlMouseButton.RIGHT;
                break;

            case GLFW.GLFW_MOUSE_BUTTON_MIDDLE:
                ulButton = UlMouseButton.MIDDLE;
                break;

            default:
                return;
        }

        UltralightMouseEventBuilder builder;

        if (action == GLFW.GLFW_PRESS) {
            builder = UltralightMouseEventBuilder.down(ulButton);
        } else if (action == GLFW.GLFW_RELEASE) {
            builder = UltralightMouseEventBuilder.up(ulButton);
        } else {
            return;
        }

        this.view.fireMouseEvent(builder.x((int) this.lastMouseX).y((int) this.lastMouseY).build());
    }

    public void onScroll(double x, double y) {
        // The 50 down below is an arbitrary value that seems to work well,
        // feel free to adjust this value as "scroll sensitivity"

        this.view.fireScrollEvent(new UltralightScrollEventBuilder(UlScrollEventType.BY_PIXEL).deltaY((int) (x * 50)).deltaY((int) (y * 50)).build());
    }

    public void onCharMods(int codepoint, int mods) {
        String text = new String(Character.toChars(codepoint));

        this.view.fireKeyEvent(UltralightKeyEventBuilder.character().unmodifiedText(text).text(text).build());
    }

    public void onKey(int key, int scancode, int action, int mods) {
        UltralightKeyEventBuilder builder;

        if (action == GLFW.GLFW_PRESS) {
            builder = UltralightKeyEventBuilder.rawDown();
        } else if (action == GLFW.GLFW_RELEASE) {
            builder = UltralightKeyEventBuilder.up();
        } else {
            return;
        }

        builder.nativeKeyCode(scancode).virtualKeyCode(KeyboardTranslator.glfwKeyToUltralight(key)).keyIdentifier(UlKeyEvent.keyIdentifierFromVirtualKeyCode(builder.virtualKeyCode)).modifiers(KeyboardTranslator.glfwModifiersToUltralight(mods));

        this.view.fireKeyEvent(builder.build());

        // Manually synthesize enter and tab
        if (builder.type == UlKeyEventType.RAW_DOWN && (key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_TAB)) {
            this.view.fireKeyEvent(UltralightKeyEventBuilder.character().unmodifiedText(key == GLFW.GLFW_KEY_ENTER ? "\n" : "\t").text(key == GLFW.GLFW_KEY_ENTER ? "\n" : "\t"));
        }
    }

    private boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(SlenderClient.INSTANCE.getMc().getWindow().getHandle(), button) == GLFW.GLFW_PRESS;
    }
}
