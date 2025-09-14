package sorting;

public class Record {
    private String date;
    private String domain;
    private String location;
    private int value;
    private int transactionCount;

    public Record(String date, String domain, String location, int value, int transactionCount) {
        this.date = date;
        this.domain = domain;
        this.location = location;
        this.value = value;
        this.transactionCount = transactionCount;
    }

    public String getDate() { return date; }
    public String getDomain() { return domain; }
    public String getLocation() { return location; }
    public int getValue() { return value; }
    public int getTransactionCount() { return transactionCount; }

    @Override
    public String toString() {
        return date + "," + domain + "," + location + "," + value + "," + transactionCount;
    }
}
