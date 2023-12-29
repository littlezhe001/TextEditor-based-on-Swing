import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Edit extends JFrame {
    public static int num = 1;
    private JTextPane textPane = new JTextPane();


    public Edit(String name, String context) {
        super(name);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        textPane.setText(context);
        UndoManager undoManager = new UndoManager();
        textPane.getDocument().addUndoableEditListener(undoManager);
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("文件");
        JMenu EditMenu = new JMenu("编辑");

        JMenuItem saveMenuItem = new JMenuItem("保存");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Text_file textFile = new Text_file();
                textFile.SaveText(textPane);
            }
        });
        fileMenu.add(saveMenuItem);

        // 工具栏
        JToolBar toolbar = new JToolBar();
        JButton boldButton = new JButton("加粗");
        boldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bold bold = new Bold();
                bold.setBold(textPane);
            }
        });
        toolbar.add(boldButton);

        JButton italicButton = new JButton("倾斜");
        italicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Italic italic = new Italic();
                italic.setItalic(textPane);
            }
        });
        toolbar.add(italicButton);

        JButton underlineButton = new JButton("下划线");
        underlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Underline underline = new Underline();
                underline.setUnderline(textPane);
            }
        });
        toolbar.add(underlineButton);

        JButton char_colorButton = new JButton("更改颜色");
        char_colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Char_Color charColor = new Char_Color();
                // 创建一个 JColorChooser 对象
                JColorChooser colorChooser = new JColorChooser();
                // 显示颜色选择器
                Color color = colorChooser.showDialog(null, "选择颜色", Color.BLACK);
                charColor.setColor(textPane,color);
            }
        });
        toolbar.add(char_colorButton);

        JButton bigSizebutton = new JButton("A+");
        bigSizebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Char_size charSize = new Char_size();
                charSize.setSizeToBIg(textPane);
            }
        });
        toolbar.add(bigSizebutton);

        JButton smallSizeButton = new JButton("A-");
        smallSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Char_size charSize = new Char_size();
                charSize.setSizeToSmall(textPane);
            }
        });
        toolbar.add(smallSizeButton);

        JButton sizeSetButton = new JButton("字体样式");
        sizeSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建一个字体名称数组
                String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
                // 弹出字体选择对话框，获取用户选择的字体
                String selectedFontName = (String) JOptionPane.showInputDialog(Edit.this, "选择字体", "字体选择", JOptionPane.QUESTION_MESSAGE, null, fontNames, textPane.getFont().getFamily());
                Char_style charStyle = new Char_style();
                charStyle.setCharStyle(textPane, selectedFontName);
            }
        });
        toolbar.add(sizeSetButton);

        JMenuItem undoMenuItem = new JMenuItem("撤销");
        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()){
                    undoManager.undo();
                }
            }
        });
        EditMenu.add(undoMenuItem);

        JMenuItem copyMenuItem = new JMenuItem("复制");
        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 获取 selectionStart() 和 selectionEnd() 方法

                int start = textPane.getSelectionStart();
                int end = textPane.getSelectionEnd();

                // 获取文本内容
                String text = textPane.getText();

                // 将 selectionStart() 到 selectionEnd() 之间的文本内容复制到剪贴板
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(text.substring(start, end)), null);
            }
        });
        EditMenu.add(copyMenuItem);

        JMenuItem pasteMenuItem = new JMenuItem("粘贴");
        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                String text = null;
                try {
                    text = (String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                int start = textPane.getSelectionStart();
                int end = textPane.getSelectionEnd();

                // 将剪贴板中的内容粘贴到 JTextPane 对象中
                textPane.setText(textPane.getText().substring(0, start) + text + textPane.getText().substring(end));
            }
        });
        EditMenu.add(pasteMenuItem);

        // 滚动条
        JScrollPane scrollPane = new JScrollPane(textPane);

        // 面板添加组件
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(toolbar, BorderLayout.NORTH);

        // 菜单设置
        menuBar.add(fileMenu);
        menuBar.add(EditMenu);

        setContentPane(contentPane);
        setJMenuBar(menuBar);
    }
}
