public class Employer extends Worker {


    private int table_count;


    public Employer(String name, Double salary) {
        super(name, salary);
        this.table_count = 0;

    }

    public int getTable_count(){

        return this.table_count;
    }
    public void riseTableCount(){
        ++this.table_count;
    }
    public void calculate_salary(){

        System.out.print("Salary for "+this.getName() + ": ");
        System.out.println(this.getSalary() + (this.getSalary() * getTable_count() * 0.1));
    }


}
