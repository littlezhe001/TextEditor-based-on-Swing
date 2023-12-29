

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.Map;


class About extends JFrame{
    public About(){
        super("关于"); //调用父类的构造方法
        this.setSize(280, 100);  //设置窗口大小,宽度280，高度100
        this.setLocation(250,300);  //设置窗口位置为距离屏幕左边水平方向250，上方垂直方向300
        this.setResizable(false);  //设置窗体大小不可改变
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //点击关闭按钮直接退出
        this.setVisible(true);  //设置窗体可见
        JPanel panel = new JPanel();  //使用默认的浮动布局
        JLabel label1 = new JLabel("这是一个用Java开发的简易文本编辑器。");
        JLabel label2 = new JLabel("2021级信安3班万毕南");

        panel.add(label1);
        panel.add(label2);
        this.add(panel);
    }
}

class Help extends JFrame{
    public Help(){
        super("帮助");  //调用父类的构造方法
        this.setSize(500, 150);  //设置窗口大小,宽度500，高度150
        this.setLocation(250,300);  //设置窗口位置为距离屏幕左边水平方向250，上方垂直方向300
        this.setResizable(false);  //设置窗体大小不可改变
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //点击关闭按钮是直接退出
        this.setVisible(true);  //设置窗体可见

        Container c = this.getContentPane();  //初始化一个容器
        c.setLayout(new GridLayout(3,0));   //将窗口布局设置为网格式布局，网格的行数和列数分别是3和0

        //创建标签文字
        JLabel label1 = new JLabel("1、“菜单”中的功能“新建”、“打开”、“保存”、“另存为”、“退出”通过点击对应使用。");
        JLabel label2 = new JLabel("2、“编辑”中的“剪切”“复制”“粘贴”可对应使用快捷键Ctrl + X、Ctrl + C、Ctrl +V。");
        JLabel label3 = new JLabel("3、“格式”里的“自动换行”勾选后到窗口边缘自动换行；不勾选则向后继续写入。");
        c.add(label1);c.add(label2);c.add(label3);  //添加标签到容器中
    }
}
public class TextEditer_Wan extends JFrame{
    private JDesktopPane desktopPane;
    private UndoManager undoManager = new UndoManager();
    public TextEditer_Wan()
    {
        super("简易文本编辑器");    //调用父类的构造方法
        //创建菜单栏(JMenuBar)对象
        JMenuBar mBar = new JMenuBar();
        //在JFrame等容器中设置菜单栏对象，即将菜单栏添加到框架容器中
        this.setJMenuBar(mBar);

        // 创建JDesktopPane
        desktopPane = new JDesktopPane();
        this.setContentPane(desktopPane);

        //创建菜单
        JMenu file = new JMenu("文件");
        JMenu edit = new JMenu("编辑");
        JMenu form = new JMenu("换行");
        JMenu help = new JMenu("帮助");

        //将菜单添加到菜单栏中
        mBar.add(file);
        mBar.add(edit);
        mBar.add(form);
        mBar.add(help);

        JTextArea workArea = new JTextArea();  //创建多行文本框
        JScrollPane imgScrollPane = new JScrollPane(workArea);  //创建一个空视图，只要组件内容超过视图大小就会显示水平和垂直滚动条
        add(imgScrollPane,BorderLayout.CENTER);  //将当前类的对象实例加到frame的中间位置

        //定义打开和保存对话框
        FileDialog openDia;
        FileDialog saveDia;
        //默认模式为 FileDialog.LOAD
        openDia = new FileDialog(this,"打开",FileDialog.LOAD);
        saveDia = new FileDialog(this,"另存为",FileDialog.SAVE);

        JMenuItem item1_1 = new JMenuItem("新建");  //生成一个对象
        item1_1.addActionListener(e -> {
            // 创建一个新的JInternalFrame
            JInternalFrame internalFrame = new JInternalFrame("新文档", true, true, true, true);
            internalFrame.setSize(200, 200);
            internalFrame.setVisible(true);

            // 创建一个新的JTextPane并添加到JInternalFrame
            JTextPane textPane = new JTextPane();
            textPane.getDocument().addUndoableEditListener(undoManager);  // 添加UndoManager到JTextPane的Document
            JScrollPane scrollPane = new JScrollPane(textPane);
            internalFrame.add(scrollPane);

            // 添加JInternalFrame到JDesktopPane
            desktopPane.add(internalFrame);
        });

        JMenuItem item1_2 = new JMenuItem("打开");
        item1_2.addActionListener(e -> {  //对当前的控件添加监听器，点击控件时就会触发监听函数里面的内容
            openDia.setVisible(true);
            String dirPath = openDia.getDirectory();
            String fileName = openDia.getFile();
            if(dirPath == null || fileName == null){
                return ;
            }
            File fileO = new File(dirPath,fileName);
            try{
                BufferedReader bufr = new BufferedReader(new FileReader(fileO));
                String line = null;
                while((line = bufr.readLine()) != null){
                    // 创建一个新的JInternalFrame
                    JInternalFrame internalFrame = new JInternalFrame(fileName, true, true, true, true);
                    internalFrame.setSize(200, 200);
                    internalFrame.setVisible(true);

                    // 创建一个新的JTextArea并添加到JInternalFrame
                    JTextArea textArea = new JTextArea();
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    internalFrame.add(scrollPane);

                    // 添加JInternalFrame到JDesktopPane
                    desktopPane.add(internalFrame);

                    // 将读取的内容添加到新的JTextArea
                    textArea.append(line + "\r\n");
                }
                bufr.close();
            }
            catch(IOException er1){
                throw new RuntimeException("文件读取失败！");
            }
        });

        JMenuItem item1_3 = new JMenuItem("保存");
        item1_3.addActionListener(e -> {
            //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
            // 获取当前活动的JInternalFrame
            JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
            if (selectedFrame == null) {
                return;
            }
            // 获取JInternalFrame的JTextPane
            JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
            JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();

            File fileS = null;
            if(fileS == null){
                saveDia.setVisible(true);
                String dirPath = saveDia.getDirectory();
                String fileName = saveDia.getFile();

                if(dirPath == null || fileName == null)
                    return;

                fileS = new File(dirPath,fileName);
            }
            try{
                BufferedWriter bufw = new BufferedWriter(new FileWriter(fileS));
                String text = textPane.getText();
                bufw.write(text);
                bufw.close();

                // 设置JInternalFrame的标题为保存的文件名
                selectedFrame.setTitle(fileS.getName());
            }catch(IOException er){
                throw new RuntimeException("文件保存失败！");
            }
        });


        JMenuItem item1_4 = new JMenuItem("退出");
        item1_4.addActionListener(e -> {  //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
            System.exit(0);
        });


        JMenuItem undoItem = new JMenuItem("撤销");
        undoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });
        edit.add(undoItem);


        JMenuItem item2_1 = new JMenuItem("剪切");
        item2_1.addActionListener(e -> {
            // 获取当前活动的JInternalFrame
            JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
            if (selectedFrame == null) {
                return;
            }
            // 获取JInternalFrame的JTextPane
            JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
            JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();
            textPane.cut();
        });

        JMenuItem item2_2 = new JMenuItem("复制");
        item2_2.addActionListener(e -> {
            // 获取当前活动的JInternalFrame
            JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
            if (selectedFrame == null) {
                return;
            }
            // 获取JInternalFrame的JTextPane
            JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
            JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();
            textPane.copy();
        });

        JMenuItem item2_3 = new JMenuItem("粘贴");
        item2_3.addActionListener(e -> {
            // 获取当前活动的JInternalFrame
            JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
            if (selectedFrame == null) {
                return;
            }
            // 获取JInternalFrame的JTextPane
            JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
            JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();
            textPane.paste();
        });

        JRadioButtonMenuItem item3_1 = new JRadioButtonMenuItem("自动换行",false);
        item3_1.addActionListener(e -> {
            //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
            // 获取当前活动的JInternalFrame
            JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
            if (selectedFrame == null) {
                return;
            }
            // 获取JInternalFrame的JTextPane
            JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
            JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();

            // 获取文本内容
            String text = textPane.getText();

            // 如果文本长度超过一定长度，就在文本末尾添加一个换行符
            if (text.length() > 80) {
                textPane.setText(text + "\n");
            }
        });

        JMenuItem item4_1 = new JMenuItem("帮助");
        item4_1.addActionListener(event -> {  //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
            new Help();
        });
        JMenuItem item4_2 = new JMenuItem("关于");
        item4_2.addActionListener(event -> {  //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
            new About ();
        });

        //在菜单中添加菜单项
        file.add(item1_1);
        file.add(item1_2);
        file.add(item1_3);
        file.add(item1_4);
        edit.add(item2_1);
        edit.add(item2_2);
        edit.add(item2_3);
        form.add(item3_1);
        help.add(item4_1);
        help.add(item4_2);

//功能
        JMenu format = new JMenu("功能");
        mBar.add(format);
// 添加改变字体颜色子菜单项
        JMenuItem changeColor = new JMenuItem("字体颜色");
        changeColor.addActionListener(e -> {
            // 获取当前活动的JInternalFrame
            JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
            if (selectedFrame == null) {
                return;
            }
            // 获取JInternalFrame的JTextPane
            JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
            JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();
            // 弹出颜色选择对话框，获取用户选择的颜色
            Color selectedColor = JColorChooser.showDialog(TextEditer_Wan.this, "选择颜色", textPane.getForeground());
            // 如果用户取消选择，selectedColor 将为 null
            if (selectedColor != null) {
                // 改变字体颜色
                textPane.setForeground(selectedColor);
            }
        });
        format.add(changeColor);

//改变字体
        JMenuItem changeFont = new JMenuItem("字体样式");
        changeFont.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // 获取当前活动的JInternalFrame
                JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
                if (selectedFrame == null) {
                    return;
                }
                // 获取JInternalFrame的JTextPane
                JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
                JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();

                // 创建一个字体名称数组
                String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

                // 弹出字体选择对话框，获取用户选择的字体
                String selectedFontName = (String) JOptionPane.showInputDialog(TextEditer_Wan.this, "选择字体", "字体选择", JOptionPane.QUESTION_MESSAGE, null, fontNames, textPane.getFont().getFamily());

                // 如果用户取消选择，selectedFontName 将为 null
                if (selectedFontName != null) {
                    // 改变字体
                    Font originalFont = textPane.getFont();
                    Font selectedFont = new Font(selectedFontName, originalFont.getStyle(), originalFont.getSize());
                    textPane.setFont(selectedFont);
                }
            }
        });
        format.add(changeFont);
//改变字体大小
        JMenuItem changeFontSize = new JMenuItem("字体大小");
        changeFontSize.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // 获取当前活动的JInternalFrame
                JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
                if (selectedFrame == null) {
                    return;
                }
                // 获取JInternalFrame的JTextPane
                JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
                JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();

                // 创建一个字体大小数组
                Integer[] fontSizes = new Integer[72];
                for (int i = 0; i < 72; i++) {
                    fontSizes[i] = i + 1;
                }

                // 弹出字体大小选择对话框，获取用户选择的字体大小
                Integer selectedFontSize = (Integer) JOptionPane.showInputDialog(TextEditer_Wan.this, "选择字体大小", "字体大小选择", JOptionPane.QUESTION_MESSAGE, null, fontSizes, textPane.getFont().getSize());

                // 如果用户取消选择，selectedFontSize 将为 null
                if (selectedFontSize != null) {
                    // 改变字体大小
                    Font originalFont = textPane.getFont();
                    Font selectedFont = originalFont.deriveFont(originalFont.getStyle(), selectedFontSize);
                    textPane.setFont(selectedFont);
                }
            }
        });
        format.add(changeFontSize);

// 添加粗体子菜单项
        JMenuItem bold = new JMenuItem("粗体");
        bold.addActionListener(e -> {
            // 获取当前活动的JInternalFrame
            JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
            if (selectedFrame == null) {
                return;
            }
            // 获取JInternalFrame的JTextPane
            JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
            JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();

            // 设置为粗体
            Font font = textPane.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
            textPane.setFont(font.deriveFont(attributes));
        });
        format.add(bold);

// 添加斜体子菜单项
        JMenuItem italic = new JMenuItem("斜体");
        italic.addActionListener(e -> {
            // 获取当前活动的JInternalFrame
            JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
            if (selectedFrame == null) {
                return;
            }
            // 获取JInternalFrame的JTextPane
            JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
            JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();

            // 设置为斜体
            Font font = textPane.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
            textPane.setFont(font.deriveFont(attributes));
        });
        format.add(italic);

// 添加增加下划线子菜单项
        JMenuItem underline = new JMenuItem("增加下划线");
        underline.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // 获取当前活动的JInternalFrame
                JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
                if (selectedFrame == null) {
                    return;
                }
                // 获取JInternalFrame的JTextPane
                JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
                JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();

                // 增加下划线
                StyledDocument doc = textPane.getStyledDocument();
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                StyleConstants.setUnderline(attrs, true);
                doc.setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), attrs, false);
            }
        });
        format.add(underline);
// 统计选中字符数
        JMenuItem count2 = new JMenuItem("统计字符");
        count2.addActionListener(e -> {
            // 获取当前活动的JInternalFrame
            JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
            if (selectedFrame == null) {
                return;
            }
            // 获取JInternalFrame的JTextPane
            JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
            JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();
            // 获取选中的文本
            String selectedText = textPane.getSelectedText();
            // 如果没有选中文本，直接返回
            if (selectedText == null || selectedText.isEmpty()) {
                return;
            }
            // 统计字符数
            int h = 0,ll = 0,l = 0,n = 0,o = 0;//定义整型变量，用于统计字符数
            for(int i = 0;i<selectedText.length();i++){
                String s = selectedText.substring(i,i+1);
                if (s.matches("[\\u4e00-\\u9fa5]")) {//if语句的条件，判断是否为汉字
                    h++;//若为汉字则c1自增
                } else if(s.matches("[A-Z]")){//if语句的条件，判断是否为大写字母
                    ll++;//若为大写字母则c2自增
                } else if(s.matches("[a-z]")){//if语句的条件，判断是否为小写字母
                    l++;//若为小写字母则c3自增
                } else if(s.matches("[0-9]")){//if语句的条件，判断是否为数字
                    n++;//若为数字则c4自增
                } else {//否则可判断为其他字符
                    o++;//若为其他字符则c5自增
                }
            }
            int count21 = selectedText.length();
            JOptionPane.showMessageDialog(TextEditer_Wan.this, "字数统计：\n汉字："+h+"\n大写字母："+ll+"\n小写字母:"+l+"\n数字："+n+"\n其他字符："+o+"\n共计"+ count21);
        });
        format.add(count2);


// 添加居中对齐子菜单项
        JMenuItem alignCenter = new JMenuItem("居中");
        alignCenter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // 获取当前活动的JInternalFrame
                JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
                if (selectedFrame == null) {
                    return;
                }
                // 获取JInternalFrame的JTextPane
                JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
                JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();
                // 获取StyledDocument
                StyledDocument doc = textPane.getStyledDocument();
                // 创建一个居中对齐的样式
                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                // 设置段落样式
                doc.setParagraphAttributes(0, doc.getLength(), center, false);
            }
        });
        format.add(alignCenter);

// 添加左对齐子菜单项
        JMenuItem alignLeft = new JMenuItem("左对齐");
        alignLeft.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // 获取当前活动的JInternalFrame
                JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
                if (selectedFrame == null) {
                    return;
                }
                // 获取JInternalFrame的JTextPane
                JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
                JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();
                // 获取StyledDocument
                StyledDocument doc = textPane.getStyledDocument();
                // 创建一个左对齐的样式
                SimpleAttributeSet left = new SimpleAttributeSet();
                StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
                // 设置段落样式
                doc.setParagraphAttributes(0, doc.getLength(), left, false);
            }
        });
        format.add(alignLeft);

// 添加右对齐子菜单项
        JMenuItem alignRight = new JMenuItem("右对齐");
        alignRight.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // 获取当前活动的JInternalFrame
                JInternalFrame selectedFrame = desktopPane.getSelectedFrame();
                if (selectedFrame == null) {
                    return;
                }
                // 获取JInternalFrame的JTextPane
                JScrollPane scrollPane = (JScrollPane) selectedFrame.getContentPane().getComponent(0);
                JTextPane textPane = (JTextPane) scrollPane.getViewport().getView();
                // 获取StyledDocument
                StyledDocument doc = textPane.getStyledDocument();
                // 创建一个右对齐的样式
                SimpleAttributeSet right = new SimpleAttributeSet();
                StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
                // 设置段落样式
                doc.setParagraphAttributes(0, doc.getLength(), right, false);
            }
        });
        format.add(alignRight);

    }//构造方法结束

    public static void main(String args[])
    {
        TextEditer_Wan app = new TextEditer_Wan();

        app.setSize(800, 600);  //设置窗口大小,宽度600，高度400
        app.setLocation(200,200);  //设置窗口位置为距离屏幕左边水平方向200，上方垂直方向200
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //点击关闭按钮是直接退出
        app.setVisible(true);  //设置窗体可见
    }
}