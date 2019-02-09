import javax.swing.*;
import java.awt.*;

public class TimeUtils {
    JDialog timeInfoDialog = new JDialog();
    JLabel timelabel = new JLabel();

    TimeUtils() {
        initialize();
    }

    private void initialize() {
        timeInfoDialog = new JDialog();
        timeInfoDialog.setAlwaysOnTop(true);
        timeInfoDialog.setBounds(100, 100, 200, 200);
        timeInfoDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        timeInfoDialog.getContentPane().setLayout(null);
        timeInfoDialog.setLocationRelativeTo(null);
        timeInfoDialog.setFocusable(false);
        timeInfoDialog.setUndecorated(true);
        timeInfoDialog.setVisible(true);
        timelabel.setBounds(0, 0, 200, 20);
        timelabel.setFont(new Font("Default", Font.BOLD, 18));
        Color fontColor = new Color(72, 249, 26, 247);
        timelabel.setForeground(fontColor);
        timeInfoDialog.add(timelabel);
        timeInfoDialog.setBackground(new Color(0, 0, 0, 0));
    }

    void setTimeInfoDialogPos(int x,int y){
        this.timeInfoDialog.setBounds(x,y,timeInfoDialog.getWidth(),timeInfoDialog.getHeight());
    }
    void setTime(int time) {
        int hourTime = time / 1000;
        if (hourTime == 12) {
            timelabel.setText("正午");
        } else if (hourTime == 0) {
            timelabel.setText("午夜");
        } else if (hourTime < 12) {
            timelabel.setText(hourTime + "AM(早上)");
        } else {
            timelabel.setText(hourTime-12 + "PM(下午)");
        }
    }
}
