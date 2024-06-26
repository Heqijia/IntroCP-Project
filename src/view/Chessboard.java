package view;

import controller.ClickController;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.BLACK;
   // private  int a=0;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private JLabel jLabel;
    private JLabel jLabelTime;
    private String Image1;
    private String Image2;
    public List<String> regretChessDataHelp=new ArrayList<String >();
    public List<String> regretChessDataTure;
    private Color[] ChessBoardColor;
//    java.util.Timer timer =new Timer();

//    public Chessboard(List<String> regretChessDataTure) {
//       regretChessDataTure=this.regretChessDataTure;
//    }

    public void PawnBoard( List<String> regretChessDataTure){
        this.regretChessDataTure=regretChessDataTure;
    }

    public List<String> getRegretChessDataTure() {
        return regretChessDataTure;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        //System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.(Rook)
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        //Bishop
        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,CHESSBOARD_SIZE-3,ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-3,ChessColor.WHITE);

//        //knight
        initKnightOnBoard(0,1,ChessColor.BLACK);
        initKnightOnBoard(0,CHESSBOARD_SIZE-2,ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE-1,1,ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-2,ChessColor.WHITE);

        //king
        initKingOnBoard(0,4,ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE-1,4,ChessColor.WHITE);

//        queen

        initQueenOnBoard(0,3,ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE-1,3,ChessColor.WHITE);

        //pawn
        for (int i=0;i<8;i++){
            initPawnOnBoard(1,i,ChessColor.BLACK);
            initPawnOnBoard(CHESSBOARD_SIZE-2,i,ChessColor.WHITE);
        }
        regretChessDataTure=StoreGame();
        regretChessDataHelp=StoreGame();
    }

    public void initializeChess(){

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.(Rook)
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        //Bishop
        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,CHESSBOARD_SIZE-3,ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-3,ChessColor.WHITE);

//        //knight
        initKnightOnBoard(0,1,ChessColor.BLACK);
        initKnightOnBoard(0,CHESSBOARD_SIZE-2,ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE-1,1,ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-2,ChessColor.WHITE);

        //king
        initKingOnBoard(0,4,ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE-1,4,ChessColor.WHITE);

        //queen
        initQueenOnBoard(0,3,ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE-1,3,ChessColor.WHITE);
//        private void initQueenOnBoard(int row, int col, ChessColor color) {
//            ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(0, 3), calculatePoint(0, 3), color, clickController, CHESS_SIZE);
//            chessComponent.setVisible(true);
//            putChessOnBoard(chessComponent);
//        }
//

        //Pawn
        for(int i=0;i<8;i++){
            initPawnOnBoard(1,i,ChessColor.BLACK);
            initPawnOnBoard(6,i,ChessColor.WHITE);
        }
        jLabel.setText("        黑方回合");
        regretChessDataTure=StoreGame();
        regretChessDataHelp=StoreGame();
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);

    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {

        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));

        }

        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        //读取文档数据
        File file = new File("regretChessDataTure.txt");
        try {
            FileOutputStream fos1=new FileOutputStream(file);
            OutputStreamWriter dos1=new OutputStreamWriter(fos1);
            dos1.write(String.valueOf(regretChessDataTure));
            dos1.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        //System.out.println(regretChessDataTure+"++++++++=================");
        chess1.repaint();
        chess2.repaint();
        regretChessDataHelp=StoreGame();



        File file1 = new File("regretChessDataHelp.txt");
           try {
               FileOutputStream fos1=new FileOutputStream(file1);
               OutputStreamWriter dos1=new OutputStreamWriter(fos1);
               dos1.write(String.valueOf(regretChessDataHelp));
               dos1.close();
           } catch (FileNotFoundException ex) {
               ex.printStackTrace();
           } catch (IOException ioException) {
               ioException.printStackTrace();
           }


        if (regretChessDataHelp.get(7).contains("P")){
            for(int j=0;j<8;j++) {
                if (regretChessDataHelp.get(7).charAt(j) == 'P') {
                    //System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    int a = j;
                    remove(chessComponents[7][a]);
                    Object[] options = {"骑士", "皇后", "大象", "战车"};
                    int op = JOptionPane.showOptionDialog(null, "请选择升级成：", "升级", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (op == 0) {
                        ChessComponent N = new KnightChessComponent(new ChessboardPoint(7, a), calculatePoint(7, a), ChessColor.BLACK, clickController, CHESS_SIZE);
                        N.setVisible(true);
                        putChessOnBoard(N);
                    }
                    if (op == 1) {
                        ChessComponent Q = new QueenChessComponent(new ChessboardPoint(7, a), calculatePoint(7, a), ChessColor.BLACK, clickController, CHESS_SIZE);
                        Q.setVisible(true);
                        putChessOnBoard(Q);
                    }
                    if (op == 2) {
                        ChessComponent B = new BishopChessComponent(new ChessboardPoint(7, a), calculatePoint(7, a), ChessColor.BLACK, clickController, CHESS_SIZE);
                        B.setVisible(true);
                        putChessOnBoard(B);
                    }
                    if (op == 3) {
                        ChessComponent R = new RookChessComponent(new ChessboardPoint(7, a), calculatePoint(7, a), ChessColor.BLACK, clickController, CHESS_SIZE);
                        R.setVisible(true);
                        putChessOnBoard(R);
                    }
                }
            }
        }
        if (regretChessDataHelp.get(0).contains("p")){
            for(int j=0;j<8;j++) {
                if (regretChessDataHelp.get(0).charAt(j) == 'p') {
                    int a=j;
                    remove(chessComponents[0][a]);
                    Object[] options = {"骑士", "皇后", "大象", "战车"};
                    int op = JOptionPane.showOptionDialog(null, "请选择升级成：", "升级", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (op == 0) {
                        ChessComponent n = new KnightChessComponent(new ChessboardPoint(0, a), calculatePoint(0, a), ChessColor.WHITE, clickController, CHESS_SIZE);
                        n.setVisible(true);
                        putChessOnBoard(n);
                    }
                    if (op == 1) {
                        ChessComponent q = new QueenChessComponent(new ChessboardPoint(0, a), calculatePoint(0, a), ChessColor.WHITE, clickController, CHESS_SIZE);
                        q.setVisible(true);
                        putChessOnBoard(q);
                    }
                    if (op == 2) {
                        ChessComponent b = new BishopChessComponent(new ChessboardPoint(0, a), calculatePoint(0, a), ChessColor.WHITE, clickController, CHESS_SIZE);
                        b.setVisible(true);
                        putChessOnBoard(b);
                    }
                    if (op == 3) {
                        ChessComponent r = new RookChessComponent(new ChessboardPoint(0, a), calculatePoint(0, a), ChessColor.WHITE, clickController, CHESS_SIZE);
                        r.setVisible(true);
                        putChessOnBoard(r);
                    }

                }
            }
        }

//        if (x2 == 2 && y2 == y1 - 1 && chessComponents[x1][y2].getName() == "P" &&
//                chessComponents[x2][y2] instanceof EmptySlotComponent &&
//                (this.getRegretChessDataTure().get(1)).charAt(y2) == 'P') {
            for (int j=0;j<8;j++){
                if((regretChessDataTure.get(2).charAt(j)=='_')&&
                        (regretChessDataTure.get(3).charAt(j)=='P')&&
                        (regretChessDataHelp.get(2).charAt(j)=='p')&&
                        (regretChessDataHelp.get(3).charAt(j)=='P')){
                    remove(chessComponents[3][j]);
                    //changeChessBoard(path1,path2);

                    //add(new EmptySlotComponent(new ChessboardPoint(3,j),calculatePoint(3,j),clickController,CHESS_SIZE));
                    ChessComponent _=new EmptySlotComponent(new ChessboardPoint(3,j),calculatePoint(3,j),clickController,CHESS_SIZE);
                    _.setVisible(true);
                    putChessOnBoard(_);
                    //changeChessBoard(path1,path2);
                }

            }
        for (int j=0;j<8;j++){
            if((regretChessDataTure.get(5).charAt(j)=='_')&&
                    (regretChessDataTure.get(4).charAt(j)=='p')&&
                    (regretChessDataHelp.get(5).charAt(j)=='P')&&
                    (regretChessDataHelp.get(4).charAt(j)=='p')){
                remove(chessComponents[4][j]);
                //changeChessBoard(path1,path2);
                ChessComponent _=new EmptySlotComponent(new ChessboardPoint(4,j),calculatePoint(4,j),clickController,CHESS_SIZE);
                _.setVisible(true);
                putChessOnBoard(_);
                //changeChessBoard(path1,path2);
            }
        }


        int K=-1;int k=-1;
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if (chessComponents[i][j].getName()=="K"){
                    K=1;
                }if (chessComponents[i][j].getName()=="k"){
                    k=1;
                }
            }
        }
        if (K!=1){
            JOptionPane.showMessageDialog(this, "游戏结束，恭喜白方获得胜利！");
        }if (k!=1){
            JOptionPane.showMessageDialog(this, "游戏结束，恭喜黑方获得胜利！");
        }



        if(jiangsi(chessComponents)){
            if(currentColor==ChessColor.BLACK){
                JOptionPane.showMessageDialog(this, "游戏结束，恭喜白方获得胜利！");
            }else {
                JOptionPane.showMessageDialog(this, "游戏结束，恭喜黑方获得胜利！");
            }
        }
        if(jiangsiCase2(chessComponents)){
            if(currentColor==ChessColor.BLACK){
                JOptionPane.showMessageDialog(this, "游戏结束，恭喜黑方获得胜利！");
            }else {
                JOptionPane.showMessageDialog(this, "游戏结束，恭喜白方获得胜利！");
            }
        }

//        changeChessBoard(path1,path2);


    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        if (currentColor==ChessColor.BLACK){
            jLabel.setText("        黑方回合");
        }else {
            jLabel.setText("        白方回合");
        }
        regretChessDataTure=regretChessDataHelp;
        regretChessDataHelp=StoreGame();
        //getTime();
        time3();

    }

    public void setRegretChessDataTure(List<String> regretChessDataTure) {
        this.regretChessDataTure = regretChessDataTure;
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    //在这个方法中，实现棋盘的信息输入，载入已生成的棋盘
    public void loadGame(List<String> chessData) {
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                switch (chessData.get(i).charAt(j)) {
                    case 'R':ChessComponent R=new RookChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.BLACK,clickController,CHESS_SIZE);
                        R.setVisible(true);
                        putChessOnBoard(R);
                        break;
                    case 'N':ChessComponent N=new KnightChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.BLACK,clickController,CHESS_SIZE);
                        N.setVisible(true);
                        putChessOnBoard(N);
                        break;
                    case 'B':ChessComponent B=new BishopChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.BLACK,clickController,CHESS_SIZE);
                        B.setVisible(true);
                        putChessOnBoard(B);
                        break;
                    case 'Q':ChessComponent Q=new QueenChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.BLACK,clickController,CHESS_SIZE);
                        Q.setVisible(true);
                        putChessOnBoard(Q);
                        break;
                    case 'K':ChessComponent K=new KingChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.BLACK,clickController,CHESS_SIZE);
                        K.setVisible(true);
                        putChessOnBoard(K);
                        break;
                    case 'P':ChessComponent P=new PawnChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.BLACK,clickController,CHESS_SIZE);
                        P.setVisible(true);
                        putChessOnBoard(P);
                        break;

                    case '_':ChessComponent _=new EmptySlotComponent(new ChessboardPoint(i,j),calculatePoint(i,j),clickController,CHESS_SIZE);
                        _.setVisible(true);
                        putChessOnBoard(_);
                        break;

                    case 'r':ChessComponent r=new RookChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.WHITE,clickController,CHESS_SIZE);
                        r.setVisible(true);
                        putChessOnBoard(r);
                        break;
                    case 'n':ChessComponent n=new KnightChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.WHITE,clickController,CHESS_SIZE);
                        n.setVisible(true);
                        putChessOnBoard(n);
                        break;
                    case 'b':ChessComponent b=new BishopChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.WHITE,clickController,CHESS_SIZE);
                        b.setVisible(true);
                        putChessOnBoard(b);
                        break;
                    case 'q':ChessComponent q=new QueenChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.WHITE,clickController,CHESS_SIZE);
                        q.setVisible(true);
                        putChessOnBoard(q);
                        break;
                    case 'k':ChessComponent k=new KingChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.WHITE,clickController,CHESS_SIZE);
                        k.setVisible(true);
                        putChessOnBoard(k);
                        break;
                    case 'p':ChessComponent p=new PawnChessComponent(new ChessboardPoint(i,j),calculatePoint(i,j),ChessColor.WHITE,clickController,CHESS_SIZE);
                        p.setVisible(true);
                        putChessOnBoard(p);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "棋子并非六种之一，棋子并非黑白棋子，请加载完成后自动退出！");
                        break;
                }
                chessComponents[i][j].setChessComponents(chessComponents);
            }
        }
        //System.out.println();
        if(chessData.get(8).equals("w")){
            currentColor=ChessColor.WHITE;
            jLabel.setText("        白方回合");
        }else if(chessData.get(8).equals("b")){
            currentColor=ChessColor.BLACK;
            jLabel.setText("        黑方回合");
        }else{
            JOptionPane.showMessageDialog(this, "缺少行棋子方或行棋方错误，请加载完成后自动退出！");
        }
        //chessData.forEach(System.out::println);
    }

    public List<String> StoreGame(){
        List<String> result=new ArrayList<>();
        String help="";
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                help=help+chessComponents[i][j].getName();
            }
            result.add(help);
            help="";
        }
        if(currentColor==ChessColor.WHITE) {
            result.add("w");
        }else {
            result.add("b");
        }
        //System.out.println(result);
        return result;
    }

    public void changeLabel(JLabel jLabel){
        this.jLabel=jLabel;
    }
//   // public void changeTime(JLabel jLabelTime){
//        this.jLabelTime=jLabelTime;
//    }


    public boolean jiangsi(ChessComponent[][] chessComponents) {
        int x0 = 0;
        int y0 = 0;
        int judge1 = -1;
        int judge2 = -1;
        int judge4 = 0;
        int counter = 0;
        int judge3 = 0;
        //鎵惧埌瀵规墜鐨勭帇鐨勪綅缃?x0,y0)锛屽垽鏂鎵嬫槸鍚﹁緭妫嬫槸鍦ㄦ垜涓嬪畬妫嬩箣鍚庣灛闂村垽鏂紙鍘熷厛杩樻病鏈塻wap chesscomponent锛屾墍浠ュ垽鏂篃鏄け鏁堢殑銆?
        //鎵惧埌鐜嬬殑浣嶇疆锛?
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == currentColor && chessComponents[i][j].getChessColor() != null &&
                        (chessComponents[i][j].getName() == "K" || chessComponents[i][j].getName() == "k")) {
                    x0 = i;
                    y0 = j;
                }
            }
        }
        //鐜嬫棤璁哄浣曡妫嬫垨鑰呬笉琛屾閮戒細杈擄細
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() != currentColor &&
                        chessComponents[i][j].getChessColor() != ChessColor.NONE) {
                    List<ChessboardPoint> points = chessComponents[i][j].getPointsCanMoveTo(this.getChessComponents());
                    // List<ChessboardPoint> points=new ArrayList<ChessboardPoint>();

                    //for(int k=0;k<points.size();k++) {
                    for (int l = 0; l < chessComponents[x0][y0].getPointsCanMoveTo(this.getChessComponents()).size(); l++) {
                        for (int k = 0; k < points.size(); k++) {
                            if (points.get(k).getX() == chessComponents[x0][y0].getPointsCanMoveTo(this.getChessComponents()).get(l).getX() &&
                                    points.get(k).getY() == chessComponents[x0][y0].getPointsCanMoveTo(this.getChessComponents()).get(l).getY()) {
                                counter++;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (counter == chessComponents[x0][y0].getPointsCanMoveTo(this.getChessComponents()).size()) {
            judge2 = 1;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() != currentColor &&
                        chessComponents[i][j].getChessColor() != ChessColor.NONE) {
                    List<ChessboardPoint> points = chessComponents[i][j].getPointsCanMoveTo(this.getChessComponents());
                    for (int k = 0; k < points.size(); k++) {
                        if (points.get(k).getX() == x0 && points.get(k).getY() == y0) {//璁板緱閫犳垚濞佽儊鐨勭偣鏄痯oints.get(k)閿欙紒杩欐槸鍥界帇鐨勭偣chessComponents[i][j]鎵嶆槸
                            judge3++;
                            label:
                            for (int l = 0; l < 8; l++) {
                                for (int m = 0; m < 8; m++) {
                                    //System.out.println(chessComponents[l][m].getChessColor()+"))");
                                    //int count=0;
                                    if (chessComponents[l][m].getChessColor() == currentColor &&
                                            chessComponents[l][m].getChessColor() != ChessColor.NONE) {
                                        //System.out.print("_");System.out.println(currentColor);
                                        // System.out.print(i); System.out.println(j);
                                        for (int n = 0; n < chessComponents[l][m].getPointsCanMoveTo(this.getChessComponents()).size(); n++) {
//                                            count++;
//                                            System.out.println(count);
                                            if (chessComponents[l][m].getPointsCanMoveTo(this.getChessComponents()).get(n).getX() == i &&
                                                    chessComponents[l][m].getPointsCanMoveTo(this.getChessComponents()).get(n).getY() == j) {
                                                System.out.println("++++++++");
                                                judge3--;
                                                break label;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//System.out.println(x0+" "+y0);
//System.out.println(judge1+"***"+judge2+"***"+judge3+"***");
        if (judge2 == 1 && judge3 != 0) {
            return true;
        }else {
            return false;
        }
    }








    public boolean jiangsiCase2(ChessComponent[][] chessComponents){
        int x0 = 0;
        int y0 = 0;
        int judge1 = -1;
        int judge2 = -1;
        int judge4 = 0;
        int counter = 0;
        int judge3 = 0;
        //鎵惧埌瀵规墜鐨勭帇鐨勪綅缃?x0,y0)锛屽垽鏂鎵嬫槸鍚﹁緭妫嬫槸鍦ㄦ垜涓嬪畬妫嬩箣鍚庣灛闂村垽鏂紙鍘熷厛杩樻病鏈塻wap chesscomponent锛屾墍浠ュ垽鏂篃鏄け鏁堢殑銆?
        //鎵惧埌鐜嬬殑浣嶇疆锛?
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() != currentColor && chessComponents[i][j].getChessColor() != null &&
                        (chessComponents[i][j].getName() == "K" || chessComponents[i][j].getName() == "k")) {
                    x0 = i;
                    y0 = j;
                }//找到了我的王
            }
        }
        //鐜嬫棤璁哄浣曡妫嬫垨鑰呬笉琛屾閮戒細杈擄細
        for (int i = 0; i < 8; i++) {//判断对面的棋子（currentColor）能不能覆盖我的王的全部的位置
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == currentColor &&
                        chessComponents[i][j].getChessColor() != ChessColor.NONE) {
                    List<ChessboardPoint> points = chessComponents[i][j].getPointsCanMoveTo(this.getChessComponents());
                    // List<ChessboardPoint> points=new ArrayList<ChessboardPoint>();

                    //for(int k=0;k<points.size();k++) {
                    for (int l = 0; l < chessComponents[x0][y0].getPointsCanMoveTo(this.getChessComponents()).size(); l++) {
                        for (int k = 0; k < points.size(); k++) {
                            if (points.get(k).getX() == chessComponents[x0][y0].getPointsCanMoveTo(this.getChessComponents()).get(l).getX() &&
                                    points.get(k).getY() == chessComponents[x0][y0].getPointsCanMoveTo(this.getChessComponents()).get(l).getY()) {
                                counter++;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (counter == chessComponents[x0][y0].getPointsCanMoveTo(this.getChessComponents()).size()) {
            judge2 = 1;//能够覆盖我的王的全部位置，那么为1这时候已经死了一半了
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == currentColor &&
                        chessComponents[i][j].getChessColor() != ChessColor.NONE) {
                    List<ChessboardPoint> points = chessComponents[i][j].getPointsCanMoveTo(this.getChessComponents());
                    for (int k = 0; k < points.size(); k++) {
                        if (points.get(k).getX() == x0 && points.get(k).getY() == y0) {///找到对手对我的王构成威胁的点：i，j处
                            judge3++;
                            label:
                            for (int l = 0; l < 8; l++) {
                                for (int m = 0; m < 8; m++) {
                                    //System.out.println(chessComponents[l][m].getChessColor()+"))");
                                    if (chessComponents[l][m].getChessColor() != currentColor &&
                                            chessComponents[l][m].getChessColor() != ChessColor.NONE) {
                                        //System.out.print("_");System.out.println(currentColor);
                                        // System.out.print(i); System.out.println(j);
                                        for (int n = 0; n < chessComponents[l][m].getPointsCanMoveTo(this.getChessComponents()).size(); n++) {
                                            if (chessComponents[l][m].getPointsCanMoveTo(this.getChessComponents()).get(n).getX() == i &&
                                                    chessComponents[l][m].getPointsCanMoveTo(this.getChessComponents()).get(n).getY() == j) {
                                                judge3--;
                                                break label;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//System.out.println(x0+" "+y0);
//System.out.println(judge1+"***"+judge2+"***"+judge3+"***");
        if (judge2 == 1 && judge3 != 0) {
            return true;
        } else {
            return false;
        }
    }
public void changeChessBoard(String Image1,String Image2){
        this.Image1=Image1;
        this.Image2=Image2;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                try {
                    chessComponents[i][j].setImage1(ImageIO.read(new File(this.Image1)));
                    chessComponents[i][j].setImage2(ImageIO.read(new File(this.Image2)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //chessComponents[i][j].setImage1(ImageIO.read(new File(this.Image1)));
            }
        }
        repaint();
}

    public void changeChessBoardColor(Color[] ChessBoardColor){
       this.ChessBoardColor=ChessBoardColor;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                //chessComponents[i][j].setBACKGROUND_COLORS(ChessBoardColor);
            }
        }
        repaint();
    }


    public void time3() {
        ChessColor bb=currentColor;
        final int[] midTime = {50};
//        timer=new Timer();
        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                midTime[0]--;
//                long hh = midTime[0] / 60 / 60 % 60;
//                long mm = midTime[0] / 60 % 60;
                long ss = midTime[0] % 60;
                jLabelTime.setText("还剩"+ss+"秒");
                //System.out.println("还剩" + ss + "秒");
                if(currentColor!=bb){
                    timer.cancel();
                }
                if(ss==0){
                    swapColor();
                    timer.cancel();
                }
            }

        }, 0, 1000);
    }

    public void changeTime(JLabel label){
        this.jLabelTime=label;
    }
}