package services;

import connection.ConnectionPool;
import connection.TransactionHandler;
import constants.MessageConstants;
import constants.Parameters;
import constants.QueriesDB;
import dao.daofactory.DaoFactory;
import dao.interfacesdao.TrackingDAO;
import entities.Tracking;
import entities.User;
import exceptions.DAOException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TrackingService {
    private final static Logger logger = Logger.getLogger(UserService.class);
    private volatile static TrackingService instance;
    private DaoFactory mySqlFactory;
    private TrackingDAO trackingDAO;

    private TrackingService() {
        mySqlFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
        trackingDAO = mySqlFactory.getTrackingDao();
    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static TrackingService getInstance() {
        if (instance == null) {
            synchronized (TrackingService.class) {
                if (instance == null) {
                    return instance = new TrackingService();
                }
            }
        }
        return instance;
    }

    /**
     * This method add new tracking entity in DB. This method implements work with transaction support.
     *
     * @param tracking - a new tracking which will be registered.
     * @throws SQLException
     */
    public void registerTracking(Tracking tracking) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                trackingDAO.add(tracking, connection)
        );
    }

    /**
     * This method receives all activities from database which belongs corresponding user.
     * This method implements work with transaction support.
     *
     * @return - a list of tracking from the database.
     * @throws SQLException
     */
    public List<Tracking> getTrackingByClientId(User user) throws SQLException {
        final List<Tracking>[] trackingList = new List[1];
        TransactionHandler.runInTransaction(connection ->
                trackingList[0] = trackingDAO.getTrackingByClientId(user, connection)
        );
        return trackingList[0];
    }

    /**
     * This method receives all activities from database which belongs corresponding user.
     * This method implements work with transaction support.
     *
     * @return - a list of tracking from the database.
     * @throws SQLException
     */
    public Tracking getTrackingById(String trackingId) throws SQLException {
        final Tracking [] tracking = new Tracking[1];
        TransactionHandler.runInTransaction(connection ->
                tracking[0] = trackingDAO.getTrackingById(trackingId, connection)
        );
        return tracking[0];
    }

    /**
     * This method receives all trackings from database. This method implements work with transaction support.
     *
     * @return - a list of activities from the database.
     * @throws SQLException
     */
    public List<Tracking> getAllTracking() throws SQLException {
        final List<Tracking>[] trackingList = new List[1];
        TransactionHandler.runInTransaction(connection ->
                trackingList[0] = trackingDAO.getAll(connection)
        );
        return trackingList[0];
    }

    /**
     * An additional accessory method that provides work with some attributes of the object of http session.
     * This method sets user's parameters to the session.
     *
     * @param session - an object of the current session.
     */
    public void setAttributeTrackingListToSession(List<Tracking> trackingList, HttpSession session) {
        session.setAttribute(Parameters.TRACKING_LIST, trackingList);
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param id - the id number of tracking which will be updated.
     * @throws SQLException
     */

    public void setStatusTracking(String id, String status) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                setStatus(id, status, connection)
        );
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param id         - the id number of tracking which will be updated.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    public void setStatus(String id, String status, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        Integer statusId = defineStatus(status);
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_TRACKING_STATUS_BY_ID);

            statement.setInt(1, statusId);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param id - the id number of tracking which will be updated.
     * @throws SQLException
     */
    public void setStatusAndTimeAndTimeStopTracking(String id, String status, String time) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                setStatusAndTime(id, status, time, connection)
        );
    }
    /**
     * This method updates an existing record (row) in a database table.
     * @param id - the tracking id which will be updated.
     * @param tracking - the tracking which will be updated.
     * @throws SQLException
     */
    public void updateTracking(String id, Tracking tracking) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                trackingDAO.updateTrackingById(id ,tracking, connection)
        );
    }


    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param id - the id number of tracking which will be updated.
     * @throws SQLException
     */
    public void setStatusAndTimeStartTracking(String id, String status, Long startTime) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                setStatusAndStartTime(id, status, startTime, connection)
        );
    }
    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param id         - the id number of tracking which will be updated.
     * @param status     - the status of tracking which will be updated.
     * @param startTime  - the start time of tracking which will be updated.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    public void setStatusAndStartTime(String id, String status, Long startTime, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        Integer statusId = defineStatus(status);
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_TRACKING_STATUS_AND_START_TIME_BY_ID);
            statement.setInt(1,statusId);// status_id
            statement.setLong(2, startTime);// time_start
            statement.setString(3, id);// tracking_id
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param id         - the id number of tracking which will be updated.
     * @param status     - the status of tracking which will be updated.
     * @param time       - the time of tracking which will be updated.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    public void setStatusAndTime(String id, String status, String time, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        Integer statusId = defineStatus(status);
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_TRACKING_STATUS_AND_TIME_BY_ID);
            statement.setInt(1,statusId);// status_id
            statement.setString(2, time);// time
            statement.setString(3, id);// tracking_id
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    Integer defineStatus(String status){
        Integer statusId = null;
        switch (status) {
            case "NEW_ACTIVITY":{
                statusId = 1;
                break;
            }
            case "IN_PROGRESS": {
                statusId = 2;
                break;
            }
            case "PAUSE": {
                statusId = 3;
                break;
            }
            case "FINISHED": {
                statusId = 4;
                break;
            }
            case "STOP": {
                statusId = 5;
                break;
            }
        }
        return statusId;
    }

}
