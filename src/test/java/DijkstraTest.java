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

    // 打印二维数组
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
//        for (int i = 0; i < shortPath.length; i++) {
//            System.out.println("从" + start + "出发到" + i + "的最短距离为：" + shortPath[i]);
//        }
    }

    /**
     * 数学上的取整数和取余数
     */
    @Test
    public void testtt(){
        System.out.println(2 % 5);// 2 取余
        System.out.println(5 / 4);// 1  取整
    }
}