/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.ulbsibiu.fadse.extended.base.stopCondition;

import ro.ulbsibiu.fadse.environment.Environment;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Horia
 */
public abstract class StopCondition {
protected Environment environment;
    public StopCondition(Environment environment) {
        this.environment = environment;
    }
    
    abstract public boolean stopConditionFulfilled(List<File> listOfPopulationFiles);

}
