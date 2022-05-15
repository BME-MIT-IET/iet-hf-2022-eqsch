package Utils;

public class Pair<FirstType, SecondType> {
    public FirstType first;
    public SecondType second;

    public Pair(){}

    public Pair(FirstType _key, SecondType _val){
        first = _key;
        second = _val;
    }
}
