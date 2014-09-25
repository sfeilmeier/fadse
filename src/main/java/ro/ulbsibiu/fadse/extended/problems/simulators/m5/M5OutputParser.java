package ro.ulbsibiu.fadse.extended.problems.simulators.m5;
import ro.ulbsibiu.fadse.environment.Individual;
import ro.ulbsibiu.fadse.extended.problems.simulators.msim3.Msim3Constants;
import ro.ulbsibiu.fadse.extended.problems.simulators.SimulatorOutputParser;
import ro.ulbsibiu.fadse.extended.problems.simulators.SimulatorBase;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import ro.ulbsibiu.fadse.environment.Objective;

/**
 * Parser for the output file of Msim simulator
 *
 * @author Andrei
 * @version 0.1
 * @since 15.04.2010
 */
public class M5OutputParser extends SimulatorOutputParser {

    /**
     * constructor
     * @param outputFilePath  the path to the output file that needs processing
     */
    public M5OutputParser(SimulatorBase scbSim){
        super(scbSim);
    }

    /**
     * Searches for the output result value in a line of text 
     * This is depending on the simulator output file format
     * @param textLine    the line of text where the value is to be searched
     * @param lineNumber  in some cases the line number is necessary for processing
     *                    skipping lines, or changing the delimiter (starts at 1)
     */
    @Override
    protected void processLine(String textLine, int lineNumber){
        
        Scanner scanner = new Scanner(textLine);
        this.defaultDelimiter = " \\s*"; 
        scanner.useDelimiter(this.defaultDelimiter);

        if (scanner.hasNext()){

          String name = scanner.next().trim();
          
          if (this.isInOutputs(name)){
            
            if(scanner.hasNext())
                this.addSimpleObjective(name, scanner.nextDouble());
          }
        }

        scanner.close();
    }

     /**
     * gets the final results
     * @return
     */
    @Override
    public LinkedList<Objective> getResults(Individual individual){
        // TODO: regroup results
        this.processFile(individual);

        LinkedList<Objective> finalResults = new LinkedList<Objective>();
        for(Objective obj: this.currentObjectives){
            String key = obj.getName();
            if (this.results.containsKey(key)){
                obj.setValue(this.results.get(key));
            }

            finalResults.add(obj);
        }
  
        return finalResults;
    }

    /**
     * Adds additional objectives needed to compute the objective received as
     * parameter
     * e.g: powerconsumption = sum(core_0_total_power + ... + core_n_total_power)
     *
     * @param objectiveName
     * @return the list with the additional objectives
     */
    @Override
    protected LinkedList<String> getRealSimulatorObjective(String objectiveName){
        LinkedList<String> alObjectives = new LinkedList<String>();

        alObjectives.add(objectiveName);

        return alObjectives;
    }
}
