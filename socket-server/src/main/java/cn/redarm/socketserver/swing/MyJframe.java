package cn.redarm.socketserver.swing;

import cn.redarm.socketserver.comm.SocketComment;
import cn.redarm.socketserver.socket.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyJframe extends JFrame {

    // 标签
    private JLabel label1;
    private JLabel label2;

    private JLabel labelIP;

    // 按钮
    private JButton jButton1;
    private JButton jButton2;

    private JFileChooser jFileChooser;

    // Port
    private JTextField jTextField2;

    public static JTextArea jTextArea;
    private JScrollPane jScrollPane;

    public MyJframe() throws UnknownHostException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(700,350);

        setLayout(null);

        this.init();

        setVisible(true);
    }

    private void init() throws UnknownHostException {
        label1 = new JLabel("IP");
        label1.setBounds(20,20,30,20);
        add(label1);

        label2 = new JLabel("Port");
        label2.setBounds(20,70,30,20);
        add(label2);

        jTextField2 = new JTextField(5);
        jTextField2.setBounds(60,70,150,20);
        add(jTextField2);

        InetAddress address = InetAddress.getLocalHost();

        String ip = address.getHostAddress().toString();

        labelIP = new JLabel(ip);
        labelIP.setBounds(60,20,150,20);
        add(labelIP);

        jButton1 = new JButton("开始等待连接");
        jButton1.setBounds(50,200,100,20);
        jButton1.addActionListener(this::actionPerformed);
        add(jButton1);

        jButton2 = new JButton("接受文件地址");
        jButton2.setBounds(50,150,100,20);
        jButton2.addActionListener(this::actionPerformed);
        add(jButton2);

        jTextArea = new JTextArea();
        jScrollPane = new JScrollPane();
        jScrollPane.setBounds(230,20,400,200);
        jScrollPane.setViewportView(jTextArea);
        add(jScrollPane);

        jTextArea.setText("message: " + "\n\n");
    }

    public void actionPerformed(ActionEvent e){
        try {
            if (e.getSource() == jButton1){
                String port = String.valueOf(jTextField2.getText().trim());

                if (port == null || port.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Port格式错误，请重新输入！", "提示", JOptionPane.ERROR_MESSAGE);
                }

                int newPort = Integer.parseInt(port);

                if (newPort <=1024 || newPort >= 30000){
                    JOptionPane.showMessageDialog(this, "system port can not be use，请重新输入！", "提示", JOptionPane.ERROR_MESSAGE);
                }

                SocketComment.PORT = newPort;

                if (SocketComment.FILE_PATH == null || SocketComment.FILE_PATH.isEmpty()){
                    JOptionPane.showMessageDialog(this, "file path error，请重新输入！", "提示", JOptionPane.ERROR_MESSAGE);
                }

                if (Server.serverUp){
                    JOptionPane.showMessageDialog(this, "server is running！", "提示", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "server starts success！", "提示", JOptionPane.ERROR_MESSAGE);
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                Server.start();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }.start();
                }
            }

            if (e.getSource() == jButton2){

                jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jFileChooser.showDialog(new JLabel(),"this");

                File file = jFileChooser.getSelectedFile();

                SocketComment.FILE_PATH = file.getPath();
            }

        } catch (Exception ex){
            System.out.println(ex);
        }
    }


}
