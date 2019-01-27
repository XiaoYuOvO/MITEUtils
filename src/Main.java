import javax.swing.*;

class Main {
   static PosGetter posGetterInstance = new PosGetter();
   static QuickChineseInput quickChineseInputInstance = new QuickChineseInput();
   static KeySetting keySettingInstance = new KeySetting();
    static void main() {
        Runtime.getRuntime().addShutdownHook(new Thread(()-> posGetterInstance.saveWaypointsListToFile(MITEInfoGetter.getWaypointsFile())));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException var12) {
            var12.printStackTrace();
        }
        posGetterInstance.start();
        quickChineseInputInstance.start();
        MITEInfoGetter.sendMessage("MITEUtils is loaded,press 'G' to open key setting");
        new InfoGetThread().start();
    }
}
