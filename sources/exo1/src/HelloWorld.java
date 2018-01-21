/***
 * Julia: France Telecom's implementation of the Fractal API
 * Copyright (C) 2001-2002 France Telecom R&D
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Contact: Eric.Bruneton@rd.francetelecom.com
 *
 * Author: Eric Bruneton
 */

import org.objectweb.fractal.api.Component;
import org.objectweb.fractal.api.factory.GenericFactory;
import org.objectweb.fractal.api.type.ComponentType;
import org.objectweb.fractal.api.type.InterfaceType;
import org.objectweb.fractal.api.type.TypeFactory;

import org.objectweb.fractal.util.Fractal;

public class HelloWorld {

  public static void main (final String[] args) throws Exception {

      Component boot = Fractal.getBootstrapComponent();
      TypeFactory tf = Fractal.getTypeFactory(boot);
      GenericFactory cf = (GenericFactory)boot.getFcInterface ("generic-factory");
      // type of root component
      ComponentType rType = tf.createFcType(new InterfaceType[] {
	      tf.createFcItfType("m", "Main", false, false, false)
	  });
      // type of client component
      ComponentType cType = tf.createFcType(new InterfaceType[] {
              tf.createFcItfType("m", "Main", false, false, false),
              tf.createFcItfType("s", "Service", true, false, false)
      });
      // type of server component
      ComponentType sType = tf.createFcType(new InterfaceType[] {
	      tf.createFcItfType("s", "Service", false, false, false),
	      tf.createFcItfType("attribute-controller","ServiceAttributes",false,false,false)
	  });
      
      // create root component
      Component rComp = cf.newFcInstance(rType, "composite", null);
      // create client component
      Component cComp = cf.newFcInstance(cType, "primitive", "ClientImpl");
      // create server component
      Component sComp = cf.newFcInstance(sType, "primitive", "ServerImpl");
      ((ServiceAttributes)Fractal.getAttributeController(sComp)).setHeader("-> ");
      ((ServiceAttributes)Fractal.getAttributeController(sComp)).setCount(1);
      
      // component assembly
      Fractal.getContentController(rComp).addFcSubComponent(cComp);
      Fractal.getContentController(rComp).addFcSubComponent(sComp);
      Fractal.getBindingController(rComp).bindFc("m", cComp.getFcInterface("m"));
      Fractal.getBindingController(cComp).bindFc("s", sComp.getFcInterface("s"));
      
      
      // start root component
      Fractal.getLifeCycleController(rComp).startFc();
      
      // call main method
      ((Main)rComp.getFcInterface("m")).main(null);
  }
}
