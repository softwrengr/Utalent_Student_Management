package utalent.square.developer.utalent.Models;

public class AddStudentModel {
String std_id;
String std_name;
String std_tel;
String parent_tel;
String std_address;
String subject;
String remarks;
String total_fee;

    public String getStd_id() {
        return std_id;
    }
    public void setStd_id(String std_id) {
        this.std_id = std_id;
    }

    public String getStd_name() {
        return std_name;
    }
    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }

    public String getStd_tel() {
        return std_tel;
    }
    public void setStd_tel(String std_tel) {
        this.std_tel = std_tel;
    }

    public String getParent_tel() {
        return parent_tel;
    }

    public void setParent_tel(String parent_tel) {
        this.parent_tel = parent_tel;
    }

    public String getStd_address() {
        return std_address;
    }
    public void setStd_address(String std_address) {
        this.std_address = std_address;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }
}
