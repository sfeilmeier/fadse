/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ulbsibiu.fadse.extended.nnhelper;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.logic.FeedforwardLogic;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.util.csv.CSVFormat;
import org.encog.util.logging.Logging;
import org.encog.util.simple.TrainingSetUtil;

/**
 *
 * @author Horia
 */
public class NNHelper {

    public static void main(final String args[]) {

        Logging.stopConsoleLogging();
        NeuralDataSet trainingSet = TrainingSetUtil.loadCSVTOMemory(CSVFormat.ENGLISH, "test.csv", true, 6, 1);
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 6));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 6));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 1));
        network.setLogic(new FeedforwardLogic());
        network.getStructure().finalizeStructure();
        network.reset();

        System.out.println();
        System.out.println("Training Network");
        final Train train = new Backpropagation(network, trainingSet, 0.7, 0.8);

        int epoch = 1;

        do {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while (epoch<2000);
train.finishTraining();
        // test the neural network
        System.out.println("Neural Network Results:");
        for (NeuralDataPair pair : trainingSet) {
            final NeuralData output = network.compute(pair.getInput());
            System.out.println(
                    pair.getInput().getData(0) + ","+
                    pair.getInput().getData(1) + ","+
                    pair.getInput().getData(2) + ","+
                    pair.getInput().getData(3) + ","+
                    pair.getInput().getData(4) + ","+
                    pair.getInput().getData(5)
                    + ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
        }
    }
}
