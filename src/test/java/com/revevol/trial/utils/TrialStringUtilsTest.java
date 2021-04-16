package com.revevol.trial.utils;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrialStringUtilsTest {


    @Test
    public void testRemoveAccent() {

        String input = "è,é,ê,ë,û,ù,ï,î,à,â,ô,Ç,ç,Ã,ã,Õ,õ,È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô";
        String expected = "e,e,e,e,u,u,i,i,a,a,o,C,c,A,a,O,o,E,E,E,E,U,U,I,I,A,A,O";
        assertEquals(expected, TrialStringUtils.removeAccent(input));
    }

    @Test
    public void testHasOnlyLetters() {
        assertTrue(TrialStringUtils.hasOnlyLetters("wertyì  à  ò ù"));
        assertFalse(TrialStringUtils.hasOnlyLetters("%  à  ò ù"));
    }


}