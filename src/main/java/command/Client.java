package command;

import dto.UserDTO;
import lombok.SneakyThrows;
import service.RoomService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.USER;
import static controllers.ConstantsJSP.CLIENT;


public class Client implements ICommand {

    final RoomService roomService = new RoomService();

    @SneakyThrows
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(USER);
        req.setAttribute("myRooms", roomService.findClientRoom(userDTO.getEmail()));
        req.getRequestDispatcher(CLIENT).forward(req, resp);
    }
}
