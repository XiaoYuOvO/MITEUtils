import javax.swing.*;
import java.awt.*;

import static java.awt.Font.BOLD;

class PosGetter {

    //    // 全局的位置变量，用于表示鼠标在窗口上的位置
//    static Point origin = new Point();
    private static JDialog frame;
    private static JLabel xLabel = new JLabel("X坐标");
    private static JLabel yLabel = new JLabel("Y坐标");
    private static JLabel zLabel = new JLabel("Z坐标");
    private static Font bigFont = new Font("Monospaced", BOLD, 23);

    /**
     * Create the application.
     */
    PosGetter() {
        initialize();

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

    void setBounds(int x, int y) {
        frame.setBounds(x, y, 200, 200);
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

}
