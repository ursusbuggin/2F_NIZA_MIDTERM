package com.autorepairservicesystem;

import com.autorepairservicesystem.dao.DatabaseConnection;
import com.autorepairservicesystem.ui.ServiceJobMenu;

public class Main {

    public static void main(String[] args) {
        DatabaseConnection.initialize();
        new ServiceJobMenu().run();
    }
}
