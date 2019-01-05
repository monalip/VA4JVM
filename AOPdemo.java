package org.mazouz.aop;

public class AOPdemo {

	public static void main(String[] args) {
		AOPdemo aop = new AOPdemo();
		aop.methodOne(5);
		aop.methodOne(10,"Hello");
		aop.methodTwo("bye");

	}
	public void methodOne(int var)
	{
		System.out.println("method one (with integer)");
	}
	public void methodOne(int var,String str )
	{
		System.out.println("method one (with integer, string)");
	}
	public void methodTwo(String str)
	{
		System.out.println("method one (with string)");
	}

}
