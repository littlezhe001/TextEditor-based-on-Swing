import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TxtEditor {

    public static void main(String[] args) {
        new TxtEditor();
    }

    private JFrame frame;

    public TxtEditor() {
        frame = new JFrame("Txt Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
        JMenuItem newMenuItem = new JMenuItem("新建");
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Edit edit_area = new Edit("文档"+Edit.num,"请输入内容");
                Edit.num++;
                edit_area.setVisible(true);
            }
        });
        JMenuItem openMenuItem = new JMenuItem("打开");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Text_file f = new Text_file();
                String[] NameAndText = f.open();
                Edit file_open = new Edit(NameAndText[0], NameAndText[1]);
                file_open.setVisible(true);
            }
        });

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

}

