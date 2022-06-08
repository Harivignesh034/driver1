import com.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

class DriverDetails extends Thread{



    private static Logger log= Logger.getLogger(String.valueOf(DriverDetails.class));
    static String cmd1="";
    public static long getDirectorySizeJava7(Path path,String file) {

        AtomicLong size = new AtomicLong(0);

        try {

            Files.walkFileTree(path, new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    System.out.printf("Failed to get size of %s%n%s", file, e);
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            System.out.printf("%s", e);
            log.error(file);
        }

        return size.get();

    }

    public static byte[] createSHA256Checksum(String filename) throws Exception {
        InputStream fis = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("SHA-256");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }
    public static String getSHA256Checksum(String filename) throws Exception {
        byte[] b = createSHA256Checksum(filename);
        String result = "";

        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public static byte[] createChecksum(String filename) throws Exception {
        InputStream fis = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }
    public static String getMD5Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";

        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
    public static void createFolder(String str){
        try {
            String path2 = "c:\\"+str;
            File f1 = new File(path2);
            System.out.print(path2);
            boolean bool2 = f1.mkdirs();
            if (bool2) {
                System.out.println("Folder is created successfully");
            } else {
                System.out.println("Already folder created");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void deleteDirectory(File file)
    {
        // store all the paths of files and folders present
        // inside directory
        if(!(file.listFiles()==null)) {
            for (File subfile : file.listFiles()) {

                // if it is a subfolder,e.g Rohan and Ritik,
                // recursiley call function to empty subfolder
                if (subfile.isDirectory()) {
                    deleteDirectory(subfile);
                }

                // delete files and empty subfolders
                subfile.delete();
            }
        }
    }
    public static void deleteFile(String str){
        try {
            String path2 = "c:\\"+str;
            File f= new File(path2);
            //File f1=new File(des.get(cpp));
            deleteDirectory(f);
            if(f.delete())
            {
                System.out.println(f.getName() + " deleted");
            }
            else
            {
                System.out.println("failed");
                log.warn(f+" failed to delete");
            }}
        catch (Exception e){

        }
    }


    public void checkDriver(JSONArray array,String[] Email)  {
        createFolder("Newfiles");
        createFolder("extract");
        createFolder("newlog");
        createFolder("filehandle");
        int count1=0;



        HashMap<String, String> map = new LinkedHashMap<>();
        List<String> pathfile=new ArrayList<>();
        List<Driver> driverList=new ArrayList<>();
        HashMap<String, String> nopath = new LinkedHashMap<>();
        HashMap<String, String> nodes=new LinkedHashMap<>();
        HashMap<String,String> des=new LinkedHashMap<>();


        try{
            LocalDateTime datetime1 = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
            String formatDateTime = datetime1.format(format);
            System.out.println(formatDateTime);
            CSVWriter osw=new CSVWriter(new FileWriter("C:\\filehandle\\" + formatDateTime + ".csv"));


            for (int i = 0; i < array.size(); i++) {

                JSONObject DriverHome = (JSONObject) array.get(i);
                //li1.add(DriverHome);
                JSONArray DriverArray = (JSONArray) DriverHome.get("FILES");
                for (int j = 0; j < DriverArray.size(); j++) {
                    count1++;
                    JSONObject files = (JSONObject) DriverArray.get(j);
                    //li2.add(files);
                    System.out.println(
                            files.get("FILE_ID") + " " + files.get("FILE_NAME") + " " + files.get("URL"));

                    URL url = new URL(String.valueOf(files.get("URL")));
                    try {
                        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                             FileOutputStream fileOutputStream = new FileOutputStream("C:\\Newfiles\\" + files.get("FILE_NAME"))) {
                            byte dataBuffer[] = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                                fileOutputStream.write(dataBuffer, 0, bytesRead);
                            }
                            log.info(files.get("FILE_NAME")+"  successfully download");
                        } catch (Exception e) {
                            System.out.println(e);
                            log.error(e);
                        }
                        try {
                            String str = "C:\\Newfiles\\" + files.get("FILE_NAME");
                            pathfile.add(str);
                            Path path = Paths.get(str);
                            String size = String.valueOf(files.get("SIZE"));
                            String size1 = String.valueOf(getDirectorySizeJava7(path,String.valueOf(files.get("FILES_ID"))));
                            System.out.println("before download files size  :" + size);
                            System.out.println("after download files size  :" + size1);
                            String checksize = "";
                            if (size.equals(size1)) {
                                checksize = "same size";
                            } else {
                                checksize = "diff size";
                            }
                            String checksum = String.valueOf(files.get("MD5_CHECKSUM"));
                            String checksum1 = getMD5Checksum("C:\\Newfiles\\" + files.get("FILE_NAME"));
                            System.out.println("before download MD5_CHECKSUM  :" + checksum);
                            System.out.println("after download MD5_CHECKSUM   :" + checksum1);
                            String checksumcheck = "";
                            if (checksum.equals(checksum1)) {
                                checksumcheck = "same checksum";
                            } else {
                                checksumcheck = "diff checksum";
                            }
                            String checksum2 = String.valueOf(files.get("SHA256_CHECKSUM"));
                            String checksum3 = getSHA256Checksum("C:\\Newfiles\\" + files.get("FILE_NAME"));
                            System.out.println("before download SHA256_CHECKSUM  :" + checksum2);
                            System.out.println("after download SHA256_CHECKSUM   :" + checksum3);
                            String checksumcheck1 = "";
                            if (checksum2.equals(checksum3)) {
                                checksumcheck1 = "same checksum";
                            } else {
                                checksumcheck1 = "diff checksum";
                            }
                            map.put(String.valueOf(files.get("FILE_ID")), String.valueOf(files.get("EXTRACTED_FILE_SIZE")));
                            String path1 = String.valueOf(files.get("SILENT_SWITCH"));
                            String path2 = "";

                            if (path1.contains("des")) {

                                path2 = "C:\\extract\\"+files.get("FILE_ID");
                                if(String.valueOf(DriverHome.get("VENDOR_NAME")).equals("DELL") || String.valueOf(DriverHome.get("VENDOR_NAME")).equals("HP")) {
                                    File f1 = new File(path2);
                                    System.out.print(path2);
                                    boolean bool2 = f1.mkdirs();
                                    if (bool2) {
                                        System.out.println("Folder is created successfully");
                                    } else {
                                        System.out.println("Error Found!");
                                    }

                                        String str1 = "C:\\Newfiles\\" + files.get("FILE_NAME");
                                        String com = String.valueOf(files.get("SILENT_SWITCH"));
                                        String cmd = com.replace("\"src\"", str1);
                                        cmd1 = cmd.replace("\"des\"", path2);
                                        des.put(String.valueOf(files.get("FILE_ID")),path2);
                                        System.out.println(cmd1);

                                }else {
                                    if(String.valueOf(files.get("FILE_ID")).equals("94")){
                                        System.out.println("94-------------------");
                                        String str1 = "C:\\Newfiles\\" + files.get("FILE_NAME");
                                        String com = String.valueOf(files.get("SILENT_SWITCH"));
                                        String cmd = com.replace("\"src\"", str1);
                                        cmd1 = cmd.replace("\"des\"", path2);
                                        des.put(String.valueOf(files.get("FILE_ID")),path2);
                                        System.out.println(cmd1);
                                    }else {
                                        String str1 = "\"C:\\Newfiles\\" + files.get("FILE_NAME") + "\"";
                                        System.out.println("lenovao-----------------");
                                        String com = "\"" + files.get("SILENT_SWITCH");
                                        String cmd = com.replace("src", str1);
                                        cmd1 = cmd.replace("des", path2);
                                        des.put(String.valueOf(files.get("FILE_ID")),path2);
                                        System.out.println(cmd1);
                                    }

                                }
                            } else {
                                String str1 = "C:\\Newfiles\\" + files.get("FILE_NAME") ;
                                String com = "\"" + files.get("SILENT_SWITCH")+"\"";
                                cmd1 = com.replace("src", str1);
                                nopath.put(String.valueOf(files.get("FILE_ID")),String.valueOf(files.get("DEFAULT_EXTRACT_FOLDER")));
                                nodes.put(String.valueOf(files.get("FILE_ID")),String.valueOf(files.get("FILE_NAME")));
                                des.put(String.valueOf(files.get("FILE_ID")),String.valueOf(files.get("DEFAULT_EXTRACT_FOLDER")));
                                System.out.println(cmd1);
                            }

                            ProcessBuilder p = new ProcessBuilder();
                            p.command("cmd.exe", "/c", cmd1);
                            Process pb=p.start();
                            String  a=String.valueOf(pb.getInputStream().available());
                            System.out.println("return code "+a);



                            LocalDateTime datetime3 = LocalDateTime.now();
                            DateTimeFormatter format3 = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
                            String Time = datetime3.format(format3);
                            String winpeVersion=String.valueOf(files.get("WINPE_VERSION"));

                            driverList.add(new Driver(String.valueOf(files.get("FILE_ID")), checksum, checksum1, checksumcheck,checksum2,checksum3,checksumcheck1, size, size1, checksize,Time,a,winpeVersion));


                        }  catch (Exception e) {
                            throw new RuntimeException(e);
                        }


                    }catch (Exception e){
                        System.out.println(e);
                        log.error(e);
                    }


                //log.info(files.get("FILE_ID"));

                }
            }
            Thread.sleep(5000);
            int count2=0;
            int cpp=0;

            int l=0;
            System.out.println(map);
            System.out.println(nopath);
            System.out.println(nodes);
            System.out.println(des);

            for(String i : map.keySet()){
                String checkextractsize="";
                String defaultSize = String.valueOf(map.get(i));
                Path path3 = Paths.get("C://extract//"+i);
                System.out.println(i+" "+defaultSize);
                String size3="";
                try {
                    size3 = String.valueOf(getDirectorySizeJava7(path3, i));

                System.out.println(path3);
                if(size3.equals("0")){
                    if(nopath.containsKey(i)) {
                        try{
                        System.out.println("path des  " + nopath.get(i));
                        System.out.println(des.get(i).substring(0,des.get(i).lastIndexOf("\\")+1));
                        size3 = String.valueOf(getDirectorySizeJava7(Paths.get("C:\\DRIVERS\\SCCM\\"+nopath.get(i).substring(des.get(i).lastIndexOf('\\')+1)), nopath.get(i).substring(des.get(i).lastIndexOf('\\')+1)));
                        System.out.println("  "+des.get(i).substring(des.get(i).lastIndexOf("\\")+1));
                        System.out.println("after extract size" + size3);
                        }catch (Exception e){
                            System.out.println(e);
                        }
                        if (defaultSize.equals(size3))
                            checkextractsize = "same extract size";
                        else
                            checkextractsize = "diff extract size";
                        log.info(i +"  successfully extract");
                    }else{
                        System.out.println("no files");
                        log.warn(i +"  no extract happened");
                    }
                }else {
                    System.out.println("after extract size :" + size3);
                    if (defaultSize.equals(size3))
                        checkextractsize = "same extract size";
                    else
                        checkextractsize = "diff extract size";
                    log.info(i +"  successfully extract");
                }

                }catch (Exception e){
                    System.out.println(e);
                    log.error("file id"+i+" "+e);
                }

                driverList.get(cpp).set(defaultSize,size3,checkextractsize);

                try {
                    File f= new File(pathfile.get(cpp));
                    File f1=new File(des.get(i));
                    deleteDirectory(f1);
                    if(f.delete())
                    {
                        System.out.println(f.getName() + " deleted");
                    }
                    else
                    {
                        System.out.println("failed");
                        log.warn(f+" failed to delete");
                    }
                    if(f1.delete())
                    {
                        System.out.println(f1 + " deleted");
                    }
                    else
                    {
                        System.out.println("failed");
                        log.warn(f1+" failed to delete");
                    }


                } catch (Exception e) {
                    log.error(e +" "+i);
                    e.printStackTrace();
                }
                cpp++;
            }
            String set1[]={"file id","md5CheckSum","afterMd5CheckSum","checkMD5checksum","SHA256Checksum","afterSHA256CheckSUM","checkSHA256checksum","size",
                    "downloadSize","sizeCheckSize","extractDefaultSize","afterextractSize","checkextractcheck","return code","WINPEVERSION"};

            osw.writeNext(set1);
            LocalDateTime datetime2 = LocalDateTime.now();
            DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
            String formatDateTime2 = datetime2.format(format2);
            System.out.println(formatDateTime);
            CSVWriter osw2=new CSVWriter(new FileWriter("C:\\filehandle\\" + formatDateTime2 + ".csv"));
            osw2.writeNext(set1);
            for(Driver i : driverList){
                String set[]={i.fileid,i.md5CheckSum,i.afterDownloadMd5CheckSum,i.checksumCheck,i.SHA256CheckSum,i.afterDownloadSHA256CheckSum,i.checksumcheck2,
                        i.size,i.downloadSize,i.sizeCheckSize,i.extractDefaultSize,i.afterextractSize,i.checkextractcheck,i.returncode,i.winpeVersion};


                if((i.checksumCheck.equals("diff checksum")  ||  i.sizeCheckSize.equals("diff size") || i.checkextractcheck.equals("diff extract size") )) {

                        if (i.checksumCheck.equals("diff checksum"))
                            log.warn(i.fileid + "   MD5 checksum not match");
                        if (i.checksumcheck2.equals("diff checksum"))
                            log.warn(i.fileid + "   SHA256 checksum not match");
                        if (i.sizeCheckSize.equals("diff size"))
                            log.warn(i.fileid + "  download size not same");
                        if (i.afterextractSize.equals("diff size"))
                            log.warn(i.fileid + "  extract size not same");

                        osw2.writeNext(set);
                        count2++;

                }

                osw.writeNext(set);


            }
            osw.close();
            osw2.close();
            int b=count1-count2;
            SendMail.sendFile(formatDateTime,formatDateTime2,Email,b,count2,count1);
        }catch (Exception e){
            System.out.println(e);
            log.error(e);
        }
        /*deleteFile("Newfiles");
        deleteFile("extract");*/
        deleteFile("DRIVERS");


    }
}
