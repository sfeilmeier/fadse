package ro.ulbsibiu.fadse;

import ro.ulbsibiu.fadse.environment.Environment;
import ro.ulbsibiu.fadse.environment.parameters.CheckpointFileParameter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.util.JMException;
import ro.ulbsibiu.fadse.tools.monitor.SwingMonitor;

/*
 * 
 *
 * This file is part of the FADSE tool.
 *
 *  Authors: Horia Andrei Calborean {horia.calborean at ulbsibiu.ro}, Andrei Zorila
 *  Copyright (c) 2009-2010
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification,
 *  are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  The names of its contributors NOT may be used to endorse or promote products
 *  derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 *  OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 *  OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
/**
 *
 * @author Horia Calborean <horia.calborean at ulbsibiu.ro>
 */
public class Boot {

    public static void main(String[] args) {
        System.out.println("Starting FADSE - Framework for Automatic Design Space Exploration");
        System.out.println("-----------------------------------------------------------------\n");
        // System.setErr(new PrintStream(new LoggingOutputStream(Category.getRoot())));

        ExitInputLister.addExitListener();

        if (args.length > 0 && args[0].equals("client")) {
        	System.out.println("System mode: Client\n");
            BootClient.main(args);
        } else if (args.length > 0 && args[0].equals("monitor")) {
        	System.out.println("System mode: Monitor\n");
            SwingMonitor.main(args);
        } else {
        	System.out.println("System mode: XML\n");
            String currentdir = System.getProperty("user.dir");
            File dir = new File(currentdir);
            String xmlFileName = null;
            
            //xmlFileName = "falsesimin.xml";
            //xmlFileName = "gapdistsimin_radu.xml";
            //xmlFileName = "falsesimin_radu.xml";
            //xmlFileName = "gapsimin_ralf_uau.xml";
            //xmlFileName = "gapsimin_ralf_uau_gaptimize_all.xml";
            //xmlFileName = "gem5_ralf_uau.xml";
            //xmlFileName = "gapsimin_ralf_uau_gaptimize_sspec.xml";
            //xmlFileName = "gapsimin_ralf_uau_gaptimize_inline_sspec.xml";
            //xmlFileName = "gapsimin_ralf_uau_gaptimize_pex.xml";
            //xmlFileName = "gapsimin_ralf_uau_gaptimize_inline_sspec_pex_bp.xml";
            //xmlFileName = "gapsimin_ralf_uau_gaptimize_inline_sspec_combi.xml";
            //xmlFileName = "gapsimin_ralf_uau_qdlru.xml";
            //xmlFileName = "gapsimin_ralf_uau_gaptimize_inline_qdlru.xml";
            //xmlFileName = "combination_testing_dijkstra_nogroups.xml";
            //xmlFileName = "contidistsimin.xml";
            //xmlFileName = "fems_energy_production.xml";
            
            if (args.length > 0) {
                xmlFileName = args[0];
            }
            if (xmlFileName == null) {
            	System.out.println("No XML file provided");
            	return;
            }
            
            String checkpointFile = "";
            String secondFile = "";

            String fuzzyConfigFile ="";
            String environmentConfigFile = dir + System.getProperty("file.separator") + "configs" + System.getProperty("file.separator") + xmlFileName;
            String neighborConfig = dir + System.getProperty("file.separator") + "configs" + System.getProperty("file.separator") + "neighborConfig.xml";
            for (int i = 1; i < args.length; i++) {
                if (args[i].endsWith(".xml")) {
                    neighborConfig = args[i];
                } else if (args[i].endsWith(".csv")) {
                    checkpointFile = args[i];
                } else if (args[i].endsWith(".spd")) { //for SMPSO speed checkpointFile
                    secondFile = args[i];
                } else if (args[i].endsWith(".fcl")) {
                    fuzzyConfigFile = args[i];
                }
            }           

            Environment env = new Environment(environmentConfigFile);
            CheckpointFileParameter checkpointFileParameter = new CheckpointFileParameter(checkpointFile, secondFile);
            env.setCheckpointFileParameter(checkpointFileParameter);
            env.setFuzzyInputFile(fuzzyConfigFile);
            //TODO
            env.setNeighborsConfigFile(neighborConfig);
            //END TODO            
            
            try {
                AlgorithmRunner algRunner = new AlgorithmRunner();
                algRunner.run(env);
            } catch (JMException ex) {
                Logger.getLogger(Boot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(Boot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Boot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Boot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Boot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Boot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
