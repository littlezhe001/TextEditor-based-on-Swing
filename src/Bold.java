import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.*;

public class Bold {
    public void setBold(JTextPane textPane) {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();

        if (start == end) {
            return;
        }

        Element element =  doc.getCharacterElement(start);
        AttributeSet attributeSet = element.getAttributes();
        boolean isBold = StyleConstants.isBold(attributeSet);

        Style boldStyle =  textPane.addStyle("Bold", null);
        StyleConstants.setBold(boldStyle, !isBold);

        doc.setCharacterAttributes(start, end - start, boldStyle, false);
    }
}
