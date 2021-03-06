/**
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

import java.io.IOException;
import java.util.Vector;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;

/**
 * This sample shows how to create, train, save and load simple Multi Layer Perceptron
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class NNHelper2 {

    /**
     * Runs this sample
     */
    public static void main(String[] args) throws IOException {

        // create training set (logical XOR function)
        TrainingSet trainingSet = CSVTrainingSetImport.importFromFile("c:\\train.csv", 6, 1, ",", true);
        TrainingSet testSet = CSVTrainingSetImport.importFromFile("c:\\test.csv", 6, 1, ",", true);

        // create multi layer perceptron
        MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.TANH, 6, 10, 1);
        myMlPerceptron.setLearningRule(new BackPropagation());
        // learn the training set
        System.out.println("Training neural network...");
        myMlPerceptron.learnInNewThread(trainingSet);
        // test perceptron
        System.out.println("Testing trained neural network");
        testNeuralNetwork(myMlPerceptron, trainingSet);

        // save trained neural network
        myMlPerceptron.save("myMlPerceptron.nnet");

        // load saved neural network
        NeuralNetwork loadedMlPerceptron = NeuralNetwork.load("myMlPerceptron.nnet");

        // test loaded neural network
        System.out.println("Testing loaded neural network");
        testNeuralNetwork(loadedMlPerceptron, testSet);
    }

    /**
     * Prints network output for the each element from the specified training set.
     * @param neuralNet neural network
     * @param trainingSet training set
     */
    public static void testNeuralNetwork(NeuralNetwork neuralNet, TrainingSet trainingSet) {

        for(TrainingElement trainingElement : trainingSet.trainingElements()) {
            neuralNet.setInput(trainingElement.getInput());
            neuralNet.calculate();
            Vector<Double> networkOutput = neuralNet.getOutput();

            System.out.print("Input: " + trainingElement.getInput());
            System.out.println(" Output: " + networkOutput);
        }
    }

}
