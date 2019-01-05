package se.kth.tracedata.jpf;

public class ChoiceGenerator<T> implements se.kth.tracedata.ChoiceGenerator<T> {
	
	gov.nasa.jpf.vm.ChoiceGenerator<?> jpfChoicegen;
	
	public ChoiceGenerator(gov.nasa.jpf.vm.ChoiceGenerator<?> jpfChoicegen) 
	{
		this.jpfChoicegen= jpfChoicegen;
		
	}

	@Override
	public String getId() {
		return jpfChoicegen.getId();
	}

	@Override
	public int getTotalNumberOfChoices() {
		return jpfChoicegen.getTotalNumberOfChoices();
	}
	 //boolean method is created to check choicegenerator is instace of ThreadChoiceFromSet
	
	@Override
	public boolean isInstaceofThreadChoiceFromSet() {
		
		return (jpfChoicegen instanceof gov.nasa.jpf.vm.choice.ThreadChoiceFromSet);
	}
	@Override
	public ThreadInfo getChoice(int idx) {
<<<<<<< HEAD
		// TODO Auto-generated method stub
=======
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d
		gov.nasa.jpf.vm.ThreadInfo threainfo = ((gov.nasa.jpf.vm.choice.ThreadChoiceFromSet)jpfChoicegen).getChoice(idx);
		return new ThreadInfo(threainfo);
	}

	@Override
	public ThreadInfo[] getChoices() {
<<<<<<< HEAD
		// TODO Auto-generated method stub
=======
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d
		gov.nasa.jpf.vm.ThreadInfo[] list =((gov.nasa.jpf.vm.choice.ThreadChoiceFromSet)jpfChoicegen).getAllChoices();
		//list is of array of threadinfo which is of type gov.nasa.jpf.vm.ThreadInfo 
		// so convert whole array of se.kth.tracedata.ThreadInfo we need to access each element and convert it to our type by using adapter
		//add that element to array of our type 
		ThreadInfo[] threadlist = new ThreadInfo[list.length];
		
		
		for(int i =0;i<list.length;i++)
		{
			threadlist[i] = new ThreadInfo(list[i]);
		}
		 return threadlist; 
	}
<<<<<<< HEAD
=======
	@Override
	//setting the id based on the method name call start join
	public int getIdPriority()
	{
		return 0;
	}
	@Override
	public long getThreadID()
	{
		return 0;
	}
	
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d


}
