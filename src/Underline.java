import javax.swing.*;
import javax.swing.text.*;

public class Underline {
    public void setUnderline(JTextPane textPane) {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();

        // 如果起始位置和结束位置相等，表示没有选中文本
        if (start == end) {
            return;
        }

        Element element =  doc.getCharacterElement(start);
        AttributeSet attributeSet = element.getAttributes();
        boolean isUnderline = StyleConstants.isUnderline(attributeSet);
        // 创建一个样式
        Style underlineStyle = textPane.addStyle("Underline", null);
        StyleConstants.setUnderline(underlineStyle, !isUnderline);

        // 将样式应用到选中的文本
        doc.setCharacterAttributes(start, end - start, underlineStyle, false);
    }
}
