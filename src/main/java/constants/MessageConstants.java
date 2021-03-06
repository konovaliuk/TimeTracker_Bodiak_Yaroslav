package constants;

/**
 * Description: This class contains all messages represented in constants view.
 *
 * Created by Yaroslav Bodyak on 11.12.2018.
 */
public class MessageConstants {
    public static final String WRONG_LOGIN_OR_PASSWORD = "Incorrect login or password.";
    public static final String SUCCESS_LOGIN = "Login operation has been executed successfully.";
    public static final String SUCCESS_LOGOUT = "Logout operation has been executed successfully";
    public static final String SUCCESS_REGISTRATION = "Registration has been executed successfully.";
    public static final String SUCCESS_CREATION = "Creation has been executed successfully.";
    public static final String SUCCESS_OVERVIEW_CLIENT_COMMAND = "Overview client command executed successfully.";
    public static final String SUCCESS_ADDING_ACTIVITY = "Adding activity to user has been executed successfully.";
    public static final String PAGE_NOT_FOUND = "Page not found. Business logic error was occurred.";
    public static final String WRONG_COMMAND = "An invalid command was sent to the servlet.";
    public static final String TRANSACTION_SUCCEEDED = "Transaction succeeded.";
    public static final String TRANSACTION_FAILED = "Transaction failed.";
    public static final String CLASS_FOR_NAME_ERROR = "Description according to Class.forName() was not found.";
    public static final String STATEMENT_ERROR = "Statement object is null.";
    public static final String RESULTSET_ERROR = "ResultSet object is empty.";
    public static final String CONNECTION_ERROR = "Connection object is null.";
    public static final String DATABASE_ACCESS_ERROR = "Database is not available.";
    public static final String EXECUTE_QUERY_ERROR = "An error was occurred while executing the query to the database.";
    public static final String USER_EXISTS = "User with this login has already existed.";
    public static final String ACTIVITY_EXISTS = "Activity with this name has already existed.";
    public static final String EMPTY_FIELDS = "Attention! At least one of the fields is empty.";
    public static final String EMPTY_FIELDS_ACTIVITY = "Attention! Field is empty.";
    public static final String COLUMN_IS_NOT_VALID = "The column label is not valid.";

    public MessageConstants() {
    }
}
