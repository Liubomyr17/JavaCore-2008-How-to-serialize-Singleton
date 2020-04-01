package com.company;

/*
2008 How to serialize Singleton
The two deserialized singleton and singleton1 objects have different references in memory and should have the same references.
Add one method to the Singleton class (google it) so that after the deserialization the object references are equal.
The main method does not participate in testing.

Requirements:
1. The Solution.Singleton class must support the Serializable interface.
2. The readResolve method without parameters must be implemented in the Solution.Singleton class.
3. The readResolve method must return a singleton (ourInstance).
4. The readResolve method must be private.


 */


import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Solution implements Serializable {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Singleton instance = Singleton.getInstance();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //Serializing the singleton instance
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(instance);
        oos.close();

        //Recreating the instance by reading the serialized object data add
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
        Singleton singleton = (Singleton) ois.readObject();
        ois.close();

        //Recreating the instance AGAIN by reading the serialized object data add
        byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        ObjectInputStream ois2 = new ObjectInputStream(byteArrayInputStream);
        Singleton singleton1 = (Singleton) ois2.readObject();
        ois2.close();

        //The singleton behavior has been broken
        System.out.println("Instance reference check : " + singleton.getInstance());
        System.out.println("Instance reference check : " + singleton1.getInstance());
        System.out.println("=========================================================");
        System.out.println("Object reference check : " + singleton);
        System.out.println("Object reference check : " + singleton1);
    }


    public static class Singleton implements Serializable {
        private static Singleton ourInstance;

        public static Singleton getInstance() {
            if (ourInstance == null) {
                ourInstance = new Singleton();
            }
            return ourInstance;
        }

        private Singleton() {
        }

        private Object readResolve() throws ObjectStreamException {
            return getInstance();
        }
    }
}


