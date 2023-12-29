import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;


public class Text_file {
    public String[] open (){
        // 创建一个 JFileChooser 对象
        JFileChooser fileChooser = new JFileChooser();
        // 设置 JFileChooser 对象的属性
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件", "txt"));

        // 显示 JFileChooser 对象
        int result = fileChooser.showOpenDialog(null);
        String[] res_list = new String[2];
        // 如果用户选择了文件
        if (result == JFileChooser.APPROVE_OPTION) {
            // 获取用户选择的文件
            java.io.File file = fileChooser.getSelectedFile();
            String name = file.getName();
            res_list[0] = name;
            // 读取文件
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String res = "";
                String line;
                while ((line = reader.readLine()) != null) {
                    // 显示文件内容
                    res += line;
                }
                res_list[1] = res;
                return res_list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res_list;
    }

    public void SaveText(JTextPane textPane){
        JFileChooser fileChooser = new JFileChooser();
        // 设置文件选择器的默认路径
        fileChooser.setCurrentDirectory(new File("."));
        // 显示文件选择器
        int result = fileChooser.showSaveDialog(null);
        // 如果用户选择了文件
        if (result == JFileChooser.APPROVE_OPTION) {
            // 获取用户选择的文件
            File file = fileChooser.getSelectedFile();
            // 将文本写入文件
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(textPane.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
