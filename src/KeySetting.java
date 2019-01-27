import org.lwjgl.input.Keyboard;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicInteger;

import static java.awt.event.KeyEvent.*;
import static org.lwjgl.input.Keyboard.*;

class KeySetting {
    static final AtomicInteger Key_CycleToChineseInput = new AtomicInteger(KEY_T);
    static final AtomicInteger Key_OpenSetting = new AtomicInteger(KEY_G);
    static final AtomicInteger Key_SaveCurrentPosToWaypoint = new AtomicInteger(KEY_M);
    static final AtomicInteger Key_CopyCurrentPosToClipboard = new AtomicInteger(KEY_L);
    static final AtomicInteger Key_SaveWaypointsToFile = new AtomicInteger(KEY_P);
    static final AtomicInteger Key_ReloadWaypointsFromeFile = new AtomicInteger(KEY_K);
    private static int KeySettingPairCount = 0;
    private JDialog keySettingDialog = new JDialog();
    private JButton closeButton = new JButton("确定");

    KeySetting() {
        init();
    }

    private static int toLWJGLCode(int key) {
        switch (key) {
            case VK_ESCAPE:
                return KEY_ESCAPE;
            case VK_1:
                return KEY_1;
            case VK_2:
                return KEY_2;
            case VK_3:
                return KEY_3;
            case VK_4:
                return KEY_4;
            case VK_5:
                return KEY_5;
            case VK_6:
                return KEY_6;
            case VK_7:
                return KEY_7;
            case VK_8:
                return KEY_8;
            case VK_9:
                return KEY_9;
            case VK_0:
                return KEY_0;
            case VK_MINUS:
                return KEY_MINUS;
            case VK_EQUALS:
                return KEY_EQUALS;
            case VK_BACK_SPACE:
                return KEY_BACK;
            case VK_TAB:
                return KEY_TAB;
            case VK_Q:
                return KEY_Q;
            case VK_W:
                return KEY_W;
            case VK_E:
                return KEY_E;
            case VK_R:
                return KEY_R;
            case VK_T:
                return KEY_T;
            case VK_Y:
                return KEY_Y;
            case VK_U:
                return KEY_U;
            case VK_I:
                return KEY_I;
            case VK_O:
                return KEY_O;
            case VK_P:
                return KEY_P;
            case VK_OPEN_BRACKET:
                return KEY_LBRACKET;
            case VK_CLOSE_BRACKET:
                return KEY_RBRACKET;
            case VK_ENTER:
                return KEY_RETURN;
            case VK_CONTROL:
                return KEY_LCONTROL;
            case VK_A:
                return KEY_A;
            case VK_S:
                return KEY_S;
            case VK_D:
                return KEY_D;
            case VK_F:
                return KEY_F;
            case VK_G:
                return KEY_G;
            case VK_H:
                return KEY_H;
            case VK_J:
                return KEY_J;
            case VK_K:
                return KEY_K;
            case VK_L:
                return KEY_L;
            case VK_SEMICOLON:
                return KEY_SEMICOLON;
            case VK_QUOTE:
                return KEY_APOSTROPHE;
            case VK_DEAD_GRAVE:
                return KEY_GRAVE;
            case VK_BACK_SLASH:
                return KEY_BACKSLASH;
            case VK_Z:
                return KEY_Z;
            case VK_X:
                return KEY_X;
            case VK_C:
                return KEY_C;
            case VK_V:
                return KEY_V;
            case VK_B:
                return KEY_B;
            case VK_N:
                return KEY_N;
            case VK_M:
                return KEY_M;
            case VK_COMMA:
                return KEY_COMMA;
            case VK_PERIOD:
                return KEY_PERIOD;
            case VK_SLASH:
                return KEY_SLASH;
            case VK_SHIFT:
                return KEY_LSHIFT;
            case VK_MULTIPLY:
                return KEY_MULTIPLY;
            case VK_SPACE:
                return KEY_SPACE;
            case VK_CAPS_LOCK:
                return KEY_CAPITAL;
            case VK_F1:
                return KEY_F1;
            case VK_F2:
                return KEY_F2;
            case VK_F3:
                return KEY_F3;
            case VK_F4:
                return KEY_F4;
            case VK_F5:
                return KEY_F5;
            case VK_F6:
                return KEY_F6;
            case VK_F7:
                return KEY_F7;
            case VK_F8:
                return KEY_F8;
            case VK_F9:
                return KEY_F9;
            case VK_F10:
                return KEY_F10;
            case VK_NUM_LOCK:
                return KEY_NUMLOCK;
            case VK_SCROLL_LOCK:
                return KEY_SCROLL;
            case VK_NUMPAD7:
                return KEY_NUMPAD7;
            case VK_NUMPAD8:
                return KEY_NUMPAD8;
            case VK_NUMPAD9:
                return KEY_NUMPAD9;
            case VK_SUBTRACT:
                return KEY_SUBTRACT;
            case VK_NUMPAD4:
                return KEY_NUMPAD4;
            case VK_NUMPAD5:
                return KEY_NUMPAD5;
            case VK_NUMPAD6:
                return KEY_NUMPAD6;
            case VK_ADD:
                return KEY_ADD;
            case VK_NUMPAD1:
                return KEY_NUMPAD1;
            case VK_NUMPAD2:
                return KEY_NUMPAD2;
            case VK_NUMPAD3:
                return KEY_NUMPAD3;
            case VK_NUMPAD0:
                return KEY_NUMPAD0;
            case VK_DECIMAL:
                return KEY_DECIMAL;
            case VK_F11:
                return KEY_F11;
            case VK_F12:
                return KEY_F12;
            case VK_F13:
                return KEY_F13;
            case VK_F14:
                return KEY_F14;
            case VK_F15:
                return KEY_F15;
            case VK_KANA:
                return KEY_KANA;
            case VK_CONVERT:
                return KEY_CONVERT;
            case VK_NONCONVERT:
                return KEY_NOCONVERT;
            case VK_CIRCUMFLEX:
                return KEY_CIRCUMFLEX;
            case VK_AT:
                return KEY_AT;
            case VK_COLON:
                return KEY_COLON;
            case VK_UNDERSCORE:
                return KEY_UNDERLINE;
            case VK_STOP:
                return KEY_STOP;
            case VK_DIVIDE:
                return KEY_DIVIDE;
            case VK_PAUSE:
                return KEY_PAUSE;
            case VK_HOME:
                return KEY_HOME;
            case VK_UP:
                return KEY_UP;
            case VK_PAGE_UP:
                return KEY_PRIOR;
            case VK_LEFT:
                return KEY_LEFT;
            case VK_RIGHT:
                return KEY_RIGHT;
            case VK_END:
                return KEY_END;
            case VK_DOWN:
                return KEY_DOWN;
            case VK_PAGE_DOWN:
                return KEY_NEXT;
            case VK_INSERT:
                return KEY_INSERT;
            case VK_DELETE:
                return KEY_DELETE;
            case VK_ALT:
                return KEY_LMENU; //todo: location left
            case VK_BACK_QUOTE:
                return KEY_GRAVE;
            case VK_WINDOWS:
                return KEY_LMETA;
            case VK_CONTEXT_MENU:
                return KEY_APPS;
        }
        System.err.println("unsupported key:" + key);
        return 0x10000 + key;
    }

    private void init() {
        keySettingDialog.setLayout(null);
        addPairOfKeySettingAndTip("打开设置:", Key_OpenSetting);
        addPairOfKeySettingAndTip("切换至中文输入:", Key_CycleToChineseInput);
        addPairOfKeySettingAndTip("将当前坐标复制到剪贴板:", Key_CopyCurrentPosToClipboard);
        addPairOfKeySettingAndTip("将当前位置保存为路径点:", Key_SaveCurrentPosToWaypoint);
        addPairOfKeySettingAndTip("将路径点保存至文件:", Key_SaveWaypointsToFile);
        addPairOfKeySettingAndTip("重新从文件载入路径点", Key_ReloadWaypointsFromeFile);
        closeButton.setBounds(150, KeySettingPairCount * 20 + 20, 75, 40);
        closeButton.addActionListener((e -> keySettingDialog.setVisible(false)));
        keySettingDialog.add(closeButton);
        keySettingDialog.setTitle("设置");
        keySettingDialog.setBounds(MITEInfoGetter.getScreenWidth() / 2, MITEInfoGetter.getScreenHeight() / 2, 400, KeySettingPairCount * 20 + 90);
        keySettingDialog.setResizable(false);
        keySettingDialog.setVisible(false);
    }

    void showKeySettingFrame() {
        keySettingDialog.setVisible(true);
    }

    private void addPairOfKeySettingAndTip(String tipString, AtomicInteger keyCodeReference) {
        JLabel label = new JLabel(tipString);
        JButton button = new JButton(Keyboard.getKeyName(keyCodeReference.get()));
        label.setBounds(0, KeySettingPairCount * 20, tipString.length() * 20, 20);
        button.setBounds(300, KeySettingPairCount * 20, 100, 20);
        button.addActionListener((e -> button.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyCodeReference.set(toLWJGLCode(e.getKeyCode()));
                button.setText(KeyEvent.getKeyText(e.getKeyCode()));
                button.removeKeyListener(this);
                System.out.println(keyCodeReference.get());
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        })));

        keySettingDialog.add(label);
        keySettingDialog.add(button);
        KeySettingPairCount++;
    }
}
