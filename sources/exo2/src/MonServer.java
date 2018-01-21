import org.objectweb.fractal.api.Component;
import org.objectweb.fractal.api.control.*;
import org.objectweb.fractal.api.factory.*;
import org.objectweb.fractal.api.type.ComponentType;
import org.objectweb.fractal.api.type.InterfaceType;
import org.objectweb.fractal.api.type.TypeFactory;


import org.objectweb.fractal.util.Fractal;

import comanche.*;

public class MonServer {
  public static void main (String[] args) {
  	
  	try {
  		Component boot = Fractal.getBootstrapComponent();

		TypeFactory tf = (TypeFactory)boot.getFcInterface ("type-factory");
		GenericFactory cf = (GenericFactory)boot.getFcInterface ("generic-factory");

		// cr�er le composite
		// interface server "r" / "java.lang.Runnable"
		ComponentType rType = tf.createFcType(new InterfaceType[] {
				tf.createFcItfType("r", "java.lang.Runnable", false, false, false)
		});
		Component rComp = cf.newFcInstance(rType, "composite", null);

		// créer le primitif RequestReceiver
		ComponentType requestReceiverType = tf.createFcType(new InterfaceType[] {
				tf.createFcItfType("r", "java.lang.Runnable", false, false, false),
				tf.createFcItfType("rh", "comanche.RequestHandler", true, false, false),
				tf.createFcItfType("s", "comanche.Scheduler", true, false, false),
		});
		// interface server "r" / "java.lang.Runnable"
		// interface client "rh" / "comanche.RequestHandler"
		// interface client "s" / "comanche.Scheduler"
		// implementation "comanche.RequestReceiver"
		Component requestReceiverComp = cf.newFcInstance(requestReceiverType, "primitive", "comanche.RequestReceiver");
		
		// créer le primitif RequestAnalyzer
		ComponentType requestAnalyzerType = tf.createFcType(new InterfaceType[] {
				tf.createFcItfType("a", "comanche.RequestHandler", false, false, false),
				tf.createFcItfType("l", "comanche.Logger", true, false, false),
				tf.createFcItfType("rh", "comanche.RequestHandler", true, false, false),
		});
		// interface server "a" / "comanche.RequestHandler"
		// interface client "l" / "comanche.Logger"
		// interface client "rh" / "comanche.RequestHandler"
		// implementation "comanche.RequestReceiver"
		Component requestAnalyzerComp = cf.newFcInstance(requestAnalyzerType, "primitive", "comanche.RequestAnalyzer");
		
		// créer le primitif Scheduler
		ComponentType schedulerType = tf.createFcType(new InterfaceType[] {
				tf.createFcItfType("s", "comanche.Scheduler", false, false, false)
		});
		// interface server "s" / "comanche.Scheduler"
		// implementation "comanche.MultiThreadScheduler"
		Component schedulerComp = cf.newFcInstance(schedulerType, "primitive", "comanche.MultiThreadScheduler");

		// créer le primitif Logger
		ComponentType loggerType = tf.createFcType(new InterfaceType[] {
				tf.createFcItfType("l", "comanche.Logger", false, false, false)
		});
		// interface server "l" / "comanche.Logger"
		// implementation "comanche.BasicLogger"
		Component loggerComp = cf.newFcInstance(loggerType, "primitive", "comanche.BasicLogger");
		
		// créer le primitif FileRequestHandler
		ComponentType rhType = tf.createFcType(new InterfaceType[] {
				tf.createFcItfType("rh", "comanche.RequestHandler", false, false, false)
		});
		// interface server "rh" / "comanche.RequestHandler"
		// implementation "comanche.FileRequestHandler"
		Component rhComp = cf.newFcInstance(rhType, "primitive", "comanche.FileRequestHandler");
		
		// ajouter les sous-composants au composite
		Fractal.getContentController(rComp).addFcSubComponent(requestReceiverComp);
		Fractal.getContentController(rComp).addFcSubComponent(requestAnalyzerComp);
		Fractal.getContentController(rComp).addFcSubComponent(loggerComp);
		Fractal.getContentController(rComp).addFcSubComponent(schedulerComp);
		Fractal.getContentController(rComp).addFcSubComponent(rhComp);
		// lier les sous-composants
		Fractal.getBindingController(rComp).bindFc("r", requestReceiverComp.getFcInterface("r"));
		Fractal.getBindingController(requestReceiverComp).bindFc("rh", requestAnalyzerComp.getFcInterface("a"));
		Fractal.getBindingController(requestReceiverComp).bindFc("s", schedulerComp.getFcInterface("s"));
		Fractal.getBindingController(requestAnalyzerComp).bindFc("l", loggerComp.getFcInterface("l"));
		Fractal.getBindingController(requestAnalyzerComp).bindFc("rh", rhComp.getFcInterface("rh"));

		// démarrer
		Fractal.getLifeCycleController(rComp).startFc();
		((Runnable)rComp.getFcInterface("r")).run();
	
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
}
