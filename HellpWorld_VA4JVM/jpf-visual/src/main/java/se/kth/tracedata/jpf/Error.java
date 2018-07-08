package se.kth.tracedata.jpf;

public class Error implements se.kth.tracedata.Error{
	gov.nasa.jpf.Error jpferror;
	public Error(gov.nasa.jpf.Error jpferror)
	{
		this.jpferror = jpferror;
	}
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return jpferror.getId();
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return jpferror.getDescription();
	}
	@Override
	public String getDetails() {
		// TODO Auto-generated method stub
		return jpferror.getDetails();
	}

}
