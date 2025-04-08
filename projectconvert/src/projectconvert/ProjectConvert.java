package projectconvert;

import tatukgis.jdk.TGIS_ViewerBmp;

public class ProjectConvert {
    
    public static void main(String[] args) {
        TGIS_ViewerBmp GIS;
        String path;
        if (args.length != 1){
            System.out.println("TatukGIS Samples - TTKGP -> TTKPROJECT converter");
            System.out.println("Enter path of the TTKGP project. TTKPROJECT output will be placed in the same directory.");
            System.out.println("TTKGP file will be kept in its place after conversion.");
            System.out.println("Put directories with filenames and .TTKGP extension into parameters.");

        }else{
            GIS = new TGIS_ViewerBmp();
            path = args[0];
            GIS.Open(path);
            path = path.replace(".ttkgp", "");
            GIS.SaveProjectAs(path + ".ttkproject");
        }
    }
}
