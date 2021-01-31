import java.io.*;
import java.util.ArrayList;

public class Main {
    private static final int MAX_EMPLOYER = 5;
    private static final int MAX_WAITER = 5;
    private static final int MAX_TABLES = 5;
    private static final int MAX_TABLE_SERVICES = 3;
    private static final int ALLOWED_MAX_TABLES = 2;
    private static final int MAX_ITEM = 10;
    private static final int ALLOWED_MAX_ORDER = 5;

    public static void main(String[] args) {

        ArrayList<Item> item = new ArrayList<>();
        Employer[] employer = new Employer[MAX_EMPLOYER];
        Waiter[] waiter = new Waiter[MAX_WAITER];
        Table[] table = {null,null,null,null,null};
        ArrayList<String[]> setup = new ArrayList<>();
        ArrayList<String[]> command = new ArrayList<>();

        try {
            String setup_dir = System.getProperty("user.dir");
            setup_dir += "/setup.dat";
            File file = new File(setup_dir);
            BufferedReader set_read = new BufferedReader(new FileReader(file));
            for (String x = set_read.readLine(); x != null; x = set_read.readLine()) {
                String[] temp = x.split(";|\\s");
                setup.add(temp);
            }

            for (String[] strings : setup) {

                String tmp = strings[0];
                switch (tmp) {
                    case "add_item":
                        add_item(strings[1], Double.parseDouble(strings[2]), Integer.parseInt(strings[3]), item);
                        break;
                    case "add_employer":
                        add_employer(strings[1], Double.parseDouble(strings[2]), employer);
                        break;
                    case "add_waiter":
                        add_waiter(strings[1], Double.parseDouble(strings[2]), waiter);
                        break;
                    default:
                        System.out.println("Wrong input in setup file !!!");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("File I/O error !!!");
        }

        try {
            String command_dir = System.getProperty("user.dir");
            command_dir+= "/commands.dat";
            File file = new File(command_dir);
            BufferedReader com_read = new BufferedReader(new FileReader(file));
            for (String y = com_read.readLine(); y != null; y = com_read.readLine()) {
                String[] temp2 = y.split(";|:|\\s");
                command.add(temp2);
            }
            for (String[] strings : command) {

                String tmp = strings[0];
                switch (tmp) {
                    case "create_table":
                        create_table(strings[1], Integer.parseInt(strings[2]), employer, table);
                        break;
                    case "new_order":{
                        ArrayList<String[]>items = new ArrayList<>();
                        int i = 3;
                        for (; i < strings.length ; i++) {
                            String[] temp_item = strings[i].split("-");
                            items.add(temp_item);
                        }
                        new_order(strings[1], Integer.parseInt(strings[2]), items, item, waiter, table);
                        break;
                    }
                    case "add_order":{
                        ArrayList<String[]>items = new ArrayList<>();
                        int i = 3;
                        for (; i < strings.length ; i++) {
                            String[] temp_item = strings[i].split("-");
                            items.add(temp_item);
                        }
                        add_order(strings[1], Integer.parseInt(strings[2]), items, item, waiter, table);
                        break;
                    }
                    case "check_out":
                        check_out(strings[1], Integer.parseInt(strings[2]), item, waiter, table);
                        break;
                    case "stock_status":
                        stock_status(item);
                        break;
                    case "get_table_status":
                        get_table_status(table);
                        break;
                    case "get_order_status":
                        get_order_status(table);
                        break;
                    case "get_employer_salary":
                        get_employer_salary(employer);
                        break;
                    case "get_waiter_salary":
                        get_waiter_salary(waiter);
                        break;
                    default:
                        System.out.println("Wrong input in command file !!!");
                        break;
                }
            }
        }catch (IOException e) {
            System.out.println("File I/O error !!!");
        }
    }

    private static void add_item(String name, Double cost, int amount, ArrayList<Item> item){

        if(item.size() == 0)
            item.add(new Item(name,cost, amount));
        else {
            int i = 0, result = -1;
            for (; i < item.size(); i++) {
                if(item.get(0).getName().equals(name)){
                    result = i;
                    break;
                }
            }
            if (result == -1)
                item.add(new Item(name, cost, amount));
            else
                item.get(i).add_item(name, amount);
        }
    }

    private static void add_employer(String name, Double salary, Employer[] employer){
        int i = 0, ind = -1;
        for (; i < MAX_EMPLOYER ; i++) {
            if (employer[i] == null) {
                ind = i;
                break;
            }
        }
        for ( i = 0; i < MAX_EMPLOYER ; i++){
            if (employer[i] != null){
                if(employer[i].getName().equals(name)) {
                    System.out.println("There is already a employer by this name " + name);
                    return;
                }
            }
        }
        if (ind != -1)
            employer[ind] = new Employer(name, salary);
        else
            System.out.println("Maximum Employer limit is not allowed to exceed !!!");

    }
    private static void add_waiter(String name, Double salary, Waiter[] waiter){

        int i = 0, ind = -1;
        for (; i < MAX_WAITER ; i++) {
            if (waiter[i] == null) {
                ind = i;
                break;
            }
        }
        for (i = 0 ; i < MAX_WAITER; i++ ){
            if (waiter[i] != null) {
                if (waiter[i].getName().equals(name)) {
                    System.out.println("There is already a waiter by this name " + name);
                    return;
                }
            }
        }
        if (ind != -1)
            waiter[ind] = new Waiter(name, salary);
        else
            System.out.println("Maximum Waiter limit is not allowed to exceed !!!");

    }
    private static void create_table(String emp_name, int capacity, Employer[] employer, Table[] table){

        int i, index = -1 ;
        for (i = 0 ; i < employer.length ; i++){
            if (employer[i] == null)
                continue;
            if (employer[i].getName().equals(emp_name)){
                index = i;
                break;
            }
        }
        System.out.println("***********************************\nPROGRESSING COMMAND: create_table");
        if (index == -1)
            System.out.println("There is no employer named "+emp_name);
        else{
            if (employer[index].getTable_count() == ALLOWED_MAX_TABLES)
                System.out.println(emp_name+ " has already created "+ALLOWED_MAX_TABLES + " tables!");
            else{
                for (i = 0 ; i < table.length ; i++ ) {
                    if (table[i] == null) {
                        table[i] = new Table(i, capacity, emp_name);
                        System.out.println("A new table has successfully been added");
                        employer[index].riseTableCount();
                        return;
                    }
                }
                System.out.println(emp_name + " is not allowed to exceed max. number of tables, " + MAX_TABLES);
            }
        }
    }
    private static void new_order(String wait_name, int customer, ArrayList<String[]> items, ArrayList<Item> item, Waiter[] waiter, Table[] table) {

        int i, waiter_index = -1;
        for (i = 0; i < waiter.length; i++) {
            if (waiter[i] == null)
                continue;
            if (waiter[i].getName().equals(wait_name)) {
                waiter_index = i;
                break;
            }
        }
        System.out.println("***********************************\nPROGRESSING COMMAND: new_order");
        if (waiter_index == -1)
            System.out.println("There is no waiter named " + wait_name);
        else if (waiter[waiter_index].getIn_service() == MAX_TABLE_SERVICES)
            System.out.println("Not allowed to service max. number of tables, " + MAX_TABLE_SERVICES);
        else {
            int table_index = -1;
            for (i = 0; i < table.length; i++) {
                if (table[i] != null && !table[i].getStatus()) {
                    if (table[i].getCapacity() >= customer){
                        table_index = i;
                        break;
                    }
                }
            }
            if (table_index == -1)
                System.out.println("There is no appropriate table for this order!");
            else if (table[table_index].getCapacity() < customer)
                System.out.println("There is no appropriate table for this order!");
            else {
                System.out.println("Table (= ID "+ table_index+ ") has been taken into service");
                table[table_index].setStatus();
                table[table_index].allocate_table(wait_name);
                waiter[waiter_index].riseTableCount();
                waiter[waiter_index].setIn_service();
                int item_count = 0;
                for (String[] strings : items) {
                    i = 0;
                    int item_index = -1;
                    for (; i < item.size(); i++) {
                        if ((item.get(i).getName().equals(strings[0]))) {
                            item_index = i;
                            break;
                        }
                    }
                    if (item_index == -1)
                        System.out.println("Unknown item  " + strings[0]);
                    else if (item.get(item_index).getAmount() == 0)
                        System.out.println(strings[0] + " is not left.");
                    else {
                        int item_num = Integer.parseInt(strings[1]);
                        while (item.get(item_index).getAmount() != 0 && item_num != 0) {

                            if(item_count == MAX_ITEM) {
                                System.out.println("Max item number is not allowed to exceed !!!");
                                --item_num;
                            }
                            else{
                            item.get(item_index).setAmount();
                            System.out.println("Item " + strings[0] + " added into order");
                            table[table_index].setItems(strings[0]);
                            --item_num;
                            ++item_count;}

                        }
                        while (item_num != 0) {
                            System.out.println("Sorry! No " + strings[0] + " in the stock!");
                            --item_num;
                        }
                    }
                }
                if (item_count != 0){
                    waiter[waiter_index].riseOrderCount();
                    table[table_index].setOrder_count(item_count);
                }
            }
        }
    }
    private static void add_order(String wait_name, int table_id, ArrayList<String[]> items, ArrayList<Item> item, Waiter[] waiter, Table[] table){
        int i, waiter_index = -1;
        for (i = 0; i < waiter.length; i++) {
            if (waiter[i] == null)
                continue;
            if (waiter[i].getName().equals(wait_name)) {
                waiter_index = i;
                break;
            }
        }
        System.out.println("***********************************\nPROGRESSING COMMAND: add_order");

        if (waiter_index == -1)
            System.out.println("There is no Waiter named " + wait_name);
        else if(table[table_id] == null)
            System.out.println("There is no table by this ID : "+table_id);
        else if (!wait_name.equals(table[table_id].getWaiter()) || !table[table_id].getStatus())
            System.out.println("This table is either not in service now or "+ wait_name + " cannot be assigned this table!");
        else if(table[table_id].getOrderCount() == ALLOWED_MAX_ORDER)
            System.out.println("Not allowed to exceed max number of order!");
        else{
            int item_count = 0;
            for (String[] strings : items) {
                i = 0;
                int item_index = -1;
                for (; i < item.size(); i++) {
                    if ((item.get(i).getName().equals(strings[0]))) {
                        item_index = i;
                        break;
                    }
                }
                if (item_index == -1)
                    System.out.println("Unknown item " + strings[0]);
                else if (item.get(item_index).getAmount() == 0) {
                    for(int x = 0 ; x < Integer.parseInt(strings[1]); x++)
                        System.out.println("Sorry! No " + strings[0] + " in the stock");
                }
                else {
                    int item_num = Integer.parseInt(strings[1]);
                    while (item.get(item_index).getAmount() != 0 && item_num != 0) {

                        if(item_count == MAX_ITEM) {
                            System.out.println("Max item number is not allowed to exceed !!!");
                            --item_num;
                        }
                        else {
                            item.get(item_index).setAmount();
                            System.out.println("Item " + strings[0] + " added into order");
                            table[table_id].setItems(strings[0]);
                            --item_num;
                            ++item_count;
                        }
                    }
                    while (item_num != 0) {
                        System.out.println("Sorry! No " + strings[0] + " in the stock");
                        --item_num;
                    }
                }
            }
            if (item_count != 0) {
                table[table_id].setOrder_count(item_count);
                waiter[waiter_index].riseOrderCount();
            }
        }
    }
    private static void check_out(String wait_name, int table_id, ArrayList<Item> item, Waiter[] waiter, Table[] table){

        int i, waiter_index = -1;
        for (i = 0; i < waiter.length; i++) {
            if (waiter[i] == null)
                continue;
            if (waiter[i].getName().equals(wait_name)) {
                waiter_index = i;
                break;
            }
        }
        System.out.println("***********************************\nPROGRESSING COMMAND: check_out");

        if (table[table_id] == null)
            System.out.println("There is no table by this ID ,"+ table_id);
        else if (waiter_index == -1)
            System.out.println("There is no waiter named " + wait_name);
        else if (!wait_name.equals(table[table_id].getWaiter()) || !table[table_id].getStatus())
            System.out.println("This table is either not in service now or "+ wait_name + " cannot be assigned this table!");
        else{
            table[table_id].check_out(item);
            waiter[waiter_index].decrease_in_service();
        }

    }
    private static void stock_status(ArrayList<Item> item){

        System.out.println("***********************************\nPROGRESSING COMMAND: stock_status");

        for (Item item1 : item)
            item1.get_info();
    }
    private static void get_table_status(Table[] table){
        System.out.println("***********************************\nPROGRESSING COMMAND: get_table_status");
        int i = 0;
        for ( ; i < table.length ; i++){
            if (table[i] != null){
                System.out.print("Table " + i);
                System.out.println(table[i].getStatus() ? ": Reserved (" + table[i].getWaiter() +")" : ": Free");
            }
        }
    }
    private static void get_order_status(Table[] table){
        System.out.println("***********************************\nPROGRESSING COMMAND: get_order_status");
        int i = 0;
        for ( ; i < table.length ; i++) {
            if (table[i] != null) {
                System.out.print("Table: " + i + "\n");
                table[i].getOrderStatus();
            }
        }
    }
    private static void get_employer_salary(Employer[] employer){
        System.out.println("***********************************\nPROGRESSING COMMAND: get_employer_salary");

        for (Employer employer1 : employer)
            if (employer1 != null) employer1.calculate_salary();
    }

    private static void get_waiter_salary(Waiter[] waiter) {
        System.out.println("***********************************\nPROGRESSING COMMAND: get_waiter_salary");

        for (Waiter waiter1 : waiter)
        if (waiter1 != null) waiter1.calculate_salary();
    }
}
