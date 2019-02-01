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
                if (Display.isCreated()) {
                    if (Display.isActive()) {
                        if (!MITEInfoGetter.isAnyChatOpen()) {
                            if (MITEInfoGetter.isKeyDown(Setting.Key_CycleToChineseInput.get())) {
                                Main.quickChineseInputInstance.requestFocus();
                            }
                            if (MITEInfoGetter.isKeyDown(Setting.Key_OpenSetting.get())) {
                                Main.keySettingInstance.showKeySettingFrame();
                            }
                            if (MITEInfoGetter.isKeyDown(Setting.Key_SaveCurrentPosToWaypoint.get())) {
                                Main.waypointUtilInstance.showAddWaypointDialog();
                            }
                            if (MITEInfoGetter.isKeyDown(Setting.Key_CopyCurrentPosToClipboard.get())) {
                                StringSelection clipBoardContent = new StringSelection("[" + "x:" + playerPos.getBlockX() + " y:" + playerPos.getBlockY() + " z:" + playerPos.getBlockZ() + "]");
                                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipBoardContent, clipBoardContent);
                                MITEInfoGetter.clearMessage();
                                MITEInfoGetter.sendMessage("§f已将当前坐标复制到剪贴板");
                            }
                            if (MITEInfoGetter.isKeyDown(Setting.Key_SaveWaypointsToFile.get())) {
                                Main.waypointUtilInstance.saveWaypointsListToFile(MITEInfoGetter.getWaypointsFile());
                            }
                            if (MITEInfoGetter.isKeyDown(Setting.Key_ReloadWaypointsFromFile.get())) {
                                Main.waypointUtilInstance.getAndReloadWaypointsFromFile(MITEInfoGetter.getWaypointsFile());
                            }
                            if (MITEInfoGetter.isKeyDown(Setting.Key_OpenRemoveWaypointDialog.get())) {
                                Main.waypointUtilInstance.showRemoveWaypointDialog();
                            }
                        }
                    }
                    Main.waypointUtilInstance.setWaypointsListDialogPos(MITEInfoGetter.getWindowPosX() + 10, MITEInfoGetter.getWindowPosY() + (MITEInfoGetter.getWindowHeight() / 2));
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
