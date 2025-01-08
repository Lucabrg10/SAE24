package model;

public class TaskDipendente {

	Task task;
	Dipendente dipendente;
	String status;
	
	public TaskDipendente(Task t, Dipendente d) {
		this.task=t;
		this.dipendente=d;
		this.status="ASSEGNATA";
	}
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task t) {
		this.task = t;
	}
	public Dipendente getDipendente() {
		return dipendente;
	}
	public void setDipendente(Dipendente d) {
		this.dipendente = d;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
