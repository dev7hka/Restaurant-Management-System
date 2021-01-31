public class Item {

    private String name;
    private Double cost;
    private int amount;

    public Item(String name, Double cost, int amount){

        this.name = name;
        this.cost = cost;
        this.amount = amount;
    }

    public void add_item(String name, int amount){

        if (this.name.equals(name))
            this.amount += amount;
        else
            System.out.println("No item exists by this name "+name);
    }
    public void get_info(){

        System.out.printf("%s:\t %d\n", this.name, this.amount);
    }
    public int getAmount(){
        return this.amount;
    }
    public void setAmount(){
        --this.amount;
    }
    public Double getCost(){
        return this.cost;
    }
    public String getName(){
        return this.name;
    }

}
