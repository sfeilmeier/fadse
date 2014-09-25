/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ulbsibiu.fadse.extended.qualityIndicator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.util.JMException;

/**
 *
 * @author Radu
 */
public class HypervolumeHelper {

    public static HypervolumeHelperResult ReadDirectories() throws IOException {
        int n = 2;
        List<String> folders = new LinkedList<String>();
        folders.add("D:\\Cluj Simulations\\Normal\\cnsgaii0.1");
        folders.add("D:\\Cluj Simulations\\Normal\\cnsgaii0.5");
        folders.add("D:\\Cluj Simulations\\Normal\\cnsgaii0.9");
        folders.add("D:\\Cluj Simulations\\Normal\\mochc");
        folders.add("D:\\Cluj Simulations\\Normal\\nsgaii");
        folders.add("D:\\Cluj Simulations\\Normal\\smpso");
        folders.add("D:\\Cluj Simulations\\Normal\\spea2");

        folders.add("D:\\Cluj Simulations\\FE\\CNSGAII 0.1");
        folders.add("D:\\Cluj Simulations\\FE\\CNSGAII 0.5");
        folders.add("D:\\Cluj Simulations\\FE\\CNSGAII 0.9");
        folders.add("D:\\Cluj Simulations\\FE\\MOCHC");          
        folders.add("D:\\Cluj Simulations\\FE\\nsgaii");
        folders.add("D:\\Cluj Simulations\\FE\\smpso");        
        folders.add("D:\\Cluj Simulations\\FE\\spea2");      

        System.out.println("Specify number of folders");
        n = folders.size();//Integer.parseInt((new BufferedReader(new InputStreamReader(System.in))).readLine());
        int nrOfobejctives = 0;
        int[] populationSizeN = new int[n];
        String[] folderPathN = new String[n];
        LinkedList<LinkedList<File>> listOfPopulationFilesN = new LinkedList<LinkedList<File>>();
        System.out.println("Specify nr of objectives ");
        nrOfobejctives = 2; //Integer.parseInt((new BufferedReader(new InputStreamReader(System.in))).readLine());
        System.out.println("Specify the population size ");
        int populationSizeN_temp = 100;//Integer.parseInt((new BufferedReader(new InputStreamReader(System.in))).readLine());                       

        for (int i = 0; i < n; i++) {
            System.out.println("Specify path to folder " + (i + 1));
            folderPathN[i] = folders.get(i);
//                System.out.println("Specify the population size " + (i+1));
            populationSizeN[i] = populationSizeN_temp;
            listOfPopulationFilesN.add(MetricsUtil.getListOfFiles(folderPathN[i], "filled"));
            System.out.println(folderPathN[i]);
            List<String> individuals = new LinkedList<String>();
            for (File file : listOfPopulationFilesN.get(i)) {
                BufferedReader input = new BufferedReader(new FileReader(file));

                String line = null; // not declared within while loop
                line = input.readLine();// skip the headder
                while ((line = input.readLine()) != null && i < 100) {                    
                    StringTokenizer tokenizer = new StringTokenizer(line, ",");                   
                    String str = line.substring(0, line.lastIndexOf(","));
                    str = line.substring(0, str.lastIndexOf(","));
                    if(!individuals.contains(str)){
                        individuals.add(str);
                    }
                } // while				
                System.out.println(individuals.size());
            }            
        }


        File metricsFolder = new File("D:\\Cluj Simulations\\metrics" + System.getProperty("file.separator") + "metricsComposed" + System.currentTimeMillis());
        if (metricsFolder.mkdir()) {
            LinkedList<LinkedList> parsedFilesN = new LinkedList<LinkedList>();
            LinkedList<double[]> maxObjectivesN = new LinkedList<double[]>();
            for (int i = 0; i < n; i++) {
                parsedFilesN.add(MetricsUtil.parseFiles(nrOfobejctives, populationSizeN[i], listOfPopulationFilesN.get(i)));
                System.out.println("Files found for folder " + i + ":" + parsedFilesN.get(i).size());
                maxObjectivesN.add(MetricsUtil.getmaxObjectives(nrOfobejctives, parsedFilesN.get(i)));
            }
            double[] maxObjectives = new double[nrOfobejctives];
            for (int i = 0; i < nrOfobejctives; i++) {
                maxObjectives[i] = max(maxObjectivesN, i);
            }

            HypervolumeHelperResult result = new HypervolumeHelperResult();

            result.MaxObjectives = maxObjectives;
            result.MetricsFolder = metricsFolder;
            result.NrFolders = n;
            result.NrObjectives = nrOfobejctives;
            result.PopulationSizeN = populationSizeN;
            result.ParsedFilesN = parsedFilesN;

            return result;

        } else {
            //System.out.println("Directory was not created");
            return null;
        }
    }

    public static double max(LinkedList<double[]> maxObjectivesN, int i) {
        double max = 0;
        for (double[] currentVec : maxObjectivesN) {
            if (currentVec[i] > max) {
                max = currentVec[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        String folder = "D:\\Work\\Doctorat\\Output\\FADSE\\ServerSimulator\\MOCHC Scoala\\1345563456000";
        LinkedList<File> files = MetricsUtil.getListOfFiles(folder, "filled");
        LinkedList<SolutionSet> solutions = new LinkedList<SolutionSet>();
        for (File file : files) {
            try {
                SolutionSet set = MetricsUtil.readPopulation(file.getAbsolutePath(), 100, 2);
                solutions.add(set);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HypervolumeHelper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HypervolumeHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        double[] maxObjectives = new double[2];
        int hyperVolumecount = 0;
        for (int i = 1; i < solutions.size(); i++) {
            TwoSetHypervolumeDifferenceResult p = MetricsUtil.computeHypervolumeTwoSetDifferenceForTwoSets(solutions.get(i - 1), solutions.get(i), 2, 100, maxObjectives);
            DecimalFormat df = new DecimalFormat(".################");
            System.out.println(df.format(p.CombinedHyperVolume21));
            if (p.CombinedHyperVolume21 < p.FirstHyperVolume / 1000) {
                hyperVolumecount++;
            } else {
                hyperVolumecount = 0;
            }
            if (hyperVolumecount > 5) {
                System.out.println("Do the Cataclysmic");
                hyperVolumecount = 0;
            }
            System.out.println("12: " + p.CombinedHyperVolume12 + "21: " + p.CombinedHyperVolume21 + "   2: " + p.FirstHyperVolume + "  3:" + p.SecondHyperVolume);
        }
    }
}
