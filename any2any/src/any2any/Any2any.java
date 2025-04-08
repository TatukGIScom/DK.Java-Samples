package any2any;

import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;

public class Any2any {

    public static void main(String[] args) {
        TGIS_LayerVector lm;
        TGIS_LayerVector ll;
        int shape_type;

        System.out.println("TatukGIS Samples - ANY->ANY converter ( Vector files only )");
        if(args.length != 3){
            System.out.println("Usage: ANY2SQL source_file destination shape_type");
            System.out.println("Where shape_type:");
            System.out.println("    A - Arc");
            System.out.println("    G - polygon");
            System.out.println("    P - Point");
            System.out.println("    M - Multipoint");
            return;
        }else{            
            lm = (TGIS_LayerVector)TGIS_Utils.GisCreateLayer("", args[0]);
            if(lm == null){
                System.out.println("ERROR: File " + args[0] + " not found");
                return;
            }
            lm.Open();
            
            switch( args[2] ){
                case "A": shape_type = TGIS_ShapeType.Arc; break;
                case "G": shape_type = TGIS_ShapeType.Polygon; break;
                case "P": shape_type = TGIS_ShapeType.Point; break;
                case "M": shape_type = TGIS_ShapeType.MultiPoint; break;
                default : shape_type = TGIS_ShapeType.Unknown; break;
            }
            
            ll = (TGIS_LayerVector)TGIS_Utils.GisCreateLayer("", args[1]);
            
            if(ll == null){
                System.out.println("ERROR: File " + args[1] + " not found");
                return;
            }
            
            ll.ImportLayer(lm, lm.getExtent(), shape_type, "", false);
            
        }
    }
    
}
