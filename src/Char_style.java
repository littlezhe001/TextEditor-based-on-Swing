import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;


public class Char_style {
    public void setCharStyle (JTextPane textPane, String style_name){
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();

        if (start == end) {
            return;
        }


        Font originalFont = textPane.getFont();
        Font selectedFont = new Font(style_name, originalFont.getStyle(), originalFont.getSize());

        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setFontFamily(set, selectedFont.getFamily());

        doc.setCharacterAttributes(start, end - start, set, false);

    }
}
