import static java.lang.Math.abs;

/**
 * Dijkstra算法求最短路径 Java实现
 */
public class Dijkstra {

    public int[] dijkstra(int[][] weight, int start) {
        // 接受一个有向图的权重矩阵，和一个起点编号start（从0编号，顶点存在数组中）
        // 返回一个int[] 数组，表示从start到它的最短路径长度
        int n = weight.length; // 顶点个数
        int[] shortPath = new int[n]; // 保存start到其他各点的最短路径
        String[] path = new String[n]; // 保存start到其他各点最短路径的字符串表示
        for (int i = 0; i < n; i++)
            path[i] = new String(start + "-->" + i);
        int[] visited = new int[n]; // 标记当前该顶点的最短路径是否已经求出,1表示已求出

        // 初始化，第一个顶点已经求出
        shortPath[start] = 0;
        visited[start] = 1;

        for (int count = 1; count < n; count++) { // 要加入n-1个顶点
            int k = -1; // 选出一个距离初始顶点start最近的未标记顶点
            int dmin = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (visited[i] == 0 && weight[start][i] < dmin) {
                    dmin = weight[start][i];
                    k = i;
                }
            }
            // 将新选出的顶点标记为已求出最短路径，且到start的最短路径就是dmin
            shortPath[k] = dmin;
            visited[k] = 1;
            // 以k为中间点，修正从start到未访问各点的距离
            for (int i = 0; i < n; i++) {
                //如果 '起始点到当前点距离' + '当前点到某点距离' < '起始点到某点距离', 则更新
                if (visited[i] == 0 && weight[start][k] + weight[k][i] < weight[start][i]) {
                    weight[start][i] = weight[start][k] + weight[k][i];
                    path[i] = path[k] + "-->" + i;
                }
            }
        }

        // TODO 计算出最短路径  path[i] 中即是最短路径的走法
        for (int i = 0; i < n; i++) {
            System.out.println("从" + start + "出发到" + i + "的最短路径为：" + path[i]);
        }
        System.out.println("=====================================");
        return shortPath;
    }

//    public static final int M = 10000; // 代表正无穷
    //案例演示
//    public static void main(String[] args) {
//        // 二维数组每一行分别是 A、B、C、D、E 各点到其余点的距离,
//        // A -> A 距离为0, 常量M 为正无穷
//        int[][] weight1 = {
//                {0, 1, 7, M, 6, M },
//                {1, 0, M, 3, M, M },
//                {7, M, 0, 2, M, 1 },
//                {M, 3, 2, 0, M, M },
//                {6, M, M, M, 0, 2 },
//                {M, M, 1, M, 2, 0 }
//        };
//
//        int start = 0;
//        int[] shortPath = dijkstra(weight1, start);
//        for (int i = 0; i < shortPath.length; i++) {
//            System.out.println("从" + start + "出发到" + i + "的最短距离为：" + shortPath[i]);
//        }
//    }

    // 根据 二维数组生成权重矩阵
    public int[][] creatWeightMatrix(int[][] data){

        int[][] data1 = {
                {1, 1, 1, 1, 1 },
                {1, 1, 99, 1, 1 },
                {1, 1, 1, 99, 1 },
                {1, 1, 1, 1, 1 }
        };



        int row = data.length;
        int column = data[0].length;
        int matrixDimension = row * column;

        int[][] weightMatrix = new int[matrixDimension][matrixDimension];
        for(int i = 0; i < matrixDimension; i++){
            //生成初始位置
            int H = i / column; //取整
            int L = i % column; //取余
            for(int j = 0; j < matrixDimension; j++){
                // 生成实际运算位置
                int wH = j / column ;
                int wL = j % column ;
//                System.out.println("运算位置" + "[" + wgHang + " " + wgLie  + "]");  //这个可以了
//                System.out.println("初始" + "[" + H + " " + L + "]");
                //这是一种情况，当不等时应该分开
                // 基于行判断
                Boolean hp = (H == wH) && (abs(wL - L) <= 1);
                // 基于列判断
                Boolean lp = (L == wL) && (abs(wH - H) <= 1);
                int temp;
                if( hp || lp){
                    temp = abs(wH - H)  + abs(wL - L) + data[wH][wL] -1;
                }else {
                    temp = 99;
                }
                weightMatrix[i][j] = temp;
            }
//            System.out.println();
        }
        //打印生成的权重矩阵
        for(int i = 0; i < matrixDimension; i++){
            for(int j = 0; j < matrixDimension; j++){
                System.out.print(weightMatrix[i][j] + ", ");
            }
            System.out.println();
        }
        return weightMatrix;
    }
}