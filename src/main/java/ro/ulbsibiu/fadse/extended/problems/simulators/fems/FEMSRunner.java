package ro.ulbsibiu.fadse.extended.problems.simulators.fems;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.ulbsibiu.fadse.environment.parameters.Parameter;
import ro.ulbsibiu.fadse.extended.problems.simulators.SimulatorBase;
import ro.ulbsibiu.fadse.extended.problems.simulators.SimulatorRunner;

public class FEMSRunner extends SimulatorRunner {
	private Logger logger = LoggerFactory.getLogger(FEMSRunner.class);	
	
	public FEMSRunner(SimulatorBase simulator) {
		super(simulator);
		logger.info("Constructor");
	}

	@Override
	public void run() {
		logger.info("run");
		String outputFile = simulator.getSimulatorOutputFile();
		logger.info("Current Output file: " + outputFile);

		Path scriptFile = Paths.get(Paths.get(outputFile).getParent().toString(), "script.m");
		try {
			Files.createDirectory(Paths.get(outputFile).getParent());
			logger.info("Script: " + scriptFile.toAbsolutePath());
			String script = generateScript();			
			Files.write(scriptFile, script.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*DEMO*/
		Path resultsFile = Paths.get(outputFile);
		try {
			logger.info("Result: " + resultsFile.toAbsolutePath());
			Integer value = (Integer) individual.getParameters()[0].getValue();
			logger.info("Result: " + value);
			Files.write(resultsFile, new String("prediction_error: " + value).getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Generates a Matlab script
	private String generateScript() {
		String ret = "";
		for(Parameter parameter : individual.getParameters()) {
			logger.info(parameter.getName() + " - " + parameter.getValue());
			ret += parameter.getName() + " - " + parameter.getValue() + "\n";
		}
		return ret;
	}
}
