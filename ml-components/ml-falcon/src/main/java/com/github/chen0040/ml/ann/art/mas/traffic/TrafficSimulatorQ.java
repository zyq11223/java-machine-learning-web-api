package com.github.chen0040.ml.ann.art.mas.traffic;

import com.github.chen0040.ml.ann.art.falcon.Falcon;
import com.github.chen0040.ml.ann.art.falcon.FalconConfig;
import com.github.chen0040.ml.ann.art.falcon.TDFalcon;
import com.github.chen0040.ml.ann.art.mas.traffic.agents.TDFalconTrafficAgent;
import com.github.chen0040.ml.ann.art.mas.traffic.agents.TrafficAgent;

/**
 * Created by chen0469 on 10/1/2015 0001.
 */
public class TrafficSimulatorQ extends TrafficSimulator {

    public TrafficSimulatorQ(TrafficSimulatorConfig config, FalconConfig falconConfig){
        super(config, falconConfig);
    }

    @Override
    protected Falcon createBrain(){
        TDFalcon brain = new TDFalcon(falconConfig);
        return brain;
    }

    @Override
    protected TrafficAgent createAgent(int agentId, Falcon brain){
        int numSonarInput = config.numSonarInput;
        int numAVSonarInput = config.numAVSonarInput;
        int numRangeInput = config.numRangeInput;

        TDFalconTrafficAgent newAgent = new TDFalconTrafficAgent((TDFalcon)brain, agentId, numSonarInput, numAVSonarInput, numRangeInput);
        newAgent.useImmediateRewardAsQ = false;

        if(config.isImmediateRewardProvided()){
            newAgent.setQGamma(0.5);
        } else {
            newAgent.setQGamma(0.9);
        }

        return newAgent;
    }

    public static void main(String[] args){
        TrafficSimulatorConfig config = new TrafficSimulatorConfig();
        config.setImmediateRewardProvided(true);
        config.setNumAgents(30);


        FalconConfig falconConfig = new FalconConfig();
        falconConfig.numAction = TrafficAgent.numAction;
        falconConfig.numState = config.numState();
        falconConfig.numReward = 2;
        falconConfig.isBounded = false;

        TrafficSimulatorQ simulator = new TrafficSimulatorQ(config, falconConfig);
        simulator.runSims();
    }
}