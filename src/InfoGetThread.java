import org.lwjgl.opengl.Display;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

@SuppressWarnings("InfiniteLoopStatement")
public class InfoGetThread extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                atc playerPos = MITEInfoGetter.getPlayerPos();
                Main.posGetterInstance.setPosInfo(playerPos.getBlockX(), playerPos.getBlockY(), playerPos.getBlockZ());
                if (!MITEInfoGetter.isAnyChatOpen()) {
                    if (MITEInfoGetter.isKeyDown(KeySetting.Key_CycleToChineseInput.get())) {
                        Main.quickChineseInputInstance.requestFocus();
                    }
                    if (MITEInfoGetter.isKeyDown(KeySetting.Key_OpenSetting.get())) {
                        Main.keySettingInstance.showKeySettingFrame();
                    }
                    if (MITEInfoGetter.isKeyDown(KeySetting.Key_SaveCurrentPosToWaypoint.get())) {
                        Main.posGetterInstance.showAddWaypointDialog();
                    }
                    if (MITEInfoGetter.isKeyDown(KeySetting.Key_CopyCurrentPosToClipboard.get())) {
                        StringSelection clipBoardContent = new StringSelection("[" + "x:" + playerPos.getBlockX() + " y:" + playerPos.getBlockY() + " z:" + playerPos.getBlockZ() + "]");
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipBoardContent, clipBoardContent);
                    }
                    if (MITEInfoGetter.isKeyDown(KeySetting.Key_SaveWaypointsToFile.get())) {
                        Main.posGetterInstance.saveWaypointsListToFile(MITEInfoGetter.getWaypointsFile());
                    }
                    if (MITEInfoGetter.isKeyDown(KeySetting.Key_ReloadWaypointsFromeFile.get())) {
                        Main.posGetterInstance.getAndReloadWaypointsFromFile(MITEInfoGetter.getWaypointsFile());
                    }
                }
                if (Display.isCreated()) {
                    Main.posGetterInstance.setWaypointsListDialogPos(MITEInfoGetter.getWindowPosX()+10,MITEInfoGetter.getWindowPosY()+(MITEInfoGetter.getWindowHeight()/2));
                    Main.posGetterInstance.setPosDialogBounds(MITEInfoGetter.getWindowPosX() + MITEInfoGetter.getWindowWidth() - 140, MITEInfoGetter.getWindowPosY() + 30);
                    Main.quickChineseInputInstance.setBonuds(MITEInfoGetter.getWindowPosX() + 10, MITEInfoGetter.getWindowPosY() + MITEInfoGetter.getWindowHeight() - 10);
                }
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
