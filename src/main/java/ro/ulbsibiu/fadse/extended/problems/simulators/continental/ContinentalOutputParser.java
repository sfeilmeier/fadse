/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ulbsibiu.fadse.extended.problems.simulators.continental;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.neuroph.util.FileUtils;

import ro.ulbsibiu.fadse.environment.Individual;
import ro.ulbsibiu.fadse.environment.Objective;
import ro.ulbsibiu.fadse.extended.problems.simulators.SimulatorBase;
import ro.ulbsibiu.fadse.extended.problems.simulators.SimulatorOutputParser;

/**
 *
 * @author Radu
 */
public class ContinentalOutputParser extends SimulatorOutputParser {

    /**
     * Constructor
     */
    public ContinentalOutputParser(SimulatorBase scbSim) {
        super(scbSim);
        this.defaultDelimiter = "=";
    }

    /**
     * Ths function calculates the objectives. In our case, this also includes
     * the composed objective COMPLEXITY.
     *
     * @return
     */
    @Override
    public LinkedList<Objective> getResults(Individual individual) {
        // TODO: regroup results

        File results = new File(this.simulator.getSimulatorOutputFile() + "\\results.txt");
        FileWriter fw = null;
        try {
            boolean createNewFile = results.createNewFile();
            fw = new FileWriter(results, true);

            for (Objective objective : individual.getObjectives()) {
                String outputfile = objective.getName();
                String value = "0";
                try {
                    value = FileUtils.readStringFromFile(new File(this.simulator.getSimulatorOutputFile() + "\\" + outputfile));
                    fw.write(outputfile + "=" + value.trim() + "\n");
                } catch (Exception ex) {
                    Logger.getLogger(ContinentalOutputParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ContinentalOutputParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception ex) {

            }
        }

        this.file = results;
        // Process the file and find some objectives => can be found in this.results
        this.processFile(individual);

        LinkedList<Objective> finalResults = new LinkedList<Objective>();

        for (Objective obj : this.currentObjectives) {
            String key = obj.getName();
            Double result = getSimpleObjectives().get(key);   
            
//            fixed by setting big in objective configuration
//            if(key.contains("mean_z") || (key.contains("F_per_mv"))){
//                result = 1/result;
//            }
            
            obj.setValue(result);
            finalResults.add(obj);
        }
        return finalResults;
    }
}
