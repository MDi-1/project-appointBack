package com.example.appointback;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
/*
public class MyObjectMatcher implements ArgumentMatcher<LocalDate> {

    private String compareValue;

    public ApplicationContextMatcher(String compareValue) {
        this.compareValue= compareValue;
    }

    public boolean matches(Object argument) {
        MyObject item= (MyObject) argument;
        if(compareValue!= null){
            if (item != null) {
                return compareValue.equals(item.getMyParameter());
            }
        }else {
            return item == null || item.getMyParameter() == null;
        }
        return false;
    }

    @Override
    public boolean matches(LocalDate argument) {
        return false;
    }
}

 */

class ListOfTwoElements implements ArgumentMatcher<List> {
    public boolean matches(List list) {
        return list.size() == 2;
    }
    public String toString() {
        //printed in verification errors
        return "[list of 2 elements]";
    }
}

class SomeClass {

    @Test
    public void testFunction() {
        List mock = mock(List.class);

        when(mock.addAll(argThat(new ListOfTwoElements()))).thenReturn(true);
        mock.addAll(Arrays.asList("one", "two"));
        verify(mock).addAll(argThat(new ListOfTwoElements()));
    }
}