/*
 * This file is part of the FADSE tool.
 * 
 *   Authors: Horia Andrei Calborean {horia.calborean at ulbsibiu.ro}
 *   Copyright (c) 2009-2010
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without modification,
 *   are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 * 
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 * 
 *   The names of its contributors NOT may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *   THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *   PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 *   OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *   WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 *   OF THE POSSIBILITY OF SUCH DAMAGE.

 */
/** Part of this file is:
 * Copyright 2010 Neuroph Project http://neuroph.sourceforge.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.ulbsibiu.fadse.extended.nnhelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;

/**
 *
 * @author Horia Calborean
 */
public class CSVTrainingSetImport {

    public static TrainingSet importFromFile(String filePath, int inputsCount, int outputsCount, String separator, boolean hasHeadder)
            throws IOException, FileNotFoundException, NumberFormatException {

        FileReader fileReader = null;

        try {
            TrainingSet trainingSet = new TrainingSet();
            fileReader = new FileReader(new File(filePath));
            BufferedReader reader = new BufferedReader(fileReader);

            String line = "";
            if (hasHeadder) {
                reader.readLine();
            }
            while ((line = reader.readLine()) != null) {
                Vector<Double> inputs = new Vector<Double>();
                Vector<Double> outputs = new Vector<Double>();
                String[] values = line.split(separator);

                if (values[0].equals("")) {
                    continue; // skip if line was empty
                }
                for (int i = 0; i < inputsCount; i++) {
                    inputs.add(Double.valueOf(Double.parseDouble(values[i])));
                }

                for (int i = inputsCount; i < inputsCount + outputsCount; i++) {
                    outputs.add(Double.valueOf(Double.parseDouble(values[i])));
                }

                if (outputsCount > 0) {
                    trainingSet.addElement(new SupervisedTrainingElement(inputs, outputs));
                } else {
                    trainingSet.addElement(new TrainingElement(inputs));
                }
            }

            return trainingSet;

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (IOException ex) {
            if (fileReader != null) {
                fileReader.close();
            }
            ex.printStackTrace();
            throw ex;
        } catch (NumberFormatException ex) {
            fileReader.close();
            ex.printStackTrace();
            throw ex;
        }
    }
}
