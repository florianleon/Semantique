package semantic.controler;

import java.util.List;

import semantic.model.IConvenienceInterface;
import semantic.model.IModelFunctions;
import semantic.model.ObservationEntity;
import semantic.model.TimestampEntity;

public class DoItYourselfControl implements IControlFunctions
{
	private IConvenienceInterface model;
	private IModelFunctions customModel;
	
	public DoItYourselfControl(IConvenienceInterface model, IModelFunctions customModel)
	{
		this.model = model;
		this.customModel = customModel;
	}
	
	
	/**
	 * This function parses the list of observations extracted from the dataset, 
	 * and instanciates them in the knowledge base. 
	 * @param obsList
	 * @param phenomenonURI
	 */
	@Override
	public void instantiateObservations(List<ObservationEntity> obsList, String paramURI) {
		
		for(int i = 0; i < obsList.size(); i++) {
			ObservationEntity obs = obsList.get(i);
			String value = obs.getValue().toString();
			TimestampEntity timestamp = obs.getTimestamp();
			String instant = this.customModel.createInstant(timestamp);
			this.customModel.createObs(value, paramURI, instant);
		}
		
	}
}
