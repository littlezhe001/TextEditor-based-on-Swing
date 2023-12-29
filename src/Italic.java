import javax.swing.*;
import javax.swing.text.*;

public class Italic {
    public void setItalic(JTextPane textPane) {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();

        // 如果起始位置和结束位置相等，表示没有选中文本
        if (start == end) {
            return;
        }

        Element element =  doc.getCharacterElement(start);
        AttributeSet attributeSet = element.getAttributes();
        boolean isItalic = StyleConstants.isItalic(attributeSet);

        // 创建一个样式
        Style italicStyle = textPane.addStyle("Italic", null);
        StyleConstants.setItalic(italicStyle, !isItalic);

        // 将样式应用到选中的文本
        doc.setCharacterAttributes(start, end - start, italicStyle, false);
    }
}
