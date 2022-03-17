package command;

import command.util.ErrorHelper;
import dao.ApplicationDAOimpl;
import dto.CreateCheckoutDTO;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.CheckoutService;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;

@NoArgsConstructor
public class Checkout implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        final CheckoutService checkoutService = new CheckoutService();
        final RoomService roomService = new RoomService();
        Integer room = Integer.valueOf(req.getParameter(ROOM));
        roomService.setCleaning(room);
        CreateCheckoutDTO createCheckoutDTO = CreateCheckoutDTO.builder()
                .room(room.toString())
                .build();
        checkoutService.create(createCheckoutDTO);
        LOGGER.info("Checkout created");
        ErrorHelper.errorSendRedirect(CLIENT_PATH,"checkout",resp);
    }
}
