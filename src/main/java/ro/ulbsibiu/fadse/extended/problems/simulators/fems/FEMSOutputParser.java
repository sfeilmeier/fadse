package ro.ulbsibiu.fadse.extended.problems.simulators.fems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.ulbsibiu.fadse.extended.problems.simulators.SimulatorBase;
import ro.ulbsibiu.fadse.extended.problems.simulators.SimulatorOutputParser;

public class FEMSOutputParser extends SimulatorOutputParser {
	private Logger logger = LoggerFactory.getLogger(FEMSOutputParser.class);	
	
	public FEMSOutputParser(SimulatorBase simulator) {
		super(simulator);
		logger.info("Constructor");
	}
}
