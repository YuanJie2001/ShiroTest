package com.vector.springbootshiro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author WangJiaHui
 * @description: 开源社区 gitee  https://gitee.com/p-santiago/banker-algorithm-/tree/master
 * @ClassName BankerAlgorithm
 * @date 2022/4/25 16:03
 */
public class BankerAlgorithm {
    JFrame jf = new JFrame("银行家算法演示程序");	//定义一个窗口，名叫银行家算法演示程序
    JPanel jp1 = new JPanel();	//定义一个面板容器
    JTextField jtf_ID, jtf_A, jtf_B, jtf_C;	//定义文本框
    JComboBox<String> jcb1;	//定义下拉列表
    JButton jb_QueDing, jb_LiZi, jb_ChongZhi, jb_CheXiao,jb_JianCha;	//定义按钮
    JTextArea jta1;	//定义文本区域
    private int numResource = 3;//资源种类数
    private int numProcess = 5;//进程的个数
    private int p = 0; // 当前处理的进程

    //数据结构
    int Available[]= new int[numResource]; 	//系统中的空闲资源数
    int Max[][]= new int[numProcess][numResource]; 	//进程对资源的最大需求资源
    int Allocation[][]= new int[numProcess][numResource]; 	//进程对资源的占有量
    int Need[][]= new int[numProcess][numResource]; 	//进程对资源的需求量
    int Request[][]= new int[numProcess][numResource]; 	//进程对资源的申请量
    int Work[] = new int[numResource]; 	//用于安全性算法中临时存储Available的值

    // 界面展示
    public void ShowFrame(){
        jf.setSize(530, 350);   //大小
        jf.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        //jf.setResizable(false);//不可拖动
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	// 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）

        // 界面组件的初始化
        jtf_ID = new JTextField(3);
        jtf_A = new JTextField(3);
        jtf_B = new JTextField(3);
        jtf_C = new JTextField(3);
        String list[] = new String[]{"Max","Allocation","Available","Request"};
        jcb1 = new JComboBox<String>(list);	//下拉列表中的 内容是字符串数字s中的元素
        jb_QueDing = new JButton("确定");
        jb_LiZi = new JButton("例子");
        jb_ChongZhi = new JButton("重置");
        jb_CheXiao = new JButton("撤销");
        jb_JianCha=new JButton("检查");


        // 界面底部布局，水平排列
        jp1.add(jcb1);
        jp1.add(new JLabel("ID:"));
        jp1.add(jtf_ID);
        jp1.add(new JLabel("A:"));
        jp1.add(jtf_A);
        jp1.add(new JLabel("B:"));
        jp1.add(jtf_B);
        jp1.add(new JLabel("C:"));
        jp1.add(jtf_C);
        jp1.add(jb_QueDing);

        // 确定按键的功能设定
        jb_QueDing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jb_QueDing) {
                    //设置已占用资源Allocation数组
                    if(jcb1.getSelectedItem() == "Allocation"){		//下拉列表选择Allocation时
                        try{
                            p = Integer.parseInt(jtf_ID.getText());
                            //判断文本框内输入的数据是否符合要求
                            if (p > 4) {
                                JOptionPane.showMessageDialog(jf, "进程ID在0-4之间！","提示",JOptionPane.WARNING_MESSAGE);
                                jtf_ID.setText("");
                                return;
                            }
                            if (Integer.parseInt(jtf_A.getText()) < 0 || Integer.parseInt(jtf_B.getText()) < 0 || Integer.parseInt(jtf_C.getText()) < 0) {
                                JOptionPane.showMessageDialog(jf, "资源数不能小于0！", "提示", JOptionPane.WARNING_MESSAGE);
                                jtf_A.setText("");
                                jtf_B.setText("");
                                jtf_C.setText("");
                                return;
                            }
                            if(Integer.parseInt(jtf_A.getText()) > Max[p][0] || Integer.parseInt(jtf_B.getText()) > Max[p][1] || Integer.parseInt(jtf_C.getText()) > Max[p][2]){
                                JOptionPane.showMessageDialog(jf, "占用资源大于最大需求资源(未定义最大需求资源)", "提示", JOptionPane.WARNING_MESSAGE);
                                jtf_ID.setText("");
                                jtf_A.setText("");
                                jtf_B.setText("");
                                jtf_C.setText("");
                                return;
                            }
                            //将输入的参数传值
                            Allocation[p][0] = Integer.parseInt(jtf_A.getText());
                            Need[p][0] = Max[p][0] - Allocation[p][0];
                            Allocation[p][1] = Integer.parseInt(jtf_B.getText());
                            Need[p][1] = Max[p][1] - Allocation[p][1];
                            Allocation[p][2] = Integer.parseInt(jtf_C.getText());
                            Need[p][2] = Max[p][2] - Allocation[p][2];
                        } catch(Exception d) {
                            JOptionPane.showMessageDialog(jf, "输入有误！请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
                            ShowData();
                            return;
                        }
                        ShowData();
                        jta1.append("\n\n 已占有资源设置成功！");
                    }

                    //设置进程的资源最大需求量Max数组
                    else if (jcb1.getSelectedItem() == "Max") {		//下拉列表选择Max时
                        try {
                            p = Integer.parseInt(jtf_ID.getText());
                            if (p > 4) {
                                JOptionPane.showMessageDialog(jf, "进程ID在0-4之间！", "提示", JOptionPane.WARNING_MESSAGE);
                                jtf_ID.setText("");
                                return;
                            }
                            if (Integer.parseInt(jtf_A.getText()) < 0 || Integer.parseInt(jtf_B.getText()) < 0 || Integer.parseInt(jtf_C.getText()) < 0) {
                                JOptionPane.showMessageDialog(jf, "资源数不能小于0！", "提示", JOptionPane.WARNING_MESSAGE);
                                jtf_A.setText("");
                                jtf_B.setText("");
                                jtf_C.setText("");
                                return;
                            }
                            if(Integer.parseInt(jtf_A.getText()) < Allocation[p][0] || Integer.parseInt(jtf_B.getText()) < Allocation[p][1] || Integer.parseInt(jtf_C.getText()) < Allocation[p][2]){
                                JOptionPane.showMessageDialog(jf, "最大需求资源小于已占有资源！", "提示", JOptionPane.WARNING_MESSAGE);
                                jtf_ID.setText("");
                                jtf_A.setText("");
                                jtf_B.setText("");
                                jtf_C.setText("");
                                return;
                            }
                            Max[p][0] = Integer.parseInt(jtf_A.getText());
                            Need[p][0] = Max[p][0] - Allocation[p][0];
                            Max[p][1] = Integer.parseInt(jtf_B.getText());
                            Need[p][1] = Max[p][1] - Allocation[p][1];
                            Max[p][2] = Integer.parseInt(jtf_C.getText());
                            Need[p][2] = Max[p][2] - Allocation[p][2];
                        } catch(Exception d) {
                            JOptionPane.showMessageDialog(jf, "输入有误！请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
                            ShowData();
                            return;
                        }
                        ShowData();
                        jta1.append("\n\n  最大需求设置成功！");
                    }
                    //设置系统空闲资源量Available数组
                    else if (jcb1.getSelectedItem()=="Available"){
                        try{
                            if (Integer.parseInt(jtf_A.getText()) < 0 || Integer.parseInt(jtf_B.getText()) < 0 || Integer.parseInt(jtf_C.getText()) < 0) {
                                JOptionPane.showMessageDialog(jf, "资源数不能小于0！", "提示", JOptionPane.WARNING_MESSAGE);
                                jtf_A.setText("");
                                jtf_B.setText("");
                                jtf_C.setText("");
                                return;
                            }
                            Available[0] = Integer.parseInt(jtf_A.getText());
                            Available[1] = Integer.parseInt(jtf_B.getText());
                            Available[2] = Integer.parseInt(jtf_C.getText());
                        }catch(Exception d) {
                            JOptionPane.showMessageDialog(jf, "您输入有误！请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
                            ShowData();
                            return;
                        }
                        ShowData();
                        jta1.append("\n\n    可用资源设置成功！");
                    }

                    //设置进程的资源请求量Request数组
                    else if(jcb1.getSelectedItem() == "Request"){
                        try{
                            p = Integer.parseInt(jtf_ID.getText());
                            if (p > 4) {
                                JOptionPane.showMessageDialog(jf, "进程ID在0-4之间！", "提示", JOptionPane.WARNING_MESSAGE);
                                jtf_ID.setText("");
                                return;
                            }
                            if (Integer.parseInt(jtf_A.getText()) < 0 || Integer.parseInt(jtf_B.getText()) < 0 || Integer.parseInt(jtf_C.getText()) < 0) {
                                JOptionPane.showMessageDialog(jf, "资源数不能小于0！", "提示", JOptionPane.WARNING_MESSAGE);
                                jtf_A.setText("");
                                jtf_B.setText("");
                                jtf_C.setText("");
                                return;
                            }
                            Request[p][0] = Integer.parseInt(jtf_A.getText());
                            Request[p][1] = Integer.parseInt(jtf_B.getText());
                            Request[p][2] = Integer.parseInt(jtf_C.getText());
                        }catch(Exception d) {
                            JOptionPane.showMessageDialog(jf, "您输入有误！请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
                            ShowData();
                            return;
                        }

                        int order[] = new int[numProcess];
                        if(banker(p,order)){
                            ShowData();
                            jta1.append("\n\n  通过安全性检查！安全序列为：");
                            //打印安全序列
                            for(int i = 0; i < order.length; i++) {
                                jta1.append("P"+order[i]+"  ");
                            }
                            JOptionPane.showMessageDialog(jf, "申请成功，资源已经分配~~~","提示",JOptionPane.INFORMATION_MESSAGE);
                        }else {
                            ShowData();
                            for (int i = 0; i < numResource; i++) {
                                Request[p][i] = 0;
                            }
                            JOptionPane.showMessageDialog(jf, "找不到安全序列！   不批准请求！", "提示", JOptionPane.WARNING_MESSAGE);
                        }
                    }else{
                        ShowData();
                        jta1.append("\n\n  系统资源不足！");
                    }
                }
            }
        });
        jp1.setBackground(new java.awt.Color(128,255,128));

        // 右边按钮布局，竖直排列
        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalStrut(60)); // 添加空白
        vBox.add(jb_LiZi);
        vBox.add(Box.createVerticalStrut(20)); // 添加空白
        vBox.add(jb_ChongZhi);
        vBox.add(Box.createVerticalStrut(20)); // 添加空白
        vBox.add(jb_CheXiao);
        vBox.add(Box.createVerticalStrut(20)); // 添加空白
        vBox.add(jb_JianCha);
        vBox.add(Box.createVerticalStrut(20)); // 添加空白

        // 例子按键功能设定
        jb_LiZi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jb_LiZi){
                    init();
                    ShowData();
                }
            }
        });

        // 清零按键功能设定
        jb_ChongZhi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jb_ChongZhi){
                    reset();
                    ShowData();
                }
            }
        });

        // 撤销按键功能设定
        jb_CheXiao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jb_CheXiao){
                    if(revocation())
                        JOptionPane.showMessageDialog(jf, "撤销成功！","提示", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(jf, "无撤销内容！","提示", JOptionPane.WARNING_MESSAGE);
                    ShowData();
                }
            }
        });
        // 检查按键功能设定
        jb_JianCha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == jb_JianCha){
                    int order[] = new int[numProcess];
                    if(isSafe(order))
                        JOptionPane.showMessageDialog(jf, "系统安全，存在安全序列","提示", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(jf, "系统不安全，没有安全序列","警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        jf.add(jp1,"South"); // 布局底部
        jf.add(vBox,"After");// 右边

        // 展示中间的文本
        jta1= new JTextArea();
        ShowData();
        jta1.setLineWrap(true);
        jta1.setBackground(Color.white);
        jta1.setEditable(false);
        jf.add(jta1,BorderLayout.CENTER);

        // 结果展示
        jf.setVisible(true);
    }
    // 展示区域jta1中的数据
    public void ShowData() {
        jta1.setText("                            Max                        Allocation                      Need                    Available\n");
        jta1.append("\n" + "    资源         " + " A       B       C          " + "    A       B       C          " +
                "  A       B       C          " + "   A       B       C");
        jta1.append("\n    进程\n      P0"  + "           " +
                + Max[0][0] + "       " + Max[0][1] + "       "
                +Max[0][2] + "          " +
                "     " + Allocation[0][0] + "       " + Allocation[0][1]
                + "       " + Allocation[0][2] + "    " +
                "          " + Need[0][0] + "       " + Need[0][1]
                + "       " + Need[0][2] + "       " +
                "       " + Available[0] + "       " + Available[1] +
                "       " + Available[2]);
        for (int i = 1; i < 5; i++) {
            jta1.append("\n\n      P" + i + "      " +
                    "     " + Max[i][0] + "       " + Max[i][1] + "       " + Max[i][2] + "          " +
                    "     " + Allocation[i][0] + "       " + Allocation[i][1]
                    + "       " + Allocation[i][2] + "    " +
                    "          " + Need[i][0] + "       " + Need[i][1]
                    + "       " + Need[i][2] + "    ");
        }
        jtf_ID.setText("");
        jtf_A.setText("");
        jtf_B.setText("");
        jtf_C.setText("");

    }
    // 算法核心流程
    public boolean banker(int process,int order[]){
        // 判断进程申请的资源数量是否符合要求：
        //request<need
        //request<Available
        for(int i = 0; i < numResource; i++){
            if(Request[process][i] > Need[process][i]) {
                System.out.println("错误：进程P" + process + "的申请，超出最大需求量Need。");
                return false;
            }
            else if(Request[process][i] > Available[i]){
                System.out.println("错误：当前没有足够的资源可申请，进程P" + process + "需等待。");
                return false;
            }
        }
        //若满足上述要求，进行预分配
        // 更新Allocation、Available、Need数组
        for (int i = 0; i < numResource; i++) {
            Allocation[process][i] += Request[process][i];
            Available[i] -= Request[process][i];
            Need[process][i] -= Request[process][i];
        }
        // 进行安全性检查
        if(isSafe(order)){//安全，请求通过
            return true;
        }
        // 不安全，返回到预分配前的状态
        for (int i = 0; i < numResource; i++) {
            Allocation[process][i] -= Request[process][i];
            Available[i] += Request[process][i];
            Need[process][i] += Request[process][i];
        }
        return false;
    }



    // 检查该请求是否安全
    public boolean isSafe(int order[]){
        int count = 0; // 安全的进程数量
        int k = 0;
        int circle = 0; // 循环次数
        boolean Finish[]= new boolean[numProcess]; // 进程是否进入安全序列
        //步骤1 将Finish的元素初始化为false,将Work=Available
        for (int i = 0; i < numResource; i++) {
            Finish[i] = false;
        }
        copyWork();//Work=Available
        while(true){
            //步骤2 从进程集合寻找符合下列条件的进程
            //Finish[i] =  false;
            //Need[i,j] <= Work[j];
            for (int i = 0; i < numProcess; i++) {
                if(Finish[i] == false){
                    for (k = 0; k < numResource; k++) {
                        if(Work[k] < Need[i][k])
                            break;
                    }
                    // 步骤3
                    // 如果空闲资源满足进程所需要的资源，将进程编号存进安全系列order中，并将其Finish值置位true
                    if(k == numResource){
                        order[count++] = i;
                        Finish[i] = true;
                        // 释放资源，将资源还给系统。
                        for (int j = 0; j < numResource; j++) {
                            Work[j] += Allocation[i][j];
                        }
                    }
                }
            }
            circle++;//每完整循环一次，circle加1
            //步骤4 判断循环次数circle是否和进程数相等
            //若相等，存在安全序列
            if(count == numProcess){
                System.out.print("存在一个安全序列：");
                //输出安全序列
                for (int i = 0; i < numProcess; i++){
                    System.out.print("P" + order[i] + " ");
                }
                System.out.println("故当前可分配！");
                return true;
            }
            // 如果进入安全序列的进程数小于循环次数，说明不存在能安全完成的进程
            if(count < circle){
                System.out.println("警告：申请使得系统处于不安全状态，申请失败。");
                return false;
            }
        }
    }

    // 使用工作向量
    public void copyWork(){
        for (int i = 0; i < numResource; i++) {
            Work[i] = Available[i];
        }
    }

    // 初始化一个例子
    public void init(){
        numProcess = 5;
        numResource = 3;
        Available[0] = 3; Available[1] = 3; Available[2] = 2;
        Max[0][0] = 7; Max[0][1] = 5; Max[0][2] = 3;
        Max[1][0] = 3; Max[1][1] = 2; Max[1][2] = 2;
        Max[2][0] = 9; Max[2][1] = 0; Max[2][2] = 2;
        Max[3][0] = 2; Max[3][1] = 2; Max[3][2] = 2;
        Max[4][0] = 4; Max[4][1] = 3; Max[4][2] = 3;
        Allocation[0][0] = 0; Allocation[0][1] = 1; Allocation[0][2] = 0;
        Allocation[1][0] = 2; Allocation[1][1] = 0; Allocation[1][2] = 0;
        Allocation[2][0] = 3; Allocation[2][1] = 0; Allocation[2][2] = 2;
        Allocation[3][0] = 2; Allocation[3][1] = 1; Allocation[3][2] = 1;
        Allocation[4][0] = 0; Allocation[4][1] = 0; Allocation[4][2] = 2;
        for (int i = 0; i < numProcess; i++) {
            for (int j = 0; j < numResource; j++) {
                Need[i][j] = Max[i][j] - Allocation[i][j];
            }
        }
    }

    // 重置功能
    public void reset(){
        for (int i = 0; i < numProcess; i++) {
            for (int j = 0; j < numResource; j++) {
                Max[i][j] = 0;
                Allocation[i][j] = 0;
                Need[i][j] = 0;
                Available[j] = 0;
            }
        }
    }
    // 撤回功能
    public boolean revocation(){
        if(Request[p][0] == 0 && Request[p][1] == 0 && Request[p][2] == 0) {
            return false;
        }
        for (int i = 0; i < numResource; i++) {
            Allocation[p][i] -= Request[p][i];
            Need[p][i] = Max[p][i] - Allocation[p][i];
            Available[i] += Request[p][i];
            Request[p][i] = 0;
        }
        return true;
    }

    public static void main(String[] args) {
        BankerAlgorithm B = new BankerAlgorithm();
        B.ShowFrame();
    }
}
