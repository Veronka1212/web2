package command;

import command.util.CommandHelper;
import dto.UserDTO;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.USER;
import static controllers.ConstantsJSP.CLIENT;


public class Client implements ICommand {

    final RoomService roomService = new RoomService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(USER);
        req.setAttribute("myRooms", roomService.findClientRoom(userDTO.getEmail()));
        CommandHelper.errorRequestDispatcher(req, resp, CLIENT, ConstantsCommand.CLIENT);
    }
}
