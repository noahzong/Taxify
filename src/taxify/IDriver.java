package project1.src.taxify;

import java.time.LocalDate;

public interface IDriver {
	public int getId();
    public String getFirstName();
    public String getLastName();
    public char getGender();
    public LocalDate getBirthDate();
	public String getYOE();
	public boolean getService();
	public void setService(boolean service);
	public ITaxiCompany getCompany();
	public void setCompany(ITaxiCompany company);
	public String toString();
	
}
