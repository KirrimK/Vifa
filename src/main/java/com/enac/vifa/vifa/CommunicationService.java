package com.enac.vifa.vifa;

import java.lang.reflect.Method;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CommunicationService extends Service<Void> {
    private Method m;

    public CommunicationService(Method m) {
        this.m = m;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                m.invoke(Modele.getInstance());
                return null;
            }
           
        };
    }
}
