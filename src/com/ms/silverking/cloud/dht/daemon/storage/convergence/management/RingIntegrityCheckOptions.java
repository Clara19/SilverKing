package com.ms.silverking.cloud.dht.daemon.storage.convergence.management;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Option;

import com.google.caliper.internal.guava.collect.ImmutableList;
import com.ms.silverking.collection.Pair;

public class RingIntegrityCheckOptions {
    RingIntegrityCheckOptions() {
	}
	
	@Option(name="-G", usage="GridConfigBase", required=false)
	String	gridConfigBase;
	
	@Option(name="-g", usage="GridConfig", required=false)
	String	gridConfig;
	
	@Option(name="-s", usage="ExclusionSet", required=false)
	String	exclusionSet;
	
	@Option(name="-f", usage="ExclusionSetFile", required=false)
	String	exclusionSetFile;
	
	@Option(name="-u", usage="Union", required=false)
	boolean	union;
	
	@Option(name="-r", usage="RingAndVersionPair", required=false)
	String	ringAndVersionPair;
	
	@Option(name="-serverFailureProbability", usage="ServerFailureProbability", required=false)
	double	serverFailureProbability;
	
	@Option(name="-serverFailureProbabilities", usage="ServerFailureProbabilities", required=false)
	String	serverFailureProbabilities;
	
	private static final String	sfp_delimiter = ",";
	
	List<Double> getServerFailureProbabilities() {
		if (serverFailureProbabilities == null) {
			return null;
		} else {
			String[]	defs;
			List<Double>	_serverFailureProbabilities;
			
			defs = serverFailureProbabilities.split(sfp_delimiter);
			_serverFailureProbabilities = new ArrayList<>(defs.length);
			for (String def : defs) {
				_serverFailureProbabilities.add(Double.parseDouble(def));
			}
			return _serverFailureProbabilities;
		}
	}
	
	@Option(name="-lossEstimationParameters", usage="LossEstimationParameters", required=false)
	String	lossEstimationParameters;
	
	private static final String	le_numServersNumSimulationsDelimiter = ":";
	private static final String	le_numServersDelimiter = ",";
	private static final int	le_defaultSimulations = 1000;
	
	Pair<List<Integer>,Integer> getLossEstimationParameters() {
		if (lossEstimationParameters == null) {
			return new Pair<>(ImmutableList.of(), 0);
		} else {
			Pair<List<Integer>,Integer>	_lossEstimationParameters;
			String[]	toks;
			String[]	numServerDefs;
			List<Integer>	numServers;
			int	numSimulations;
			
			toks = lossEstimationParameters.split(le_numServersNumSimulationsDelimiter);
			if (toks.length == 0) {
				numSimulations = le_defaultSimulations;
			} else if (toks.length == 2) {
				numSimulations = Integer.parseInt(toks[1]);
			} else {
				throw new RuntimeException("Bad loss estimation parameters");
			}
			
			numServerDefs = toks[0].split(le_numServersDelimiter);
			numServers = new ArrayList<>(numServerDefs.length);
			for (String numServerDef : numServerDefs) {
				numServers.add(Integer.parseInt(numServerDef));
			}
			return new Pair<>(numServers, numSimulations);
		}
	}
}
