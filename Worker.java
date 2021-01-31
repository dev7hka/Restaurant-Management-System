


public class Worker {

    private String name;
    private Double salary;


    public Worker(String name, Double salary){

        this.name = name;
        this.salary = salary;

    }

    public String getName(){

        return this.name;
    }
    public Double getSalary(){
        return this.salary;
    }
    public void get_info(){

        System.out.printf("Name : %s  Salary : %.3f\n" , getName(), getSalary());
    }


}
