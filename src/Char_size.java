import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import javax.swing.text.*;

public class Char_size {
    public void setSizeToBIg(JTextPane textPane) {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();

        if (start == end) {
            return;
        }

        // 获取当前字体大小
        AttributeSet attributes = textPane.getCharacterAttributes();
        int currentSize = StyleConstants.getFontSize(attributes);

        // 增大字号
        int newSize = Math.min(currentSize + 2, 36);

        // 创建一个新字体，使用增大后的大小
        Font newFont = new Font(StyleConstants.getFontFamily(attributes), Font.PLAIN, newSize);

        // 将新字体应用到选中的文本
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setFontSize(set, newSize);
        StyleConstants.setFontFamily(set, newFont.getFamily());

        doc.setCharacterAttributes(start, end - start, set, false);
    }

    public void setSizeToSmall(JTextPane textPane) {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();

        if (start == end) {
            return;
        }

        // 获取当前字体大小
        AttributeSet attributes = textPane.getCharacterAttributes();
        int currentSize = StyleConstants.getFontSize(attributes);

        // 字号减小
        int newSize = Math.min(currentSize - 2, 36);

        // 创建一个新字体，使用增大后的大小
        Font newFont = new Font(StyleConstants.getFontFamily(attributes), Font.PLAIN, newSize);

        // 将新字体应用到选中的文本
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setFontSize(set, newSize);
        StyleConstants.setFontFamily(set, newFont.getFamily());

        doc.setCharacterAttributes(start, end - start, set, false);
    }
}
