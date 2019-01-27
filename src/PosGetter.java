import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.awt.Font.BOLD;

class PosGetter {

    static ArrayList<Waypoint> waypointsList = new ArrayList<>();
    //    // 全局的位置变量，用于表示鼠标在窗口上的位置
//    static Point origin = new Point();
    private static JDialog frame;
    private static JLabel xLabel = new JLabel("X坐标");
    private static JLabel yLabel = new JLabel("Y坐标");
    private static JLabel zLabel = new JLabel("Z坐标");
    private static Font bigFont = new Font("Default", BOLD, 23);
    private ArrayList<JLabel> waypointLabels = new ArrayList<>();
    private JDialog addWaypointDialog = new JDialog();
    private JDialog waypointsListDialog = new JDialog();
    private Font font = new Font("Default", Font.BOLD, 14);
    private int waypointsCount = 0;
    private JTextField nameField = new JTextField();


    /**
     * Create the application.
     */
    PosGetter() {
        initialize();
        initAddWaypointDialog();
        initWaypointListDialog();
    }
    void saveWaypointsListToFile(File targetFile) {
        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            int i = 0;
            for (Waypoint waypoint : waypointsList) {
                String outputString = "name:" + waypoint.name + ";x:" + waypoint.pos.getBlockX() + ";y:" + waypoint.pos.getBlockY() + ";z:" + waypoint.pos.getBlockZ();
                for (char c : outputString.toCharArray()) {
                    outputStream.write(c);
                }
                outputStream.write('\n');
                i++;
            }
            System.out.println("Saved " + i + " waypoint(s) to file: " + targetFile.getCanonicalPath());
            MITEInfoGetter.sendMessage("已保存" + i + "个路径点");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initWaypointListDialog() {
        waypointsListDialog.setAlwaysOnTop(true);
        waypointsListDialog.setUndecorated(true);
        waypointsListDialog.setFocusable(false);
        waypointsListDialog.setFocusableWindowState(false);
        waypointsListDialog.setBounds(MITEInfoGetter.getScreenWidth() / 2, MITEInfoGetter.getScreenHeight() / 2, 200, 200);
        waypointsListDialog.setBackground(new Color(0, 0, 0, 0));
        waypointsListDialog.setLayout(null);
        getAndReloadWaypointsFromFile(MITEInfoGetter.getWaypointsFile());
        waypointsListDialog.setVisible(true);
    }

    void reloadWaypointDisplayFromList() {
        for (JLabel waypointLabel : waypointLabels) {
            waypointsListDialog.remove(waypointLabel);
        }
        for (PosGetter.Waypoint waypoint : PosGetter.waypointsList) {
            addWaypointToDisplay(waypoint);
        }
    }

    void showAddWaypointDialog() {
        addWaypointDialog.setVisible(true);
        nameField.requestFocus();
    }

    private void initAddWaypointDialog() {

        JButton okButton = new JButton("确认");
        JButton cancelButton = new JButton("取消");
        addWaypointDialog.setLayout(null);
        addWaypointDialog.setAlwaysOnTop(true);
        addWaypointDialog.setBounds(MITEInfoGetter.getScreenWidth() / 2 - 100, MITEInfoGetter.getScreenHeight() / 2 - 80, 200, 80);
        addWaypointDialog.setResizable(false);
        nameField.setBounds(0, 0, 200, 20);
        okButton.setBounds(1, 20, 66, 20);
        cancelButton.setBounds(67, 20, 66, 20);
        okButton.addActionListener((e) -> {
            Waypoint waypoint = new Waypoint(MITEInfoGetter.getPlayerPos(), nameField.getText());
            addWaypointToDisplay(waypoint);
            waypointsList.add(waypoint);
            nameField.setText("");
            addWaypointDialog.setVisible(false);
        });
        cancelButton.addActionListener((e) -> {
            nameField.setText("");
            addWaypointDialog.setVisible(false);
        });
        addWaypointDialog.add(nameField);
        addWaypointDialog.add(okButton);
        addWaypointDialog.add(cancelButton);
    }

    /**
     * Launch the application.
     */
    void start() {
        EventQueue.invokeLater(() -> {
            try {
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    void setPosDialogBounds(int x, int y) {
        frame.setBounds(x, y, 200, 200);
    }

    void setWaypointsListDialogPos(int x, int y) {
        waypointsListDialog.setLocation(x, y);
    }

    void setPosInfo(int x, int y, int z) {
        xLabel.setText("X坐标" + x);
        yLabel.setText("Y坐标" + y);
        zLabel.setText("Z坐标" + z);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frame = new JDialog();
        frame.setAlwaysOnTop(true);
        frame.setBounds(100, 100, 200, 200);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(false);
        frame.setUndecorated(true);
        frame.setVisible(true);
        xLabel.setBounds(0, 0, 200, 20);
        yLabel.setBounds(0, 20, 200, 20);
        zLabel.setBounds(0, 40, 200, 20);
        xLabel.setFont(bigFont);
        yLabel.setFont(bigFont);
        zLabel.setFont(bigFont);
        Color fontColor = new Color(72, 249, 26, 247);
        xLabel.setForeground(fontColor);
        yLabel.setForeground(fontColor);
        zLabel.setForeground(fontColor);
        frame.add(xLabel);
        frame.add(yLabel);
        frame.add(zLabel);
        frame.setBackground(new Color(0, 0, 0, 0));
    }

    private void addWaypointToDisplay(Waypoint waypoint) {

        JLabel waypointLabel = new JLabel();
        waypointLabel.setFont(font);
        waypointLabel.setBounds(0, waypointsCount * 20, waypoint.toString().length() * 10, 20);
        waypointsListDialog.setSize(
                Math.max(waypointLabel.getSize().width, waypointsListDialog.getWidth()),
                waypointsListDialog.getHeight() + 20);
        waypointLabel.setText(waypoint.toString());
        waypointLabel.setForeground(new Color(72, 249, 26, 247));
        waypointsListDialog.add(waypointLabel);
        waypointLabels.add(waypointLabel);
        waypointsCount++;
    }

    void getAndReloadWaypointsFromFile(File waypointsFile) {
        waypointsList.clear();
        try (FileInputStream inputStream = new FileInputStream(waypointsFile);
             Scanner fileScanner = new Scanner(inputStream)) {
            int i = 0;
            while (fileScanner.hasNextLine()) {
                String singleWaypointString = fileScanner.nextLine();
                String waypointName = singleWaypointString.substring(singleWaypointString.lastIndexOf("name:") + 5, singleWaypointString.indexOf(";"));
                singleWaypointString = singleWaypointString.replace("name:" + waypointName + ";", "");
                int WaypointX = Integer.parseInt(singleWaypointString.substring(singleWaypointString.indexOf("x:") + 2, singleWaypointString.indexOf(";")));
                singleWaypointString = singleWaypointString.replace("x:" + WaypointX + ";", "");
                int WaypointY = Integer.parseInt(singleWaypointString.substring(singleWaypointString.indexOf("y:") + 2, singleWaypointString.indexOf(";")));
                singleWaypointString = singleWaypointString.replace("y:" + WaypointY + ";", "");
                int WaypointZ = Integer.parseInt(singleWaypointString.substring(singleWaypointString.indexOf("z:") + 2));
                waypointsList.add(new Waypoint(atc.a(WaypointX, WaypointY, WaypointZ), waypointName));
                i++;
            }
            waypointsCount =0;
            reloadWaypointDisplayFromList();
            System.out.println("Reloaded " + i + " waypoint(s) from file: " + waypointsFile.getCanonicalPath());
            MITEInfoGetter.sendMessage("从文件重新加载了 " + i + "个路径点");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Waypoint {
        atc pos;
        String name;

        Waypoint(atc pos, String name) {
            this.pos = pos;
            this.name = name;
        }

        @Override
        public String toString() {
            return name + ": " + "[" + "x:" + pos.getBlockX() + " y:" + pos.getBlockY() + " z:" + pos.getBlockZ() + "]";
        }
    }

}
