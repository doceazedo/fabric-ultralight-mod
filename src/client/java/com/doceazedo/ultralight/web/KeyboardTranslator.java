package com.doceazedo.ultralight.web;

import net.janrupf.ujr.api.event.UlKeyCode;
import net.janrupf.ujr.api.event.UlKeyEventModifiers;
import org.lwjgl.glfw.GLFW;

import java.util.EnumSet;

/**
 * Helper to translate GLFW keyboard events to Ultralight keyboard events.
 */
public class KeyboardTranslator {
    /**
     * Translates GLFW key modifiers to Ultralight key modifiers.
     *
     * @param mods the GLFW key modifiers bitfield
     * @return the Ultralight key modifiers
     */
    public static EnumSet<UlKeyEventModifiers> glfwModifiersToUltralight(int mods) {
        EnumSet<UlKeyEventModifiers> result = EnumSet.noneOf(UlKeyEventModifiers.class);

        if ((mods & GLFW.GLFW_MOD_SHIFT) != 0) {
            result.add(UlKeyEventModifiers.SHIFT);
        }

        if ((mods & GLFW.GLFW_MOD_CONTROL) != 0) {
            result.add(UlKeyEventModifiers.CTRL);
        }

        if ((mods & GLFW.GLFW_MOD_ALT) != 0) {
            result.add(UlKeyEventModifiers.ALT);
        }

        if ((mods & GLFW.GLFW_MOD_SUPER) != 0) {
            result.add(UlKeyEventModifiers.META);
        }

        return result;
    }

    /**
     * Translates GLFW key codes to Ultralight key codes.
     *
     * @param glfwKeyCode the GLFW key code
     * @return the Ultralight key code, or {@link UlKeyCode#UNKNOWN} if the key code is not supported
     */
    public static int glfwKeyToUltralight(int glfwKeyCode) {
	    return switch (glfwKeyCode) {
		    case GLFW.GLFW_KEY_SPACE -> UlKeyCode.SPACE;
		    case GLFW.GLFW_KEY_APOSTROPHE -> UlKeyCode.OEM_7;
		    case GLFW.GLFW_KEY_COMMA -> UlKeyCode.OEM_COMMA;
		    case GLFW.GLFW_KEY_MINUS -> UlKeyCode.OEM_MINUS;
		    case GLFW.GLFW_KEY_PERIOD -> UlKeyCode.OEM_PERIOD;
		    case GLFW.GLFW_KEY_SLASH -> UlKeyCode.OEM_2;
		    case GLFW.GLFW_KEY_0 -> UlKeyCode.NUMBER_0;
		    case GLFW.GLFW_KEY_1 -> UlKeyCode.NUMBER_1;
		    case GLFW.GLFW_KEY_2 -> UlKeyCode.NUMBER_2;
		    case GLFW.GLFW_KEY_3 -> UlKeyCode.NUMBER_3;
		    case GLFW.GLFW_KEY_4 -> UlKeyCode.NUMBER_4;
		    case GLFW.GLFW_KEY_5 -> UlKeyCode.NUMBER_5;
		    case GLFW.GLFW_KEY_6 -> UlKeyCode.NUMBER_6;
		    case GLFW.GLFW_KEY_7 -> UlKeyCode.NUMBER_7;
		    case GLFW.GLFW_KEY_8 -> UlKeyCode.NUMBER_8;
		    case GLFW.GLFW_KEY_9 -> UlKeyCode.NUMBER_9;
		    case GLFW.GLFW_KEY_SEMICOLON -> UlKeyCode.OEM_1;
		    case GLFW.GLFW_KEY_EQUAL, GLFW.GLFW_KEY_KP_EQUAL -> UlKeyCode.OEM_PLUS;
		    case GLFW.GLFW_KEY_A -> UlKeyCode.A;
		    case GLFW.GLFW_KEY_B -> UlKeyCode.B;
		    case GLFW.GLFW_KEY_C -> UlKeyCode.C;
		    case GLFW.GLFW_KEY_D -> UlKeyCode.D;
		    case GLFW.GLFW_KEY_E -> UlKeyCode.E;
		    case GLFW.GLFW_KEY_F -> UlKeyCode.F;
		    case GLFW.GLFW_KEY_G -> UlKeyCode.G;
		    case GLFW.GLFW_KEY_H -> UlKeyCode.H;
		    case GLFW.GLFW_KEY_I -> UlKeyCode.I;
		    case GLFW.GLFW_KEY_J -> UlKeyCode.J;
		    case GLFW.GLFW_KEY_K -> UlKeyCode.K;
		    case GLFW.GLFW_KEY_L -> UlKeyCode.L;
		    case GLFW.GLFW_KEY_M -> UlKeyCode.M;
		    case GLFW.GLFW_KEY_N -> UlKeyCode.N;
		    case GLFW.GLFW_KEY_O -> UlKeyCode.O;
		    case GLFW.GLFW_KEY_P -> UlKeyCode.P;
		    case GLFW.GLFW_KEY_Q -> UlKeyCode.Q;
		    case GLFW.GLFW_KEY_R -> UlKeyCode.R;
		    case GLFW.GLFW_KEY_S -> UlKeyCode.S;
		    case GLFW.GLFW_KEY_T -> UlKeyCode.T;
		    case GLFW.GLFW_KEY_U -> UlKeyCode.U;
		    case GLFW.GLFW_KEY_V -> UlKeyCode.V;
		    case GLFW.GLFW_KEY_W -> UlKeyCode.W;
		    case GLFW.GLFW_KEY_X -> UlKeyCode.X;
		    case GLFW.GLFW_KEY_Y -> UlKeyCode.Y;
		    case GLFW.GLFW_KEY_Z -> UlKeyCode.Z;
		    case GLFW.GLFW_KEY_LEFT_BRACKET -> UlKeyCode.OEM_4;
		    case GLFW.GLFW_KEY_BACKSLASH -> UlKeyCode.OEM_5;
		    case GLFW.GLFW_KEY_RIGHT_BRACKET -> UlKeyCode.OEM_6;
		    case GLFW.GLFW_KEY_GRAVE_ACCENT -> UlKeyCode.OEM_3;
		    case GLFW.GLFW_KEY_ESCAPE -> UlKeyCode.ESCAPE;
		    case GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER -> UlKeyCode.RETURN;
		    case GLFW.GLFW_KEY_TAB -> UlKeyCode.TAB;
		    case GLFW.GLFW_KEY_BACKSPACE -> UlKeyCode.BACK;
		    case GLFW.GLFW_KEY_INSERT -> UlKeyCode.INSERT;
		    case GLFW.GLFW_KEY_DELETE -> UlKeyCode.DELETE;
		    case GLFW.GLFW_KEY_RIGHT -> UlKeyCode.RIGHT;
		    case GLFW.GLFW_KEY_LEFT -> UlKeyCode.LEFT;
		    case GLFW.GLFW_KEY_DOWN -> UlKeyCode.DOWN;
		    case GLFW.GLFW_KEY_UP -> UlKeyCode.UP;
		    case GLFW.GLFW_KEY_PAGE_UP -> UlKeyCode.PRIOR;
		    case GLFW.GLFW_KEY_PAGE_DOWN -> UlKeyCode.NEXT;
		    case GLFW.GLFW_KEY_HOME -> UlKeyCode.HOME;
		    case GLFW.GLFW_KEY_END -> UlKeyCode.END;
		    case GLFW.GLFW_KEY_CAPS_LOCK -> UlKeyCode.CAPITAL;
		    case GLFW.GLFW_KEY_SCROLL_LOCK -> UlKeyCode.SCROLL;
		    case GLFW.GLFW_KEY_NUM_LOCK -> UlKeyCode.NUMLOCK;
		    case GLFW.GLFW_KEY_PRINT_SCREEN -> UlKeyCode.SNAPSHOT;
		    case GLFW.GLFW_KEY_PAUSE -> UlKeyCode.PAUSE;
		    case GLFW.GLFW_KEY_F1 -> UlKeyCode.F1;
		    case GLFW.GLFW_KEY_F2 -> UlKeyCode.F2;
		    case GLFW.GLFW_KEY_F3 -> UlKeyCode.F3;
		    case GLFW.GLFW_KEY_F4 -> UlKeyCode.F4;
		    case GLFW.GLFW_KEY_F5 -> UlKeyCode.F5;
		    case GLFW.GLFW_KEY_F6 -> UlKeyCode.F6;
		    case GLFW.GLFW_KEY_F7 -> UlKeyCode.F7;
		    case GLFW.GLFW_KEY_F8 -> UlKeyCode.F8;
		    case GLFW.GLFW_KEY_F9 -> UlKeyCode.F9;
		    case GLFW.GLFW_KEY_F10 -> UlKeyCode.F10;
		    case GLFW.GLFW_KEY_F11 -> UlKeyCode.F11;
		    case GLFW.GLFW_KEY_F12 -> UlKeyCode.F12;
		    case GLFW.GLFW_KEY_F13 -> UlKeyCode.F13;
		    case GLFW.GLFW_KEY_F14 -> UlKeyCode.F14;
		    case GLFW.GLFW_KEY_F15 -> UlKeyCode.F15;
		    case GLFW.GLFW_KEY_F16 -> UlKeyCode.F16;
		    case GLFW.GLFW_KEY_F17 -> UlKeyCode.F17;
		    case GLFW.GLFW_KEY_F18 -> UlKeyCode.F18;
		    case GLFW.GLFW_KEY_F19 -> UlKeyCode.F19;
		    case GLFW.GLFW_KEY_F20 -> UlKeyCode.F20;
		    case GLFW.GLFW_KEY_F21 -> UlKeyCode.F21;
		    case GLFW.GLFW_KEY_F22 -> UlKeyCode.F22;
		    case GLFW.GLFW_KEY_F23 -> UlKeyCode.F23;
		    case GLFW.GLFW_KEY_F24 -> UlKeyCode.F24;
		    case GLFW.GLFW_KEY_KP_0 -> UlKeyCode.NUMPAD0;
		    case GLFW.GLFW_KEY_KP_1 -> UlKeyCode.NUMPAD1;
		    case GLFW.GLFW_KEY_KP_2 -> UlKeyCode.NUMPAD2;
		    case GLFW.GLFW_KEY_KP_3 -> UlKeyCode.NUMPAD3;
		    case GLFW.GLFW_KEY_KP_4 -> UlKeyCode.NUMPAD4;
		    case GLFW.GLFW_KEY_KP_5 -> UlKeyCode.NUMPAD5;
		    case GLFW.GLFW_KEY_KP_6 -> UlKeyCode.NUMPAD6;
		    case GLFW.GLFW_KEY_KP_7 -> UlKeyCode.NUMPAD7;
		    case GLFW.GLFW_KEY_KP_8 -> UlKeyCode.NUMPAD8;
		    case GLFW.GLFW_KEY_KP_9 -> UlKeyCode.NUMPAD9;
		    case GLFW.GLFW_KEY_KP_DECIMAL -> UlKeyCode.DECIMAL;
		    case GLFW.GLFW_KEY_KP_DIVIDE -> UlKeyCode.DIVIDE;
		    case GLFW.GLFW_KEY_KP_MULTIPLY -> UlKeyCode.MULTIPLY;
		    case GLFW.GLFW_KEY_KP_SUBTRACT -> UlKeyCode.SUBTRACT;
		    case GLFW.GLFW_KEY_KP_ADD -> UlKeyCode.ADD;
		    case GLFW.GLFW_KEY_LEFT_SHIFT, GLFW.GLFW_KEY_RIGHT_SHIFT -> UlKeyCode.SHIFT;
		    case GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_RIGHT_CONTROL -> UlKeyCode.CONTROL;
		    case GLFW.GLFW_KEY_LEFT_ALT, GLFW.GLFW_KEY_RIGHT_ALT -> UlKeyCode.MENU;
		    case GLFW.GLFW_KEY_LEFT_SUPER -> UlKeyCode.LWIN;
		    case GLFW.GLFW_KEY_RIGHT_SUPER -> UlKeyCode.RWIN;
		    default -> UlKeyCode.UNKNOWN;
	    };
    }
}
