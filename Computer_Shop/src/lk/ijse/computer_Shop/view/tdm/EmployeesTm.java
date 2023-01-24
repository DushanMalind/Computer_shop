package lk.ijse.computer_Shop.view.tdm;

public class EmployeesTm {
    private String Id;
    private String empName;
    private String address;
    private String cusId;

    public EmployeesTm() {
    }

    public EmployeesTm(String id, String empName, String address, String cusId) {
        Id = id;
        this.empName = empName;
        this.address = address;
        this.cusId = cusId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    @Override
    public String toString() {
        return "EmployeesTm{" +
                "Id='" + Id + '\'' +
                ", empName='" + empName + '\'' +
                ", address='" + address + '\'' +
                ", cusId='" + cusId + '\'' +
                '}';
    }
}
