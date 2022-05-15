package Utils;

public class LinkerException extends Exception{
    final int UID;

    public LinkerException(int uid){UID = uid;}

    public int GetUID(){return UID;}
}
