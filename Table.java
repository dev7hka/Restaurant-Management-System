import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Table {


    private int id;
    private int capacity;
    private boolean status;
    private String creator;
    private String waiter;
    private LinkedHashMap<String, Integer> items ;
    private ArrayList<Integer> order_count;
    public Table(int id, int capacity, String creator) {
        this.id = id;
        this.capacity = capacity;
        this.creator = creator;
        this.status = false;
        this.items = new LinkedHashMap<>();
        this.order_count = new ArrayList<>();

    }
    public int getCapacity(){
        return this.capacity;
    }
    public int getId(){
        return this.id;
    }
    public String getWaiter(){
        return this.waiter;
    }
    public boolean getStatus(){
        return this.status;
    }
    public void setStatus(){
        this.status = true;
    }
    public void setItems(String name){
        if (items.get(name) == null)
            items.put(name,1);
        else
            items.replace(name, items.get(name)+1);
    }
    public int getItemAmount(String name){
        if (items.get(name) == null)
            return -1;
        else
            return items.get(name);
    }
    public void check_out(ArrayList<Item> item){
        Double[] total = {0.0};
        items.forEach((name, amount) -> {
            int i = 0; while (!item.get(i).getName().equals(name)) i++;
            System.out.printf("%s:\t %.3f x (%d) %.3f $\n",name, item.get(i).getCost() , amount, (item.get(i).getCost()) * amount );
            total[0] += (item.get(i).getCost() * amount);
        });
        System.out.printf("Total : \t%.3f $\n",total[0]);
        this.status = false;
        this.items.clear();
        this.waiter = null;
        this.order_count.clear();
    }
    public void allocate_table(String waiter)
    {
        this.waiter = waiter;
    }
    public void getOrderStatus(){

            System.out.println("\t"+order_count.size() + " order(s)");
            for (Integer integer : order_count)
                System.out.println("\t\t"+ integer + " item(s)");

    }
    public void setOrder_count(int count){
        this.order_count.add(count);
    }
    public int getOrderCount(){
        return this.order_count.size();
    }

}
