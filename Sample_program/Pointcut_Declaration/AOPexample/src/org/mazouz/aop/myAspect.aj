package org.mazouz.aop;
import org.aspectj.lang.annotation.Pointcut;

public aspect myAspect {
	
	pointcut function():
		//call(void org.mazouz.aop.AOPdemo.*(int)); ---- call the any method having only int as a parameter
		//call(void org.mazouz.aop.AOPdemo.*(..)); ------ call the any method having any parameter
		//call(void org.mazouz.aop.AOPdemo.*(int, String)); --------- call the any method having  parameter int and string
		call(void org.mazouz.aop.AOPdemo.methodTwo(String)); // call methodTwo having String as a parameter
	
	before(): function()
	{
		System.out.println("......before aspectJ");
	}
	after(): function()
	{
		System.out.println("aspectJ after .......");
	}
}
