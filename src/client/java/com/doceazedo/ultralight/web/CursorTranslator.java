package com.doceazedo.ultralight.web;

import net.janrupf.ujr.api.cursor.UlCursor;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to translate Ultralight cursors to GLFW cursors.
 */
public class CursorTranslator {

    private static final Map<Integer, Long> standardCursors;

    static {
        standardCursors = new HashMap<>();
        int[] cursors = {
                GLFW.GLFW_ARROW_CURSOR,
                GLFW.GLFW_IBEAM_CURSOR,
                GLFW.GLFW_CROSSHAIR_CURSOR,
                GLFW.GLFW_POINTING_HAND_CURSOR,
                GLFW.GLFW_RESIZE_EW_CURSOR,
                GLFW.GLFW_RESIZE_NS_CURSOR,
                GLFW.GLFW_RESIZE_NWSE_CURSOR,
                GLFW.GLFW_RESIZE_NESW_CURSOR,
                GLFW.GLFW_RESIZE_ALL_CURSOR,
                GLFW.GLFW_NOT_ALLOWED_CURSOR,
        };

        for (int cursor : cursors) {
            long stdC = GLFW.glfwCreateStandardCursor(cursor);
            standardCursors.put(cursor, stdC);
        }
    }

    /**
     * Translates an Ultralight cursor to a GLFW cursor.
     *
     * @param cursor the Ultralight cursor
     * @return the GLFW standard cursor
     */
    public static int ultralightToGlfwCursor(UlCursor cursor) {
	    return switch (cursor) {
		    case POINTER -> GLFW.GLFW_ARROW_CURSOR;
		    case CROSS -> GLFW.GLFW_CROSSHAIR_CURSOR;
		    case HAND -> GLFW.GLFW_POINTING_HAND_CURSOR;
		    case I_BEAM -> GLFW.GLFW_IBEAM_CURSOR;
		    case NORTH_SOUTH_RESIZE -> GLFW.GLFW_RESIZE_NS_CURSOR;
		    case EAST_WEST_RESIZE -> GLFW.GLFW_RESIZE_EW_CURSOR;
		    default -> -1;
	    };
    }

    public static void changeCursor(int glfwCursorId) {
        if (glfwCursorId == -1) {
            GLFW.glfwSetCursor(MinecraftClient.getInstance().getWindow().getHandle(), 0);
        } else {
            GLFW.glfwSetCursor(MinecraftClient.getInstance().getWindow().getHandle(), getStandardCursor(glfwCursorId));
        }
    }

    private static long getStandardCursor(int cursor) {
        return standardCursors.get(cursor);
    }
}
