public class Waiter extends Worker {

    private int in_service ;
    private int table_count;
    private int order_count;
    public Waiter(String name, Double salary) {
        super(name, salary);
        this.in_service = 0;
        this.table_count = 0;
        this.order_count = 0;
    }

    public int getIn_service() {
        return this.in_service;
    }
    public void setIn_service(){
        ++this.in_service;
    }
    public void decrease_in_service(){
        --this.in_service;
    }
    public int getTable_count(){
        return this.table_count;
    }
    public void riseTableCount(){
        ++this.table_count;
    }
    public void riseOrderCount(){
        ++this.order_count;
    }
    public int getOrder_count(){
        return this.order_count;
    }
    public void calculate_salary(){

        System.out.print("Salary for "+this.getName() + ": ");
        System.out.println(this.getSalary() + (this.getSalary() * getOrder_count() * 0.05));
    }

}
