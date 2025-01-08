package model;

public class TaskDipendente {

	Task t;
	Dipendente d;
	
	public TaskDipendente(Task t, Dipendente d) {
		this.t=t;
		this.d=d;
	}
	
	public Task getT() {
		return t;
	}
	public void setT(Task t) {
		this.t = t;
	}
	public Dipendente getD() {
		return d;
	}
	public void setD(Dipendente d) {
		this.d = d;
	}
	
	
	
}
