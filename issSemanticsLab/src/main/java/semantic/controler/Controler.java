package semantic.controler;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import semantic.model.DoItYourselfModel;
import semantic.model.IModelFunctions;
import semantic.model.ObservationEntity;
import semantic.model.SemanticModel;
import semantic.view.JSONEndpoint;

public class Controler
{
	private SemanticModel model;
	private IModelFunctions customModel;
	private IControlFunctions customControl;
	
	public Controler()
	{
		// TODO : Change the path to the one to your own ontology
		this.model = new SemanticModel("./tp-iss.ttl");
		this.customModel = new DoItYourselfModel(this.model);
		this.customControl = new DoItYourselfControl(this.model, this.customModel);
		this.initializeContext();
	}
	
	private void initializeContext()
	{
		this.customModel.createPlace("Aarhus");
	}
	
	public void exportModel(String path)
	{
		this.model.exportModel(path);
	}
	
	public SemanticModel getModel()
	{
		return this.model;
	}
	
	public IModelFunctions getCustomModel()
	{
		return this.customModel;
	}
	
	public IControlFunctions getCustomControl()
	{
		return this.customControl;
	}
	
	public static void main(String[] args) 
	{
		BasicConfigurator.configure();
		Controler c = new Controler();
		try
		{
			// TODO : Change the path to the one of the file you downloaded
			List<ObservationEntity> obsList = JSONEndpoint.parseObservations("./data/tempm.txt");
			String tempURI = c.model.getEntityURI("Temperature").get(0);
			c.getCustomControl().instantiateObservations(obsList, tempURI);
			// TODO : Same thing, for humidity
			obsList = JSONEndpoint.parseObservations("tempm.txt");
			String humidityURI = c.model.getEntityURI("Température").get(0);
			c.getCustomControl().instantiateObservations(obsList, humidityURI);
			// TODO : Change the path so that it points where you want the export to be done
			c.exportModel("export.ttl");
			System.out.println("Export Done");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
