package semantic.model;

import java.util.List;

public class DoItYourselfModel implements IModelFunctions
{
	IConvenienceInterface model;
	
	public DoItYourselfModel(IConvenienceInterface m) {
		this.model = m;
	}

	@Override
	public String createPlace(String name) {
		String placeURI = model.getEntityURI("Lieu").get(0);
		String URI = model.createInstance(name, placeURI);
		return URI;
	}

	@Override
	public String createInstant(TimestampEntity instant) {
		String instantURI = model.getEntityURI("Instant").get(0);
		List<String> instantLabels = model.listLabels(instantURI);
		String value = instant.timestamp;
		for(int i = 0; i < instantLabels.size(); i++) {
			//s'il existe déjà on fait rien
			if(instantLabels.get(i) == value) {
				return null;
			}
		}
		
		String URI = model.createInstance(value, instantURI);
		//On lui rajoute son attribut
		String timestampURI = model.getEntityURI("a pour timestamp").get(0);
		model.addDataPropertyToIndividual(URI, timestampURI, value);
		return URI;
	}

	@Override
	public String getInstantURI(TimestampEntity instant) {
		//On récupère l'URI de l'instant
		String instantURI = model.getEntityURI("Instant").get(0);	
		String URI = null;
		List<String> instantList = model.getInstancesURI(instantURI);
		for(int i = 0; i < instantList.size(); i++) {
			//On vérifie s'il existe et on récupere son timestamp s'il existe donc
			String timestampURI = model.getEntityURI("a pour timestamp").get(0);
			if(model.hasDataPropertyValue(instantList.get(i), timestampURI, instant.timestamp)) {
				URI = model.getInstancesURI(instantURI).get(i);
			}
		}
		
		return URI;
	}

	@Override
	public String getInstantTimestamp(String instantURI)
	{
		String timestamp = null;
		List<List<String>> instantProperties = model.listProperties(instantURI);
		//On parcours les proprietés de l'URI
		for(int i = 0; i < instantProperties.size(); i++) {
			String timestampURI = model.getEntityURI("a pour timestamp").get(0);
			if(instantProperties.get(i).get(0).equals(timestampURI)) {
				timestamp = instantProperties.get(i).get(1);
			}
		}
		
		return timestamp;
	}

	@Override
	public String createObs(String value, String paramURI, String instantURI) {
		String obsURI = null;
		
		obsURI = model.createInstance(value, model.getEntityURI("Observation").get(0));
		model.addObjectPropertyToIndividual(obsURI, model.getEntityURI("a pour date").get(0), instantURI);
		model.addObservationToSensor(obsURI, model.whichSensorDidIt(getInstantTimestamp(instantURI), paramURI));
		model.addObjectPropertyToIndividual(obsURI, model.getEntityURI("mesure").get(0), paramURI);
		model.addDataPropertyToIndividual(obsURI, model.getEntityURI("a pour valeur").get(0), value);
		
		return obsURI;
	}
}
