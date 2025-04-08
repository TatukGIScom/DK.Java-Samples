package exporttoimage;

import tatukgis.jdk.TGIS_LayerPixelSubFormat;

public class T_Capability {

    public TGIS_LayerPixelSubFormat C;
    
    public T_Capability(TGIS_LayerPixelSubFormat _c){
        C = _c.CreateCopy();
    }
}
