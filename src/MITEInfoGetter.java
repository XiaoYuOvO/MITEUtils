import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

@SuppressWarnings("unused")
public class MITEInfoGetter {
    static int getWindowWidth() {
        return Display.getWidth();
    }

    public static int getWindowHeight() {
        return Display.getHeight();
    }

    static int getWindowPosX() {
        return Display.getX();
    }

    static int getWindowPosY() {
        return Display.getY();
    }

    public static boolean isKeyDown(int Key) {
        if (Keyboard.isCreated()) {
            return Keyboard.isKeyDown(Key);
        } else return false;
    }

    static atc getPlayerPos() {
        try {
            return atv.O.h.getFootPos();
        } catch (Exception e) {
            return atc.a(0d, 0d, 0d);
        }
    }
}
