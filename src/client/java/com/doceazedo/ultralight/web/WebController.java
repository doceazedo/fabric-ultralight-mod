package com.doceazedo.ultralight.web;

import com.doceazedo.SlenderClient;
import net.janrupf.ujr.api.UltralightConfigBuilder;
import net.janrupf.ujr.api.UltralightPlatform;
import net.janrupf.ujr.api.UltralightRenderer;
import net.janrupf.ujr.api.javascript.*;
import net.janrupf.ujr.core.UltralightJavaReborn;
import net.janrupf.ujr.core.platform.PlatformEnvironment;
import com.doceazedo.ultralight.CustomJavaScriptClass;
import com.doceazedo.ultralight.bridge.FilesystemBridge;
import com.doceazedo.ultralight.bridge.GlfwClipboardBridge;
import com.doceazedo.ultralight.bridge.LoggerBridge;
import com.doceazedo.ultralight.surface.GlfwSurfaceFactory;
import net.minecraft.client.gui.DrawContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class WebController implements AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger(WebController.class);

    private final Set<WebWindow> windows;
    private final UltralightJavaReborn ujr;

    public WebController() {
        this.windows = new HashSet<WebWindow>();

        // Load the platform and create the Ultralight Java Reborn instance
        PlatformEnvironment environment = PlatformEnvironment.load();
        LOGGER.info("Platform environment has been loaded!");

        this.ujr = new UltralightJavaReborn(environment);
        this.ujr.activate();
        LOGGER.info("Ultralight Java Reborn has been activated!");

        // Activate global bridge instances for Ultralight
        UltralightPlatform platform = UltralightPlatform.instance();
        platform.usePlatformFontLoader();
        platform.setFilesystem(new FilesystemBridge());
        platform.setClipboard(new GlfwClipboardBridge());
        platform.setLogger(new LoggerBridge());

        // Note the usage of the GlfwSurfaceFactory here.
        // This is required to make Ultralight Java Reborn work custom surfaces.
        // Technically, we could also use the default surface factory, but that would
        // require us to deal with bitmaps and then upload them to OpenGLES.
        // Using a custom surface factory allows us to directly use pixel buffer objects.
        platform.setSurfaceFactory(new GlfwSurfaceFactory());

        //TODO: change
        platform.setConfig(new UltralightConfigBuilder().cachePath(System.getProperty("java.io.tmpdir") + File.separator + "ujr-example-glfw").resourcePathPrefix(FilesystemBridge.RESOURCE_PREFIX).build());

        JSGlobalContext context = new JSGlobalContext((JSClass) null);
        context.setName("TestContext");

        try {
            // Create the custom object and make it available on the global object
            JSObject customObject = context.makeObject(CustomJavaScriptClass.getJavaScriptClass());
            context.getGlobalObject().setProperty("customObject", customObject);

            context.evaluateScript("customObject.test = 42;\n" + "\n" + "new customObject();\n" + "customObject();\n" + "\n" + "delete customObject.test;\n" + "delete customObject;\n", null, null, 1);
        } catch (JavaScriptValueException e) {
            LOGGER.error("Failed to run demo JavaScript code", e);
        }
    }

    public void terminate() {
        LOGGER.debug("Cleaning up Ultralight Java Reborn...");
        ujr.cleanup();
    }

    public WebWindow createWindow(Supplier<Integer> width, Supplier<Integer> height) {
        WebWindow window = new WebWindow(width, height);
        this.windows.add(window);

        return window;
    }

    public void update() {
        for (WebWindow window : this.windows) {
            window.resizeIfNeeded();
        }
        UltralightRenderer.getOrCreate().update();
    }

    public void render(DrawContext drawContext) {
        UltralightRenderer.getOrCreate().render();

        for (WebWindow window : this.windows) {
            window.renderToFramebuffer(drawContext, SlenderClient.INSTANCE.getMc().getWindow().getScaledWidth(), SlenderClient.INSTANCE.getMc().getWindow().getScaledHeight());
        }
    }

    @Override
    public void close() {
        terminate();
    }
}
