// IPerson.aidl
package com.example.servicetest02;

// Declare any non-default types here with import statements

interface IPerson {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void setName(String name);
     void setSex(String sex);
     void setAge(int age);
     String getPerson();


}
