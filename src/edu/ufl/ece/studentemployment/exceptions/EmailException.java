/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ufl.ece.studentemployment.exceptions;

/**
 *
 * @author Saurabh
 */
public class EmailException extends Exception {

    public  EmailException(){
        super();
    }

    public EmailException(String message){
        super(message);
    }
}
