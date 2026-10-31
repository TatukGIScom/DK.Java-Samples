package exporttoimage;

import tatukgis.jdk.TGIS_LayerPixelSubFormat;

/**
 * Thin wrapper that deep-copies a {@link TGIS_LayerPixelSubFormat} descriptor.
 *
 * <p>Each entry in the format combobox corresponds to one T_Capability instance.
 * The deep copy (via {@code CreateCopy()}) ensures the stored descriptor remains
 * valid even if the originating {@code Capabilities} list is later released or
 * reused by the DK runtime.</p>
 */
public class T_Capability {

    /** The wrapped sub-format descriptor (pixel depth, compression, etc.). */
    public TGIS_LayerPixelSubFormat C;

    /**
     * Constructs a T_Capability by deep-copying the given sub-format.
     *
     * @param _c Source {@link TGIS_LayerPixelSubFormat} to copy.
     *           {@code CreateCopy()} produces a value independent of the
     *           originating format list.
     */
    public T_Capability(TGIS_LayerPixelSubFormat _c){
        C = _c.CreateCopy();
    }
}
