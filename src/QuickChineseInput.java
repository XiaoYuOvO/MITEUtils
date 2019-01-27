import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class QuickChineseInput {
    private static JDialog frame;
    private static Point origin = new Point();
    private static JTextField textField = new JTextField();

    void requestFocus() {
        textField.requestFocus();
    }

    void setBonuds(int x, int y) {
        frame.setBounds(x, y, 200, 25);
    }

    void start() {
        frame = new JDialog();
        frame.setBounds(100, 100, 200, 25);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setOpacity(1f);
//        frame.addMouseListener(new MouseAdapter() {
//            // 按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
//            public void mousePressed(MouseEvent e) {
//                // 当鼠标按下的时候获得窗口当前的位置
//                origin.x = e.getX();
//                origin.y = e.getY();
//            }
//        });
//        frame.addMouseMotionListener(new MouseMotionAdapter() {
//            // 拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
//            public void mouseDragged(MouseEvent e) {
//                // 当鼠标拖动时获取窗口当前位置
//                Point p = frame.getLocation();
//                // 设置窗口的位置
//                // 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
//                frame.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
//            }
//        });
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    StringSelection clipBoardContent = new StringSelection(textField.getText());
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipBoardContent, clipBoardContent);
                    textField.setText("");
                    try {
                        Robot rb = new Robot();
                        rb.keyPress(KeyEvent.VK_CONTROL);
                        rb.keyPress(KeyEvent.VK_SHIFT);
                        rb.keyRelease(KeyEvent.VK_CONTROL);
                        rb.keyRelease(KeyEvent.VK_SHIFT);
                        rb.keyPress(KeyEvent.VK_ALT);
                        rb.keyPress(KeyEvent.VK_TAB);
                        rb.keyRelease(KeyEvent.VK_ALT);
                        rb.keyRelease(KeyEvent.VK_TAB);
                    } catch (AWTException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        textField.setBounds(0, 5, 200, 20);
        textField.setToolTipText("在此输入信息");
        textField.setForeground(new Color(0, 25, 255, 254));
        textField.setCaretColor(new Color(0, 0, 0, 0));
        textField.setDisabledTextColor(new Color(0, 0, 0, 0));
        textField.setFont(new Font("Monospaced", Font.BOLD, 16));

        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setForeground(new Color(0, 0, 0, 0));
        frame.setOpacity(0.5f);
        frame.add(textField);
    }
}
