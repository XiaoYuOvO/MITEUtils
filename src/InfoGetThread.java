import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.awt.event.KeyEvent;

@SuppressWarnings("InfiniteLoopStatement")
public class InfoGetThread extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                atc playerPos = MITEInfoGetter.getPlayerPos();
                Main.posGetterInstance.setPosInfo(playerPos.getBlockX(), playerPos.getBlockY(), playerPos.getBlockZ());
                if (MITEInfoGetter.isKeyDown(Keyboard.KEY_T)) {
                    Main.quickChineseInputInstance.requestFocus();
                }
                if (Display.isCreated()) {
                    Main.posGetterInstance.setBounds(MITEInfoGetter.getWindowPosX() + MITEInfoGetter.getWindowWidth() - 140, MITEInfoGetter.getWindowPosY() + 30);
                    Main.quickChineseInputInstance.setBonuds(MITEInfoGetter.getWindowPosX()+10,MITEInfoGetter.getWindowPosY() + MITEInfoGetter.getWindowHeight()-10);
                }
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
