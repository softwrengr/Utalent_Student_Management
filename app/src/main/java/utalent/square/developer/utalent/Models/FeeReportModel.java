package utalent.square.developer.utalent.Models;

public class FeeReportModel {
    String monthName;
    String monthlyFee;
    String monthlyTotalStudents;

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(String monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public String getMonthlyTotalStudents() {
        return monthlyTotalStudents;
    }

    public void setMonthlyTotalStudents(String monthlyTotalStudents) {
        this.monthlyTotalStudents = monthlyTotalStudents;
    }
}
