import org.lwjgl.input.Keyboard;

import javax.swing.*;

class Main {
   static PosGetter posGetterInstance = new PosGetter();
   static QuickChineseInput quickChineseInputInstance = new QuickChineseInput();
   static WaypointUtil waypointUtilInstance = new WaypointUtil();
   static Setting keySettingInstance = new Setting();
    static void main() {
        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            waypointUtilInstance.saveWaypointsListToFile(MITEInfoGetter.getWaypointsFile());
            keySettingInstance.saveSettingToFile();
        }));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException var12) {
            var12.printStackTrace();
        }
        posGetterInstance.start();
        quickChineseInputInstance.start();
        MITEInfoGetter.clearMessage();
        MITEInfoGetter.sendMessage("§6MITEUtils模组已安装，按下 '" + Keyboard.getKeyName(Setting.Key_OpenSetting.get()) +" '来打开按键设置");
        new InfoGetThread().start();
    }
}
