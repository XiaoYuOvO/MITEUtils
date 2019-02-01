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

    static File getMITESettingFile(){
        try {
            return new File(getMITESavesFile().getCanonicalPath().replace("MITE\\saves\\1.6.4","") + "MITEUtilsOptions.txt");
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Failed to get setting file,will use default setting");
            return getMITESavesFile();
        }
    }

    private static File getMITESavesFile() {
        return atv.O.saves_dir_MITE;
    }

    static File getWaypointsFile() {
        try {
            return new File(getMITESavesFile().getCanonicalPath().replace("saves\\1.6.4", "") + "waypoints.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to get waypoints file,will not load saved waypoints");
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
    static void clearMessage(){
        atv.clearErrorMessage();
    }
    static void sendMessage(String msg){
        atv.setErrorMessage(msg,false);
    }
    /**
     * @param msg The message need to send
     * @param color The color of the chars
     *              It should be a value of
     * @see a
     * The deobfuscation of the color enumeration of class a is
     * {@code a.a} is black  R:0 G:0 B:0
     * {@code a.b} is dark_blue  R:0 G:0 B:170
     * {@code a.c} is dark_green  R:0 G:170 B:0
     * {@code a.e} is dark_red	R:170 G:0 B:0
     * {@code a.f} is dark_purple  R:170 G:0 B:70
     * {@code a.g} is gold	R:255 G:170	B:0
     * {@code a.h} is gray	R:170 G:170	B:170
     * {@code a.i} is dark_gray	R:85 G:85 B:85
     * {@code a.j} is blue  R:85 G:85 B:255
     * {@code a.k} is green	R:85 G:255 B:85
     * {@code a.l} is aqua  R:85 G:255 B:255
     * {@code a.m} is red  R:255 G:85 B:85
     * {@code a.n} is light_purple  R:255 G:85 B:255
     * {@code a.o} is yellow  R:255 G:255 B:85
     * {@code a.p} is white  R:255 G:255 B:255
     *
     * */
//    static void sendMessage(String msg,a color){
//        atv.O.h.receiveChatMessage(msg,color);
//    }
}
