package project1.src.taxify;

import java.time.LocalDate;

public interface IUser {

    public int getId();
    public String getFirstName();
    public String getLastName();
    public char getGender();
    public LocalDate getBirthDate();
    public boolean getService();
    public void setService(boolean service);
    public void setCompany(ITaxiCompany company);
    public void requestService(ServiceType s);
    public void rateService(IService service);
    public String toString();

}