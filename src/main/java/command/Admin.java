package command;

import command.util.CommandHelper;
import dao.ApplicationDAOimpl;
import dao.CheckoutDAOimpl;
import dto.CreateBillDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ApplicationService;
import service.BillService;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static command.ConstantsCommand.*;


public class Admin implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        final ApplicationService applicationService = new ApplicationService();
        final RoomService roomService = new RoomService();
        final BillService billService = new BillService();
        final CheckoutDAOimpl checkoutDAOimpl = new CheckoutDAOimpl();
        Integer id = Integer.parseInt(req.getParameter(ID));
        String status = req.getParameter(STATUS);
        applicationService.adminProcessApplication(id, status);
        String room = String.valueOf(req.getParameter(FREE_ROOM));
        if (!"null".equals(room)) {
            Integer roomID = Integer.parseInt(getID(room));
            if (status.equals(TRUE)) {
                roomService.setBusy(roomID);
            }
            try {
                Integer time = Integer.valueOf(req.getParameter(TIME));
                Integer cost = Integer.valueOf(getCost(room));
                int totalCost = time * cost;
                String email = req.getParameter(EMAIL);
                CreateBillDTO billDTO = CreateBillDTO.builder()
                        .id(String.valueOf(id))
                        .room(roomID.toString())
                        .email(email)
                        .cost(Integer.toString(totalCost))
                        .build();
                billService.create(billDTO);
                CommandHelper.returnToAdminPage(req, resp, applicationService, checkoutDAOimpl);
                LOGGER.info("Go to admin page");
            } catch (IllegalStateException e) {
                LOGGER.error("Illegal State Exception in Admin command");
                CommandHelper.returnToAdminPage(req, resp, applicationService, checkoutDAOimpl);
            }
        } else {
            CommandHelper.returnToAdminPage(req, resp, applicationService, checkoutDAOimpl);
        }
    }

    private String getCost(String param) {
        Pattern pattern = Pattern.compile("(\\d+)$");
        Matcher matcher = pattern.matcher(param);
        matcher.find();
        return matcher.group();
    }

    private String getID(String param) {
        Pattern pattern = Pattern.compile("^(\\d+)");
        Matcher matcher = pattern.matcher(param);
        matcher.find();
        return matcher.group();
    }
}

