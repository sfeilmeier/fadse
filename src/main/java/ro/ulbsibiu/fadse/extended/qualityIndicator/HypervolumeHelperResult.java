package ro.ulbsibiu.fadse.extended.qualityIndicator;

import java.io.File;
import java.util.LinkedList;

/**
 *
 * @author Radu
 */
public class HypervolumeHelperResult {
    public int NrObjectives;
    public int NrFolders;
    public int[] PopulationSizeN;
    public double[] MaxObjectives;
    public File MetricsFolder;
    LinkedList<LinkedList> ParsedFilesN;
}
