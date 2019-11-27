


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.fastdfs.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class DataBase_FastDFS {

    private static Log _logger = LogFactory.getLog(DataBase_FastDFS.class);
    public String conf_filename = "E:\\DemoRecording\\com.LQS_Study_Recoding_Greadle\\src\\main\\java\\fdfs_client.conf";

    /**
     * 上传文件到FastDFS数据库
     * @param fileContent
     * @param fileExtName
     * @return
     */
    private String[] FastDFSUploadFile(byte[] fileContent, String fileExtName){

        String fileIds[] = new String[2];
        StorageServer storageServer = null;
        TrackerServer trackerServer = null;
        try{
            _logger.info("confURL.toString(): \"");
            ClientGlobal.init(conf_filename);
            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer,storageServer);
            fileIds = storageClient.upload_appender_file(fileContent,fileExtName,null);
            if(fileIds == null){
                _logger.info("DFSUploadFile failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(storageServer != null)
                    storageServer.close();
                if(trackerServer != null)
                    trackerServer.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
//        System.out.println("=====GroupID: " + fileIds[0] + " =====filepath: " + fileIds[1] );
        return fileIds ;
    }

    /**
     * 从FastDFS数据库中下载文件
     * @param args
     * @return
     */
    private String FastDFSDownloadFile(List<String> args){
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        String groupID = args.get(0);
        String filepath = args.get(1);
        String storePath = args.get(2);

        try{
            ClientGlobal.init(conf_filename);
            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer,storageServer);

            byte[] bytes = storageClient.download_file(groupID,filepath);

            if(bytes == null){
                _logger.info("DownloadFile is failed");
            }
            OutputStream out = new FileOutputStream(storePath);
            out.write(bytes);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(storageServer!= null)
                    storageServer.close();
                if(trackerServer != null)
                    trackerServer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("======download file sucessful!=====" + filepath);
        return "download file sucessful!";
    }

    /**
     * 从FastDFS数据库中删除文件
     * @param args
     * @return
     */
    public  String FastDFSDeleteFile(List<String> args){
        if (args.size() != 2) {
            return "Incorrect number of arguments. Expecting 2";
        }
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        String groupId = args.get(0);
        String Filepath = args.get(1);

        int i = 0 ;
        try{
            ClientGlobal.init(conf_filename);
            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer,storageServer);
            i = storageClient.delete_file(groupId,Filepath);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (storageServer != null)
                    storageServer.close();
                if (trackerServer != null)
                    trackerServer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        String deletemessage = null;
        if(i == 0){
            deletemessage = "delete file sucessful!";
        }
        System.out.println("===========" + deletemessage);
        return  deletemessage;
    }

    public static void main(String[] args) {

        String path = "E:\\DemoRecording\\File_storage\\JerseyTest\\test1.jpg";
        fileToByte fileToByte = new fileToByte();
        // 上传文件
        byte[] data = fileToByte.imageTobyte(path);

        String fileExtName = path.substring(path.lastIndexOf('.')+1) ;
        String[] fileIds = new DataBase_FastDFS().FastDFSUploadFile( data,fileExtName);
        System.out.println("=====GroupID: " + fileIds[0] + " =====filepath: " + fileIds[1] );

        // 下载
        List<String> downloadlist = new LinkedList<>();
        downloadlist.add(fileIds[0]);
        downloadlist.add(fileIds[1]);
        downloadlist.add("E:\\DemoRecording\\File_storage\\JerseyTest\\downloadtest" + "." + fileExtName);
        new DataBase_FastDFS().FastDFSDownloadFile(downloadlist);
//        // 删除
//        List<String> deletelist = new LinkedList<>();
//        deletelist.add(fileIds[0]);
//        deletelist.add("M00/00/00/wKgojV1x8XiEZ1CTAAAAAGYRQew206.LQS");
//        new DataBase_FastDFS().FastDFSDeleteFile(deletelist);
    }
}


