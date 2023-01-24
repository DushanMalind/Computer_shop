package lk.ijse.computer_Shop.model;

public class EmployeesDTO {
    private String empId;
    private String empName;
    private String address;
    private String cusId;

    public EmployeesDTO() {
    }

    public EmployeesDTO(String empId, String empName, String address, String cusId) {
        this.empId = empId;
        this.empName = empName;
        this.address = address;
        this.cusId = cusId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
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
        return "EmployeesDTO{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", address='" + address + '\'' +
                ", cusId='" + cusId + '\'' +
                '}';
    }
}
