
public class Driver {
    String fileid="";
    String md5CheckSum="";
    String afterDownloadMd5CheckSum="";
    String checksumCheck="";

    String SHA256CheckSum="";
    String afterDownloadSHA256CheckSum="";
    String checksumcheck2="";
    String size="";
    String downloadSize="";
    String sizeCheckSize="";
    String extractDefaultSize="";
    String afterextractSize="";
    String checkextractcheck="";
    String time="";
    String returncode="";
    String winpeVersion="";
    Driver(String filename,String md5CheckSum,String afterDownloadMd5CheckSum,String checkSumCheck,String SHA256CheckSum,String afterDownloadSHA256CheckSum,String checksumcheck2,String size,String downloadSize,String sizeCheckSize,String time,String returncode,String winpeVersion){
        this.fileid=filename;
        this.afterDownloadMd5CheckSum=afterDownloadMd5CheckSum;
        this.md5CheckSum=md5CheckSum;
        this.checksumCheck=checkSumCheck;
        this.SHA256CheckSum=SHA256CheckSum;
        this.afterDownloadSHA256CheckSum=afterDownloadSHA256CheckSum;
        this.checksumcheck2=checksumcheck2;
        this.size=size;
        this.downloadSize=downloadSize;
        this.sizeCheckSize=sizeCheckSize;
        this.time=time;
        this.returncode=returncode;
        this.winpeVersion=winpeVersion;

    }
    public void set(String extractDefaultSize,String afterextractSize,String checkextractcheck){
        this.extractDefaultSize=extractDefaultSize;
        this.afterextractSize=afterextractSize;
        this.checkextractcheck=checkextractcheck;
    }


}
