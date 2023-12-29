import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class Char_Color {
    public void setColor(JTextPane textPane, Color color) {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();

        // 如果起始位置和结束位置相等，表示没有选中文本
        if (start == end) {
            return;
        }

        // 创建一个样式
        Style colorStyle = textPane.addStyle("Color", null);
        StyleConstants.setForeground(colorStyle, color);

        // 将样式应用到选中的文本
        doc.setCharacterAttributes(start, end - start, colorStyle, false);
    }
}
