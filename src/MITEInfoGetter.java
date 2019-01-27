import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
class MITEInfoGetter {
    static int getWindowWidth() {
        return Display.getWidth();
    }

    static int getWindowHeight() {
        return Display.getHeight();
    }

    static int getWindowPosX() {
        return Display.getX();
    }

    static int getWindowPosY() {
        return Display.getY();
    }

    static boolean isKeyDown(int Key) {
        if (Keyboard.isCreated()) {
            return Keyboard.isKeyDown(Key);
        } else return false;
    }

    static atc getPlayerPos() {
        try {
//            System.out.println(atv.O.H);
//            System.out.println(atv.O.F);
//            System.out.println(atv.O.B);
            return atv.O.h.getFootPos();
        } catch (Exception e) {
            return atc.a(-1d, -1d, -1d);
        }
    }

    static int getScreenWidth() {
        return Display.getDesktopDisplayMode().getWidth();
    }

    static int getScreenHeight() {
        return Display.getDesktopDisplayMode().getHeight();
    }

    private static File getMITESavesFile() {
        return atv.O.saves_dir_MITE;
    }

    static File getWaypointsFile() {
        try {
            return new File(getMITESavesFile().getCanonicalPath().replace("saves\\1.6.4", "") + "waypoints.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return getMITESavesFile();
        }
    }

    static boolean isAnyChatOpen() {
        try {
            return atv.O.isAnyChatOpen();
        } catch (Exception e) {
            return false;
        }
    }
    static void sendMessage(String msg){
        atv.setErrorMessage(msg);
    }
}
