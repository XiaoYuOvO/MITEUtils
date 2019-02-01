import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class WaypointUtil {
    private static ArrayList<Waypoint> waypointsList = new ArrayList<>();
    final AtomicInteger waypointsBackupFileMaximum = new AtomicInteger(4);
    private int waypointsCount = 0;
    private JDialog addWaypointDialog = new JDialog();
    private JDialog waypointsListDialog = new JDialog();
    private JDialog removeWaypointDialog = new JDialog();
    private JTextField nameField = new JTextField();
    private JComboBox<Waypoint> waypointListComboBox = new JComboBox<>(new DefaultComboBoxModel<>());
    private Font font = new Font("Default", Font.BOLD, 14);
    private ArrayList<JLabel> waypointLabels = new ArrayList<>();
    private boolean isRemoveWaypointDialogOpened = false;


    WaypointUtil() {
        initAddWaypointDialog();
        initWaypointListDialog();
        initRemoveWaypointDialog();
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

    private void reloadWaypointDisplayFromList() {
        waypointsCount = 0;
        for (JLabel waypointLabel : waypointLabels) {
            waypointsListDialog.remove(waypointLabel);
        }
        for (Waypoint waypoint : waypointsList) {
            addWaypointToDisplay(waypoint);
        }
    }

    private List<File> getFileSort(File[] files) {
        List<File> list = new ArrayList<>(Arrays.asList(files));
        if (list.size() > 0) {
            list.sort((file, newFile) -> Long.compare(newFile.lastModified(), file.lastModified()));
        }
        return list;
    }

    void getAndReloadWaypointsFromFile(File waypointsFile) {
        waypointsList.clear();
        String singleWaypointString;
        try (FileInputStream inputStream = new FileInputStream(waypointsFile);
             Scanner fileScanner = new Scanner(inputStream)) {
            int i = 0;
            while (fileScanner.hasNextLine()) {
                singleWaypointString = fileScanner.nextLine();
                int WaypointX, WaypointY, WaypointZ;
                String waypointName = "";
                try {
                    waypointName = singleWaypointString.substring(singleWaypointString.lastIndexOf("name:") + 5, singleWaypointString.indexOf(";"));
                    singleWaypointString = singleWaypointString.replace("name:" + waypointName + ";", "");
                    WaypointX = Integer.parseInt(singleWaypointString.substring(singleWaypointString.indexOf("x:") + 2, singleWaypointString.indexOf(";")));
                    singleWaypointString = singleWaypointString.replace("x:" + WaypointX + ";", "");
                    WaypointY = Integer.parseInt(singleWaypointString.substring(singleWaypointString.indexOf("y:") + 2, singleWaypointString.indexOf(";")));
                    singleWaypointString = singleWaypointString.replace("y:" + WaypointY + ";", "");
                    WaypointZ = Integer.parseInt(singleWaypointString.substring(singleWaypointString.indexOf("z:") + 2));
                    waypointsList.add(new Waypoint(atc.a(WaypointX, WaypointY, WaypointZ), waypointName));
                } catch (StringIndexOutOfBoundsException e) {
                    System.err.println("Detected an invalid waypoint,will skip it,name: " + waypointName);
                }
                i++;
            }
            reloadWaypointDisplayFromList();
            System.out.println("Reloaded " + i + " waypoint(s) from file: " + waypointsFile.getCanonicalPath());
            MITEInfoGetter.clearMessage();
            MITEInfoGetter.sendMessage("§f从文件重新加载了§l§o" + i + "§r§f个路径点");
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                System.out.println("Waypoint file not found,will create a new one");
                try {
                    if (!new File(MITEInfoGetter.getWaypointsFile().getCanonicalPath()).createNewFile()) {
                        System.err.println("Cannot create waypoint file,waypoint will be disable");
                        waypointsListDialog.setVisible(false);
                    }
                } catch (IOException e1) {
                    System.err.println("Cannot create waypoint file,waypoint will be disable");
                    waypointsListDialog.setVisible(false);
                    e1.printStackTrace();
                }
            } else {
                System.err.println("Cannot load waypoint file,waypoint will be disable");
                waypointsListDialog.setVisible(false);
                e.printStackTrace();
            }
        }
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
            if (!nameField.getText().equals("")) {
                if (!nameField.getText().contains(";")) {
                    atc currentPos = MITEInfoGetter.getPlayerPos();
                    Waypoint waypoint = new Waypoint(new atc(atc.a, currentPos.getBlockX(), currentPos.getBlockY(), currentPos.getBlockZ()), nameField.getText());
                    addWaypointToDisplay(waypoint);
                    waypointsList.add(waypoint);
                    nameField.setText("");
                    addWaypointDialog.setVisible(false);
                } else
                    JOptionPane.showMessageDialog(addWaypointDialog, "路径点名称中不能含有';'", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        cancelButton.addActionListener((e) -> {
            nameField.setText("");
            addWaypointDialog.setVisible(false);
        });
        nameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!nameField.getText().equals("")) {
                        if (!nameField.getText().contains(";")) {
                            atc currentPos = MITEInfoGetter.getPlayerPos();
                            Waypoint waypoint = new Waypoint(new atc(atc.a, currentPos.getBlockX(), currentPos.getBlockY(), currentPos.getBlockZ()), nameField.getText());
                            addWaypointToDisplay(waypoint);
                            waypointsList.add(waypoint);
                            nameField.setText("");
                            addWaypointDialog.setVisible(false);
                        } else
                            JOptionPane.showMessageDialog(waypointsListDialog, "路径点名称中不能含有';'", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    nameField.setText("");
                    addWaypointDialog.setVisible(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        addWaypointDialog.add(nameField);
        addWaypointDialog.add(okButton);
        addWaypointDialog.add(cancelButton);
    }

    private void initRemoveWaypointDialog() {
        JButton okButton = new JButton("确认");
        JButton cancelButton = new JButton("取消");
        removeWaypointDialog.setAlwaysOnTop(true);
        removeWaypointDialog.setUndecorated(true);
        removeWaypointDialog.setFocusable(true);
        removeWaypointDialog.setResizable(false);
        removeWaypointDialog.setLayout(null);
        removeWaypointDialog.setBounds(MITEInfoGetter.getScreenWidth() / 2 - 100, MITEInfoGetter.getScreenHeight() / 2 - 80, 200, 80);
        waypointListComboBox.setBounds(0, 0, 200, 20);
        okButton.setBounds(1, 20, 66, 20);
        cancelButton.setBounds(67, 20, 66, 20);
        okButton.addActionListener((e) -> {
            waypointsList.remove(waypointListComboBox.getModel().getElementAt(waypointListComboBox.getSelectedIndex()));
            reloadWaypointDisplayFromList();
            removeWaypointDialog.setVisible(false);
            isRemoveWaypointDialogOpened = false;
        });
        cancelButton.addActionListener((e) -> {
            removeWaypointDialog.setVisible(false);
            isRemoveWaypointDialogOpened = false;
        });
        waypointListComboBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    waypointsList.remove(waypointListComboBox.getModel().getElementAt(waypointListComboBox.getSelectedIndex()));
                    reloadWaypointDisplayFromList();
                    removeWaypointDialog.setVisible(false);
                    isRemoveWaypointDialogOpened = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    removeWaypointDialog.setVisible(false);
                    isRemoveWaypointDialogOpened = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        removeWaypointDialog.add(waypointListComboBox);
        removeWaypointDialog.add(okButton);
        removeWaypointDialog.add(cancelButton);
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

    void saveWaypointsListToFile(File targetFile) {
        File backupFile = null;
        File waypointsSavesDir = new File(targetFile.getParent());
        File[] currentBackupFiles = waypointsSavesDir.listFiles((dir, name) -> name.contains("waypoints-backup"));
        int currentBackupFilesCount;

        if (currentBackupFiles!=null) {
            currentBackupFilesCount = currentBackupFiles.length;
            if (currentBackupFilesCount >= waypointsBackupFileMaximum.get()) {
                if (!getFileSort(currentBackupFiles).get(currentBackupFilesCount-1).delete()){
                    System.err.println("Cannot delete latest waypoints file,but will also create a new backup file");
                }
            }
        }
        try {
            GregorianCalendar date = new GregorianCalendar();
            backupFile = new File(targetFile.getCanonicalPath().replace(".txt", "") + "-backup-" +
                    date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DAY_OF_MONTH) + "_" + date.get(Calendar.HOUR_OF_DAY) + "." + date.get(Calendar.MINUTE) + "." + date.get(Calendar.SECOND)
                    + ".txt");
            if (!backupFile.exists()) {
                if (!backupFile.createNewFile()) {
                    System.err.println("Failed to create waypoint backup file");
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to create waypoint backup file");
            e.printStackTrace();
        }
        try (FileOutputStream backupOutputStream = new FileOutputStream(Objects.requireNonNull(backupFile));
             FileInputStream in = new FileInputStream(targetFile);
             Scanner scanner = new Scanner(in)) {
            while (scanner.hasNextLine()) {
                backupOutputStream.write((scanner.nextLine() + "\n").getBytes());
            }
        } catch (IOException e) {
            System.err.println("Failed to create waypoint backup file");
            e.printStackTrace();
        }


        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            int i = 0;
            for (Waypoint waypoint : waypointsList) {
                atc pos = waypoint.pos;
                String outputString = "name:" + waypoint.name + ";x:" + pos.getBlockX() + ";y:" + pos.getBlockY() + ";z:" + pos.getBlockZ();
                outputStream.write(outputString.getBytes());
                outputStream.write('\n');
                i++;
            }
            System.out.println("Saved " + i + " waypoint(s) to file: " + targetFile.getCanonicalPath());
            MITEInfoGetter.clearMessage();
            MITEInfoGetter.sendMessage("§f已保存§l§o" + i + "§r§f个路径点");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setWaypointsListDialogPos(int x, int y) {
        waypointsListDialog.setLocation(x, y);
    }

    void showAddWaypointDialog() {
        addWaypointDialog.setVisible(true);
        nameField.requestFocus();
    }

    void showRemoveWaypointDialog() {
        if (!isRemoveWaypointDialogOpened) {
            waypointListComboBox.removeAllItems();
            removeWaypointDialog.setVisible(true);
            for (Waypoint waypoint : waypointsList) {
                waypointListComboBox.addItem(waypoint);
            }
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
