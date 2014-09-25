package ro.ulbsibiu.fadse.extended.problems.simulators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.ulbsibiu.fadse.environment.Environment;
import ro.ulbsibiu.fadse.extended.problems.simulators.fems.FEMSOutputParser;
import ro.ulbsibiu.fadse.extended.problems.simulators.fems.FEMSRunner;

/*
 * @author Stefan Feilmeier
 */
public class FEMSSimulator extends SimulatorBase {
	private static final long serialVersionUID = -6498432619185702033L;
	private Logger logger = LoggerFactory.getLogger(FEMSSimulator.class);	
	
	public FEMSSimulator(Environment environment) throws ClassNotFoundException {
		super(environment);
		logger.info("Constructor");
        this.simulatorOutputFile = environment.getInputDocument().getSimulatorParameter("simulator_output_file");
        this.simulatorOutputParser = new FEMSOutputParser(this);
        this.simulatorRunner = new FEMSRunner(this);
        logger.info(this.toString());
	}
}
