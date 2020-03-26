package com.example.moneyassistance;

public class Contract {
    private Contract(){}
    public static final class TransactionEntry{
        public static final String TABLE_NAME = "Income";
        public static final String TABLE_NAME2 = "Expense";
        public static final String _ID = "_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_Reciept = "reciept";

        public static final String month ="";
        public static final String ORDERBY="strftime("+month+",date)";
    }
}
