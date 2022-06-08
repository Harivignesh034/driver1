import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;


public class Test1 {

        public static void main(String[] args) throws IOException, ParseException {
            Scanner sc=new Scanner(System.in);
            String path="";
            JSONParser jsonparser = new JSONParser();
            String str="pathfiles.txt";
            String str1="";
                        try {
                            File myObj = new File(str);
                            Scanner myReader = new Scanner(myObj);
                            while (myReader.hasNextLine()) {
                                String data = myReader.nextLine();
                                if(data.contains("link")){
                                    path=data.substring(data.lastIndexOf("link")+5);
                                    if((path.substring(0,4)).equals("http")){
                                        DriverDetails.createFolder("jsonFolder");
                                        URL url = new URL(path);
                                        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                                             FileOutputStream fileOutputStream = new FileOutputStream("C:\\jsonFolder\\" + "Jsonfile")) {
                                            byte dataBuffer[] = new byte[1024];
                                            int bytesRead;
                                            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                                                fileOutputStream.write(dataBuffer, 0, bytesRead);
                                            }
                                            str1="C:\\jsonFolder\\" + "Jsonfile";

                                        } catch (Exception e) {
                                            System.out.println(e);

                                        }
                                    }else{
                                        str1=path;
                                    }

                                }
                                System.out.println(data);
                            }
                            myReader.close();
                        } catch (FileNotFoundException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }



            String[] Email={};
            try {
                File myObj = new File(str);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if(data.contains("mailid")){
                        String s1=data.substring(data.lastIndexOf("mailid")+7);
                        Email=s1.split(",");
                    }
                    System.out.println(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            FileReader reader = new FileReader(str1);//"C:\\intern\\JSON\\jsonfiles1.Json"
            Object obj = jsonparser.parse(reader);
            try {
                JSONObject empl = (JSONObject) obj;

                DriverDetails emp = new DriverDetails();

                JSONArray arr = (JSONArray) empl.get("DriverVendorMetadata");

                emp.checkDriver(arr,Email);

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }


