import org.junit.Test;

import static java.lang.Math.abs;
import static org.junit.Assert.*;

public class DijkstraTest {

    Dijkstra dk = new Dijkstra();
    int M = 10;
    int[][] weight = {
            {0, 1, 7, M, 6, M },
            {1, 0, M, 3, M, M },
            {7, M, 0, 2, M, 1 },
            {M, 3, 2, 0, M, M },
            {6, M, M, M, 0, 2 },
            {M, M, 1, M, 2, 0 }
    };

    /**
     *  开始位置 start
     *  最短路径 shortPath
     *
     */
    @Test
    public void testDijkstra() {
        int start = 0;
        int[] shortPath = dk.dijkstra(weight, start);
        for (int i = 0; i < shortPath.length; i++) {
            System.out.println("从" + start + "出发到" + i + "的最短距离为：" + shortPath[i]);
        }
    }

    // 打印数组
    @Test
    public void testPrint(){
        for(int i = 0; i < weight.length; i++){
            for(int k =0; k < weight.length; k++){
                System.out.print(weight[i][k] + ", ");
            }
            System.out.println();
        }
    }

    @Test
    public void testShow(){
        // 假设的比赛场地，其中设置值为99的表示障碍物
        int[][] data = {
                {1, 1, 1, 1},
                {1, 1, 10, 1},
                {1, 10, 1, 1}
        };
        int[][] WeightMatrix = dk.creatWeightMatrix(data);
        // 计算最短路径并给出路线图
        int start = 8;
        int[] shortPath = dk.dijkstra(WeightMatrix, start);
        for (int i = 0; i < shortPath.length; i++) {
            System.out.println("从" + start + "出发到" + i + "的最短距离为：" + shortPath[i]);
        }
    }

    @Test
    public void testtt(){
        System.out.println(2 % 5);// 2 取余
        System.out.println(3 / 4);// 0
        System.out.println(5 / 4);// 1  取整
        System.out.println(6 / 4);// 1
    }

    /**
     * 遗弃版本
     */
    @Test
    public void testShow1(){

        int row = 2; //行
        int column = 4;//列
        int[][] wg = new int[row][column];
        for(int i = 0; i < row; i++){
            for(int j =0; j < column; j++){
                wg[i][j] = 1;
                System.out.print(wg[i][j] + ", ");
            }
            System.out.println();
        }
        dk.creatWeightMatrix(wg);


        // 初始状态下定义数组权重  当初始时 权重为1，当是障碍物时权重为 99 这样设置，根据二维矩阵的值确定最短路径
        // 现在的权重矩阵全部为1，根据此求出最短路径
        int matrixDimension = row * column;
        int[][] w = new int[matrixDimension][matrixDimension];
        for(int i = 0; i < matrixDimension; i++){
            //生成初始位置
            int H = i / column; //取整
            int L = i % column; //取余
            for(int j = 0; j < matrixDimension; j++){
                // 如何表示出 wg 矩阵的行和列 length = 2    width = 3
                int wgHang = j / column ;
                int wgLie = j % column ;
//                System.out.println("计算位置" + "[" + wgHang + " " + wgLie  + "]");  //这个可以了
//                System.out.println("初始" + "[" + H + " " + L + "]");
                int temp = abs(wgHang - H)  + abs(wgLie - L)
                        + wg[wgHang][wgLie] -1;
                w[i][j] = temp;
            }
//            System.out.println();
        }
    }

}