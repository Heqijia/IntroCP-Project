package view;

import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    static JFrame fatherFrame;
    //    public final Dimension FRAME_SIZE ;
    private List<String> chessData=new ArrayList<>();
    private List<String> regretChessData;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private ChessColor currentColor;
    Image image;
    JLabel Label;
    MusicPlayer a=new MusicPlayer("musics/Nightbook.wav");
//    Chessboard chessboard;
//
//    public void PawnGetBoard(Chessboard chessboard){
//        this.chessboard=chessboard;
//    }
    public ChessGameFrame(int width, int height) {
        setTitle("国际象棋"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;//棋盘只有整个页面的4/5的大小

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        ImageIcon picture=new ImageIcon(".\\images\\背景图片.jpeg");
        addChessboard();
        Label=new JLabel(picture);
//        JLabel label=new JLabel(picture);        //add the picture to a label
        add(Label);
        //add the label to the frame
        setVisible(true);//set the window to visible


        a.play();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {//棋盘的初始化
        Chessboard chessboard ;
        chessboard=new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
        String pa="images/加载页面2.jpeg";
        String pa2="images/主题3.jpeg";
        //chessboard.changeChessBoard(pa,pa2);


        JLabel timeCounter;
        //TimeCounter time11=new TimeCounter();
        timeCounter = new JLabel("剩余时间：");
        timeCounter.setSize(160, 48);
        timeCounter.setLocation(HEIGTH, HEIGTH / 10 + 10);
        timeCounter.setFont(new Font("Rockwell", Font.BOLD, 24));
        chessboard.changeTime(timeCounter);
        add(timeCounter);
        chessboard.time3();


        /**
         * 在游戏面板中添加标签
         */
        JLabel statusLabel;
        statusLabel = new JLabel("        黑方回合");
        statusLabel.setSize(160, 48);

        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 24));
        chessboard.changeLabel(statusLabel);
        add(statusLabel);
       // add(new JPanel());

        /**
         * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
         */
        JButton button = new JButton("？");
       // button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "保存成功！"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 60);
        button.setSize(160, 48);
        button.setFont(new Font("Rockwell", Font.BOLD, 16));
        add(button);
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "嘟嘟嘟！");
//           chessData= chessboard.StoreGame();
//            File file = new File("ChessData.txt");
//            try {
//                FileOutputStream fos1=new FileOutputStream(file);
//                OutputStreamWriter dos1=new OutputStreamWriter(fos1);
//                dos1.write(String.valueOf(chessData));
//                dos1.close();
//            } catch (FileNotFoundException ex) {
//                ex.printStackTrace();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
        });

        JButton Load = new JButton("加载存档");
        Load.setLocation(HEIGTH, HEIGTH / 10 + 115);
        Load.setSize(160, 48);
        Load.setFont(new Font("Rockwell", Font.BOLD, 16));
        add(Load);

       Load.addActionListener(e -> {

           JFileChooser fileChooser = new JFileChooser();
           int option = fileChooser.showOpenDialog(null);
           if(option == JFileChooser.APPROVE_OPTION) {
               //File file = fileChooser.getSelectedFile();
               File f=fileChooser.getSelectedFile();
               String filename=f.getName();
               System.out.println(filename);
               if(filename.charAt(filename.length()-1)!='t'||
                       filename.charAt(filename.length()-2)!='x'||
                       filename.charAt(filename.length()-3)!='t'){
                   JOptionPane.showMessageDialog(this, "文件格式错误！");
               }
               Stream<String> lines1 = null;
               try {
                   lines1 = Files.lines(Paths.get(filename));
               }catch(FileNotFoundException ex){
               }
               catch (IOException ex) {
                   ex.printStackTrace();
               }
               String[] m = {""};
               lines1.forEach(ele -> {
                   //System.out.println(ele);
                   m[0] = m[0] +ele;
               });
               if(m[0].length()!=83){
                   JOptionPane.showMessageDialog(this, "棋盘并非8*8，请加载完毕后自动退出！");
               }
               chessData=new ArrayList<>();
               try {
                   chessData.add(m[0].substring(1, 9));
                   chessData.add(m[0].substring(11, 19));
                   chessData.add(m[0].substring(21, 29));
                   chessData.add(m[0].substring(31, 39));
                   chessData.add(m[0].substring(41, 49));
                   chessData.add(m[0].substring(51, 59));
                   chessData.add(m[0].substring(61, 69));
                   chessData.add(m[0].substring(71, 79));
                   chessData.add(m[0].substring(81, 82));
               }catch (IndexOutOfBoundsException ex){
                   JOptionPane.showMessageDialog(this, "棋盘并非8*8，请加载完毕后自动退出！");
               }
               chessboard.initializeChess();
               chessboard.loadGame(chessData);
               repaint();
               chessboard.time3();

               //               try {
//                   FileReader reader = new FileReader(file);
//                   System.out.println(String.valueOf(reader));
//               } catch (FileNotFoundException ex) {
//                   ex.printStackTrace();
//               }


           }



//           chessData= chessboard.StoreGame();
//           File file = new File("ChessData2.txt");
//           try {
//               FileOutputStream fos1=new FileOutputStream(file);
//               OutputStreamWriter dos1=new OutputStreamWriter(fos1);
//               dos1.write(String.valueOf(chessData));
//               dos1.close();
//           } catch (FileNotFoundException ex) {
//               ex.printStackTrace();
//           } catch (IOException ioException) {
//               ioException.printStackTrace();
//           }
        });



        JButton Reset = new JButton("重置");
        Reset.addActionListener((e) -> {
            int confirm=JOptionPane.showConfirmDialog(null,"是否重置","a",JOptionPane.YES_NO_OPTION);
            if(confirm==JOptionPane.YES_NO_OPTION){
                if(currentColor==ChessColor.WHITE){
                    chessboard.setCurrentColor(ChessColor.BLACK);
                }else {
                    chessboard.setCurrentColor(ChessColor.WHITE);
                }
                try {
                    Thread.currentThread().sleep(1000);
                }
                catch(Exception a){
                }

                chessboard.setCurrentColor(ChessColor.BLACK);
                chessboard.getChessComponents();

                //add(chessboard);
                chessboard.initializeChess();
                chessboard.time3();
                repaint();
            }
        });
        Reset.setLocation(HEIGTH, HEIGTH / 10 +170);
        Reset.setSize(160, 48);
        Reset.setFont(new Font("Rockwell", Font.BOLD, 16));
        add(Reset);

        {
            JButton Regret = new JButton("悔棋");
            Regret.setLocation(HEIGTH, HEIGTH / 10 + 225);
            Regret.setSize(160, 48);
            Regret.setFont(new Font("Rockwell", Font.BOLD, 16));
            add(Regret);

            Regret.addActionListener(e -> {
                if(currentColor==ChessColor.WHITE){
                    chessboard.setCurrentColor(ChessColor.BLACK);
                }else {
                    chessboard.setCurrentColor(ChessColor.WHITE);
                }
                try {
                    Thread.currentThread().sleep(1000);
                }
                catch(Exception a){
                }
                // System.out.println(chessboard.regretChessDataTure);
                //chessboard.swapColor();
                chessboard.time3();
                chessboard.loadGame(chessboard.regretChessDataTure);
                chessboard.repaint();

            });
        }

        JButton exit =new JButton("返回菜单");
        exit.setLocation(HEIGTH, HEIGTH / 10 + 280);
        exit.setSize(160, 48);
        exit.setFont(new Font("Rockwell", Font.BOLD, 16));
        add(exit);
        exit.addActionListener(e->{
            new Music_Click();
            new Music_PlayChess().stop();
            newGameOption.fatherFrame = this;
            newGameOption.main(null);
            this.setVisible(false);
            a.over();
        });

        JButton changeBackground=new JButton("保存游戏");
        changeBackground.setLocation(HEIGTH, HEIGTH / 10 + 335);
        changeBackground.setSize(160, 48);
        changeBackground.setFont(new Font("Rockwell", Font.BOLD, 16));
        add(changeBackground);
        changeBackground.addActionListener(a->{
            chessData= chessboard.StoreGame();
//创建文件选择器
            JFileChooser fileChooser = new JFileChooser();
//后缀名过滤器
            FileNameExtensionFilter filter = new FileNameExtensionFilter("标签文件(*.txt)", "txt");
            fileChooser.setFileFilter(filter);
// 在容器上打开文件选择器
            fileChooser.showSaveDialog(null);
            File f=fileChooser.getSelectedFile();
//字节输出流
            FileOutputStream fos = null;
            try {
                String fname = f.getName();//从文件名输入框中获取文件名
                //创建文件
                File file=new File(fileChooser.getCurrentDirectory()+"/"+fname+".txt");
                fos = new FileOutputStream(file);
                //写入文件操作
                String Datas = chessData.toString();
                fos.write(Datas.getBytes());
                fos.close();

            } catch (IOException e1) {
                System.err.println("IO异常");
                e1.printStackTrace();
            }



//            Object[] optionsBackground={"棋盘1","棋盘2","棋盘3"};
//            String BackGroundTheme=(String)JOptionPane.showInputDialog(null,"更换棋盘\n","更换",JOptionPane.PLAIN_MESSAGE,new ImageIcon("xx.png"),optionsBackground,"棋盘");
//            if (BackGroundTheme == "棋盘1") {
////                String path1="images/加载页面2.jpeg";
////                String path2="images/主题3.jpeg";
////                chessboard.changeChessBoard(path1,path2);
//                Color[] COLOR1={Color.WHITE, Color.RED};
//                chessboard.changeChessBoardColor(COLOR1);
//            }
        });

        JButton changeTheme=new JButton("更改背景");
        //JButton exit =new JButton("返回菜单");
        changeTheme.setLocation(HEIGTH, HEIGTH / 10 + 390);
        changeTheme.setSize(160, 48);
        changeTheme.setFont(new Font("Rockwell", Font.BOLD, 16));
        add(changeTheme);
        changeTheme.addActionListener(e->{
            Object[] options={"主题1","主题2","主题3"};
            String theme=(String)JOptionPane.showInputDialog(null,"更换主题\n","更换",JOptionPane.PLAIN_MESSAGE,new ImageIcon("xx.png"),options,"主题");
            if (theme == "主题1") {
                String path="images/背景图片.jpeg";
                changeTheme(path);
            }
            if(theme=="主题2"){
                String path="images/主题4.jpeg";
                changeTheme(path);
            }
            if(theme=="主题3"){
                String path="images/主题5.jpeg";
                changeTheme(path);
            }
        });
    }
    public void changeTheme(String path){
        remove(Label);
        ImageIcon theme1=new ImageIcon(path);
        Image a=theme1.getImage();
        Image dos=a.getScaledInstance(WIDTH,HEIGTH,Image.SCALE_FAST);
        ImageIcon Ture=new ImageIcon(dos);
        Label=new JLabel(Ture);
        Label.setBounds(0,0,getWidth(),getHeight());
        add(Label);
        Label.repaint();
    }

    public static void main(String[] args){
        ChessGameFrame mainFrame = new ChessGameFrame(800, 608);
        mainFrame.setVisible(true);
    }
}
