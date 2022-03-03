package command;

import controllers.ConstantsJSP;
import dao.ApplicationDAOimpl;
import dto.CreateCheckoutDTO;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.CheckoutService;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.*;

@NoArgsConstructor
public class Checkout implements ICommand {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    @SneakyThrows
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final CheckoutService checkoutService = new CheckoutService();
        final RoomService roomService = new RoomService();
        if (req.getMethod().equals(GET)) {
            Integer room = Integer.valueOf(req.getParameter(ROOM));
            roomService.setCleaning(room);
            CreateCheckoutDTO createCheckoutDTO = CreateCheckoutDTO.builder()
                    .room(room.toString())
                    .build();
            checkoutService.create(createCheckoutDTO);
            logger.info("Checkout created");
            resp.sendRedirect("/hotel/login?command=login");
        } else {
            Integer id = Integer.valueOf(req.getParameter(ID));
            Integer room = Integer.valueOf(req.getParameter(ROOM));
            roomService.setFree(room);
            checkoutService.delete(id);
            logger.info("Checkout approved and deleted");
            resp.sendRedirect("/hotel/admin?command=admin");

        }
    }
}
