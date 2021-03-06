package commands.implementations.client;

import commands.BasicCommand;
import constants.MessageConstants;
import constants.Parameters;
import constants.PathPageConstants;
import entities.ActivityStatus;
import entities.Tracking;
import manager.ConfigManagerPages;
import Timer.Time;
import services.TrackingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class StopTimeCommand implements BasicCommand {
    /**
     * This method stop te Time counter.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        String trackingId = request.getParameter(Parameters.TRACKING_ID);
        try {
            Tracking tracking = TrackingService.getInstance().getTrackingById(trackingId);
            Time.getInstance().setStartTime(tracking.getTimeStart());
            Time.getInstance().setDifference(tracking.getDifferenceTime());
            Time.getInstance().stop();
            tracking.setStatus(ActivityStatus.PAUSE);
            tracking.setElapsedTime(Time.getInstance().getElapsedTime());
            tracking.setTimeStop(Time.getInstance().getStopTime());
            tracking.setDifferenceTime(Time.getInstance().getDifference());
            TrackingService.getInstance().updateTracking(trackingId, tracking);
            List<Tracking> trackingList = TrackingService.getInstance().getAllTracking();
            TrackingService.getInstance().setAttributeTrackingListToSession(trackingList, session);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
        } catch (SQLException e) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }

}

